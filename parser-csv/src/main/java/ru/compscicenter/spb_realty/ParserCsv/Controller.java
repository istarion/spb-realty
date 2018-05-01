package ru.compscicenter.spb_realty.ParserCsv;

import com.mongodb.client.model.Filters;
import org.apache.commons.csv.CSVRecord;
import org.bson.conversions.Bson;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.service.MongoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.*;

public class Controller<T extends CsvSource> {
    private MongoService mongoService;
    private ExecutorService threadPool;
    private T source;
    private static Logger logger = Logger.getLogger("ru.compscicenter.spb_realty.ParserCsv.controller");
    private long allRecords;
    private AtomicLong currentCount = new AtomicLong();

    static {
        try {
            Handler fh = new FileHandler("%h/csv-parser.log");
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            logger.setLevel(Level.FINE);
            logger.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller(MongoService mongoService, T source) {
        this.mongoService = mongoService;
        this.source = source;
    }

    public synchronized void run(int threads, Iterable<CSVRecord> records){
        this.threadPool = Executors.newFixedThreadPool(threads);

        List<Callable<Void>> tasks = new ArrayList<>();
        for (CSVRecord r : records) {
            tasks.add(
                    () -> this.processRecord(r)
            );
        }
        this.allRecords = tasks.size();

        System.out.println("Prepared " + this.allRecords + " records.");

        try {
            threadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

    private Void processRecord(CSVRecord r) {
        String address = source.getAddress(r);
        if (!address.equals("NOT_FOUND")) {
            Building building = mongoService.getBuildingByRawAddress(address);

            if (building.getAddress().equals("NOT_FOUND")) {
                logger.fine("NOT_FOUND: " + address);
                System.out.println(this.getCurrentProcess());
                return null;
            }

            Bson updates = source.getUpdates(r, building);

            logger.fine("Update: " + building.getAddress());
            try {
                if (building.getId() != null) {
                    mongoService.getBuildingMongoCollection().updateOne(Filters.eq("_id", building.getId()), updates);
                } else {
                    mongoService.getBuildingMongoCollection().insertOne(building);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.fine("NOT_FOUND: " + address);
        }

        System.out.println(this.getCurrentProcess());
        return null;
    }

    private String getCurrentProcess() {
        long currentCnt = this.currentCount.incrementAndGet();
        return String.format("Processed: %d/%d Progress: %.2f%%", currentCnt, this.allRecords, (double) currentCnt * 100 / this.allRecords);
    }
}
