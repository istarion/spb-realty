package ru.compscicenter.spb_realty.CsvIdAdder;

import org.kohsuke.args4j.Option;

import java.util.Map;

public class RunSettings {
    @Option(name = "-f", aliases = "--filename", usage = "CSV file", required = true)
    private String filename;

    @Option(name= "-o", aliases = "--output")
    private String outputFilename = "out.csv";

    @Option(name = "--charset", usage = "CSV file charset (like utf-8 or windows-1251)")
    private String charsetName = "utf-8";

    @Option(name = "--address", usage = "Address field")
    private String address = "ADDRESS";

    @Option(name = "--fias", usage = "Fias field")
    private String fias;

    @Option(name = "--eas", usage = "Eas field")
    private String eas;

    @Option(name = "--kadastr", usage = "Kadastr field")
    private String kadastr;

    public String getFilename() {
        return this.filename;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public String getCharsetName() {
        return this.charsetName;
    }

    public String getAddress() {
        return address;
    }

    public String getFias() {
        return fias;
    }

    public String getEas() {
        return eas;
    }

    public String getKadastr() {
        return kadastr;
    }
}
