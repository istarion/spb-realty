package ru.compscicenter.spb_realty.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.compscicenter.spb_realty.model.Building;

import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoService {
    private CodecRegistry pojoCodecRegistry;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Building> buildingMongoCollection;
    private final String DATABASE_NAME = "test";
    private final String BUILDINGS_COLLECTION_NAME = "spbRealty";


    public MongoService() {
        this.pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        this.mongoClient = new MongoClient(
                "localhost",
                MongoClientOptions.builder().codecRegistry(this.pojoCodecRegistry).build()
        );

        this.database = mongoClient.getDatabase(DATABASE_NAME);
        this.buildingMongoCollection = this.database.getCollection(BUILDINGS_COLLECTION_NAME, Building.class);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Building getBuildingByRawAddress(String address) {
        Building building = this.getBuildingByAlias(address);
        if (building == null) {
            String normalizedAddress = GorodGovService.normalizeAdress(address);
            building = this.getBuildingOrCreate(normalizedAddress);
            building.addToAddressAliases(address);

            if (!building.getAddressAliases().contains(normalizedAddress)) {
                building.setEasCode(GorodGovService.getEas(normalizedAddress));
                building.addToAddressAliases(normalizedAddress);
            }


            if (building.getId() == null) {
                this.buildingMongoCollection.insertOne(building);
                building = this.getBuildingByAlias(address);
            } else {
                try{
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
            System.out.println("Normalizing via http");
        }
        return building;
    }

    private Building getBuildingByAlias(String alias) {
        return this.buildingMongoCollection.find(Filters.eq("addressAliases", alias)).first();
    }
}
