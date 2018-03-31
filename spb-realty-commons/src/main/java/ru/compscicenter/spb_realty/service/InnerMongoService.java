package ru.compscicenter.spb_realty.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ru.compscicenter.spb_realty.model.Building;

import com.mongodb.client.model.Filters;

public class InnerMongoService {
    private MongoDatabase database;
    private MongoCollection<Building> collection;

    public InnerMongoService(MongoDatabase database) {
        this.database = database;
        this.collection = database.getCollection("buildings", Building.class);
    }

    public Building getBuildingOrCreate(String address) {
        Building result = null;
        try {
            result = this.collection.find(Filters.eq("address", address)).first();
            if (result == null) {
                result = new Building();
                result.setAddress(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean hasAddress(String address) {
        Building record = this.collection.find(Filters.eq("address", address)).first();
        return record != null;
    }
}
