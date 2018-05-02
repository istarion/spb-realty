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
}
