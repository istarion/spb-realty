package ru.compscicenter.spb_realty.CsvIdAdder;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.service.MongoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

class RecordProcessor {
    private CSVPrinter printer;
    private RunSettings settings;
    private AtomicLong count = new AtomicLong();
    private MongoService mongoService = new MongoService();

    RecordProcessor(RunSettings settings, CSVPrinter printer) {
        this.printer = printer;
        this.settings = settings;
    }

    void process(CSVRecord record) throws IOException {
        String address = this.fixAddress(record.get(settings.getAddress()));
        String fias = this.getFiasFromRecord(record);
        String eas = this.getEasFromRecord(record);
        String kadastr = this.getKadastrFromRecord(record);

        Building currentBuilding = mongoService.getBuildingByFias(fias).orElseGet(
                () -> mongoService.getBuildingByEas(eas).orElseGet(
                        () -> mongoService.getBuildingByKadastr(kadastr).orElseGet(
                                () -> mongoService.getBuildingByRawAddress(address)
                        )
                )
        );

        List<String> newRecord = new ArrayList<>();
        record.iterator().forEachRemaining(newRecord::add);
        newRecord.add(currentBuilding.getFiasCode());
        newRecord.add(currentBuilding.getEasCode());
        newRecord.add(currentBuilding.getKadastrCode());
        newRecord.add(currentBuilding.getKladrCode());
        printer.printRecord(newRecord);

        count.getAndIncrement();
    }

    private String getFiasFromRecord(CSVRecord record) {
        if (settings.getFias() == null) {
            return "";
        }

        return record.get(settings.getFias());
    }

    private String getEasFromRecord(CSVRecord record) {
        if (settings.getEas() == null) {
            return "";
        }

        return record.get(settings.getEas());
    }

    private String getKadastrFromRecord(CSVRecord record) {
        if (settings.getKadastr() == null) {
            return "";
        }

        return record.get(settings.getKadastr());
    }

    private String fixAddress(String address) {
//        if (Character.isDigit(address.charAt(0))) {
//            // Удаляем индекс из начала
//            address = address.split(",", 2)[1].trim();
//        }
//        // Удаляем Санкт-Перербург/ г. Санкт-Петербург из начала
//        return address.split(",", 2)[1].trim();
        return address;
    }

    AtomicLong getCount() {
        return count;
    }
}
