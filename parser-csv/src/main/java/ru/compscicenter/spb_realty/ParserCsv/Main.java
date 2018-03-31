package ru.compscicenter.spb_realty.ParserCsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.service.MongoService;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader in = new InputStreamReader(new FileInputStream("/mnt/storage/Downloads/ADRES.csv"), "windows-1251");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(in);

        Controller<RgisAddressSource> c = new Controller<>(new MongoService(), new RgisAddressSource());

        c.run(32, records);
    }
}
