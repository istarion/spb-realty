package ru.compscicenter.spb_realty.ParserGorodGov;

import com.mongodb.client.model.Filters;
import org.jsoup.HttpStatusException;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.GorodGovRecord;
import ru.compscicenter.spb_realty.service.GorodGovService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Controller {
    private int fromIndex;
    private int toIndex;
    private MongoService mongoService;
    private ExecutorService threadPool;
    private Parser parser;

    private final int CHUNKSIZE = 256;

    Controller(int fromIndex, int toIndex, MongoService mongoService) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.mongoService = mongoService;
        this.parser = new Parser();
    }

    public synchronized void run(int threads) throws IOException {
        this.threadPool = Executors.newFixedThreadPool(threads);

        for (int chunkId = 0; chunkId * CHUNKSIZE < (toIndex - fromIndex); ++chunkId) {
            int chunkStart = fromIndex + chunkId * CHUNKSIZE;
            int chunkEnd = fromIndex + (chunkId+1) * CHUNKSIZE;
            if (chunkEnd > this.toIndex) {
                chunkEnd = this.toIndex;
            }
            System.out.println("PROCESSING CHUNK: " + String.valueOf(chunkStart) + " - " + String.valueOf(chunkEnd));
            this.processChunk(chunkStart, chunkEnd);
        }

    }

    private void processChunk(int chunkStart, int chunkEnd) {
        List<Callable<Void>> tasks = new ArrayList<>();
        IntStream.range(chunkStart, chunkEnd+1).forEach(
                i -> tasks.add(
                        () -> this.processRecord(i)
                )
        );
        try {
            threadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Void processRecord(int id) {
        GorodGovRecord gorodGovRecord = parser.parse(id);
        if (gorodGovRecord == null) {
            return null;
        }

        Building record = mongoService.getInnerMongoService().getBuildingOrCreate(
                GorodGovService.normalizeAdress(gorodGovRecord.getAddress())
        );
        boolean updated = false;

        if (record.getGorodGov() == null) {
            record.setGorodGov(new HashMap<>());
            updated = true;
        }

        if (!record.getGorodGov().containsKey(String.valueOf(id))) {
            record.getGorodGov().put(String.valueOf(id), gorodGovRecord);
            updated = true;
        }

        if (record.getId() == null) {
            System.out.println("Insert: " + record);
            mongoService.getBuildingMongoCollection().insertOne(record);
        } else {
            if (updated) {
                System.out.println("Update: " + record);
                System.out.println(mongoService.getBuildingMongoCollection().replaceOne(Filters.eq("_id", record.getId()), record));
            } else {
                System.out.println("Identical: " + record);
            }
        }
        return null;
    }
}
