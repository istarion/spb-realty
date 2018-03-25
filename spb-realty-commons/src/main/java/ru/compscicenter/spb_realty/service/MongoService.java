package ru.compscicenter.spb_realty.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    private InnerMongoService innerMongoService;

    public MongoService() {
        this.pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        this.mongoClient = new MongoClient(
                "localhost",
                MongoClientOptions.builder().codecRegistry(this.pojoCodecRegistry).build()
        );

        this.database = mongoClient.getDatabase("test");
        this.buildingMongoCollection = this.database.getCollection("buildings", Building.class);
        this.innerMongoService = new InnerMongoService(database);
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

    public InnerMongoService getInnerMongoService() {
        return innerMongoService;
    }
}
