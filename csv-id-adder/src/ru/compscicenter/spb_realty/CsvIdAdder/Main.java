package ru.compscicenter.spb_realty.CsvIdAdder;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.*;
import java.util.*;

public class Main {
    @SuppressWarnings("Duplicates")
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

        try (
                Reader in = new InputStreamReader(new FileInputStream(settings.getFilename()), settings.getCharsetName());
                Writer out = new OutputStreamWriter(new FileOutputStream(settings.getOutputFilename()), settings.getCharsetName())
        ) {
            CSVParser records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(in);

            List<String> newHeader = new LinkedList<>(records.getHeaderMap().keySet());
            newHeader.addAll(List.of("FIAS_CODE", "EAS_CODE", "KADASTR_CODE"));

            CSVFormat printerFormat = CSVFormat.EXCEL.withDelimiter(';').withHeader(
                    newHeader.toArray(new String[0])
            );

            try (CSVPrinter csvPrinter = new CSVPrinter(out, printerFormat)){
                RecordProcessor processor = new RecordProcessor(settings, csvPrinter);
                for (CSVRecord record : records) {
                    processor.process(record);
                    System.out.println("Processed record #" + processor.getCount());
                }
                System.out.println("OK!");
            }
        }
    }
}
