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

        Building currentBuilding = mongoService.getBuildingByRawAddress(address);

        List<String> newRecord = new ArrayList<>();
        record.iterator().forEachRemaining(newRecord::add);
        newRecord.add(currentBuilding.getFiasCode());
        newRecord.add(currentBuilding.getEasCode());
        newRecord.add(currentBuilding.getKadastrCode());
        newRecord.add(currentBuilding.getKladrCode());
        printer.printRecord(newRecord);

        count.getAndIncrement();
    }

    private String fixAddress(String address) {
        if (Character.isDigit(address.charAt(0))) {
            // Удаляем индекс из начала
            address = address.split(",", 2)[1].trim();
        }
        // Удаляем Санкт-Перербург/ г. Санкт-Петербург из начала
        return address.split(",", 2)[1].trim();
    }

    AtomicLong getCount() {
        return count;
    }
}
