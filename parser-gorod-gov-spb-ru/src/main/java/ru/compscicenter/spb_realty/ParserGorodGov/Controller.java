package ru.compscicenter.spb_realty.ParserGorodGov;

import com.mongodb.client.model.Filters;
import org.jsoup.HttpStatusException;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.GorodGovRecord;
import ru.compscicenter.spb_realty.service.GorodGovService;

import java.io.IOException;
import java.util.HashMap;

public class Controller {
    public static void run(int fromIndex, int toIndex, MongoService mongoService) throws IOException {
        Parser parser = new Parser();
        for (int id = fromIndex; id < toIndex; ++id) {
            try {
                GorodGovRecord gorodGovRecord = parser.parse(id);
                if (gorodGovRecord == null) {
                    continue;
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

            } catch (HttpStatusException e) {
                if (e.getStatusCode() == 404) {
                    System.out.println("Got 404.");
                } else throw e;
            }
        }
    }
}
