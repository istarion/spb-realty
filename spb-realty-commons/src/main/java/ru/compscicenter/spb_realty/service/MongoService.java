package ru.compscicenter.spb_realty.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.compscicenter.spb_realty.model.Building;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoService {
    private CodecRegistry pojoCodecRegistry;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Building> buildingMongoCollection;
    private final String DATABASE_NAME = "test";
    private final String BUILDINGS_COLLECTION_NAME = "buildings5";


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
            this.buildingMongoCollection.insertOne(building);
            System.out.println("Normalizing via http");
        }
        return building;
    }

    public boolean hasAddress(String address) {
        Building record = this.buildingMongoCollection.find(Filters.eq("address", address)).first();
        return record != null;
    }

    private Building getBuildingByAlias(String alias) {
        return this.buildingMongoCollection.find(Filters.eq("addressAliases", alias)).first();
    }
}
