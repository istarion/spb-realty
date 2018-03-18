package ru.compscicenter.spb_realty.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ru.compscicenter.spb_realty.model.Building;

import com.mongodb.client.model.Filters;

public class InnerMongoService {
    private MongoDatabase database;

    public InnerMongoService(MongoDatabase database) {
        this.database = database;
    }

    public Building getBuildingOrCreate(String address) {
        MongoCollection<Building> collection = database.getCollection("buildings", Building.class);
        Building result = collection.find(Filters.eq("address", address)).first();
        if (result == null) {
            result = new Building();
            result.setAddress(address);
        }
        return result;
    }
}
