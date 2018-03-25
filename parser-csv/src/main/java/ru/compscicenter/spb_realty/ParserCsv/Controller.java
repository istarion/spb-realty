package ru.compscicenter.spb_realty.ParserCsv;

import com.mongodb.client.model.Filters;
import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.MongoRecord;
import ru.compscicenter.spb_realty.service.GorodGovService;
import ru.compscicenter.spb_realty.service.MongoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Controller<T extends CsvSource> {
    private MongoService mongoService;
    private ExecutorService threadPool;
    private T source;

    public Controller(MongoService mongoService, T source) {
        this.mongoService = mongoService;
        this.source = source;
    }

    public synchronized void run(int threads, Iterable<CSVRecord> records) throws IOException {
        this.threadPool = Executors.newFixedThreadPool(threads);

        List<Callable<Void>> tasks = new ArrayList<>();
        for (CSVRecord r : records) {
            tasks.add(
                    () -> this.processRecord(r)
            );
        }

        try {
            threadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Void processRecord(CSVRecord r) {
        MongoRecord customRecord = source.getData(r);
        Building building = mongoService.getInnerMongoService().getBuildingOrCreate(
                GorodGovService.normalizeAdress(source.getAddress(r))
        );
        boolean updated = false;

        if (building.getRgisAddress() == null) {
            building.setRgisAddress(new HashMap<>());
            updated = true;
        }

        if (!building.getRgisAddress().containsKey(source.getId(r))) {
            building.getRgisAddress().put(source.getId(r), customRecord);
        }

        if (building.getId() == null) {
            System.out.println("Insert: " + building);
            mongoService.getBuildingMongoCollection().insertOne(building);
        } else {
            if (updated) {
                System.out.println("Update: " + building);
                System.out.println(mongoService.getBuildingMongoCollection().replaceOne(Filters.eq("_id", building.getId()), building));
            } else {
                System.out.println("Identical: " + building);
            }
        }

        return null;
    }
}
