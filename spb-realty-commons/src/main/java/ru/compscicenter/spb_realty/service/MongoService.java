package ru.compscicenter.spb_realty.service;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.KadastrCode;

import java.util.Optional;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoService {
    private CodecRegistry pojoCodecRegistry;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Building> buildingMongoCollection;


    public MongoService(String host, String databaseName, String collectionName) {
        this.pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        this.mongoClient = new MongoClient(
                host,
                MongoClientOptions.builder().codecRegistry(this.pojoCodecRegistry).build()
        );

        this.database = mongoClient.getDatabase(databaseName);
        this.buildingMongoCollection = this.database.getCollection(collectionName, Building.class);
    }


    public MongoService() {
        this("localhost", "test", "spbRealty");
    }

    public CodecRegistry getPojoCodecRegistry() {
        return pojoCodecRegistry;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Building> getBuildingMongoCollection() {
        return buildingMongoCollection;
    }

    public Building getBuildingOrCreate(String address) {
        Building result = null;
        try {
            result = this.buildingMongoCollection.find(Filters.eq("address", address)).first();
            if (result == null) {
                result = new Building();
                result.setAddress(address);
                result.addToAddressAliases(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Building getBuildingByRawAddress(String address) {
        Building building = this.getBuildingByAlias(address);
        if (building == null) {
            System.out.println("Normalizing via http");
            String normalizedAddress = GorodGovService.normalizeAdress(address);
            building = this.getBuildingOrCreate(normalizedAddress);
            building.addToAddressAliases(address);

            if (building.getEasCode() == null) {
                building.setEasCode(GorodGovService.getEas(normalizedAddress));
            }


            if (building.getId() == null) {
                building = this.addNewBuilding(building);
            } else {
                try {
                    this.buildingMongoCollection.updateOne(
                            Filters.eq("_id", building.getId()),
                            Updates.combine(
                                    Updates.addToSet("addressAliases", address),
                                    Updates.set("easCode", building.getEasCode())
                            )
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return building;
    }

    public Optional<Building> getBuildingByFias(String fias) {
        if (fias.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(
                this.buildingMongoCollection.find(Filters.eq("fiasCode", fias)).first()
        );
    }

    public Optional<Building> getBuildingByEas(String eas) {
        if (eas.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(
                this.buildingMongoCollection.find(Filters.eq("easCode", eas)).first()
        );
    }

    public Optional<Building> getBuildingByKadastr(String kadastr) {
        if (kadastr.isEmpty()) {
            return Optional.empty();
        }

        KadastrCode kadastrCode = new KadastrCode(kadastr);

        Optional<Building> optionalBuilding = Optional.ofNullable(
                this.buildingMongoCollection.find(
                        Filters.regex("kadastrCode", kadastrCode.getRegexp())
                ).first()
        );

        return optionalBuilding;
    }

    synchronized public Building addNewBuilding(Building building) {
        this.buildingMongoCollection.insertOne(building);
        return this.getBuildingByAlias(building.getAddress());
    }

    private Building getBuildingByAlias(String alias) {
        return this.buildingMongoCollection.find(Filters.eq("addressAliases", alias)).first();
    }
}
