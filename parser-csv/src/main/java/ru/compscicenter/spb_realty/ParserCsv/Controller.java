package ru.compscicenter.spb_realty.ParserCsv;

import com.mongodb.client.model.Filters;
import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.CustomBuildingInfo;
import ru.compscicenter.spb_realty.model.RgisAddressRecord;
import ru.compscicenter.spb_realty.service.GorodGovService;
import ru.compscicenter.spb_realty.service.MongoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public synchronized void run(int threads, Iterable<CSVRecord> records) throws IOException {
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
        CustomBuildingInfo customRecord = source.getData(r);
        String address = source.getAddress(r);
        if (!mongoService.hasAddress(address)) {
            address = GorodGovService.normalizeAdress(source.getAddress(r));
        } else {
            System.out.println("Address found. Normalization skipped.");
        }
        Building building = mongoService.getBuildingOrCreate(address);
        boolean updated = false;

        if (building.getRgisAddress() == null) {
            building.setRgisAddress(new HashMap<>());
            updated = true;
        }

        if (!building.getRgisAddress().containsKey(source.getId(r))) {
            building.getRgisAddress().put(source.getId(r), (RgisAddressRecord) customRecord);
            updated = true;
        }

        if (building.getId() == null) {
            logger.fine("Insert: " + building);
            mongoService.getBuildingMongoCollection().insertOne(building);
        } else if (updated) {
            logger.fine("Update: " + building);
            mongoService.getBuildingMongoCollection().replaceOne(Filters.eq("_id", building.getId()), building);
        } else {
            logger.fine("Identical: " + building);
        }
        long currentCnt = this.currentCount.incrementAndGet();
        System.out.println(String.format("Processed: %d/%d Progress: %.2f%%", currentCnt, this.allRecords,
                (double) currentCnt * 100 / this.allRecords));

        return null;
    }
}
