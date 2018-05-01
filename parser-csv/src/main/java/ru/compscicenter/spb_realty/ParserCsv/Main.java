package ru.compscicenter.spb_realty.ParserCsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import ru.compscicenter.spb_realty.service.MongoService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Main {


    public static void main(String[] args) throws IOException {
        RunSettings settings = new RunSettings();
        CmdLineParser argParser = new CmdLineParser(settings);

        try {
            argParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println("Invalid usage!");
            argParser.printUsage(System.out);
            return;
        }

        Reader in = new InputStreamReader(new FileInputStream(settings.getFilename()), settings.getCharsetName());
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(in);

        Controller<? extends CsvSource> c = null;
        try {
            c = new Controller<>(
                    new MongoService(),
                    settings.getCsvSource().getDeclaredConstructor().newInstance()
            );

            c.run(4, records);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
