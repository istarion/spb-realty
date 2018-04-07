package ru.compscicenter.spb_realty.ParserCsv;

import org.kohsuke.args4j.Option;

import java.util.Map;

public class RunSettings {
    private Map<String, Class<? extends CsvSource>> sourcesMap = Map.ofEntries(
            Map.entry("RGISADDRESS", RgisAddressSource.class)
    );

    @Option(name = "-f", aliases = "--filename", usage = "CSV file", required = true)
    private String filename;

    @Option(name = "--charset", usage = "CSV file charset (like utf-8 or windows-1251)")
    private String charsetName = "utf-8";

    @Option(name = "--source", usage = "Source type, can be rgisAddress now", required = true)
    private String source;

    public String getFilename() {
        return this.filename;
    }

    public String getCharsetName() {
        return this.charsetName;
    }

    public Class<? extends CsvSource> getCsvSource() {
        return this.sourcesMap.get(this.source.toUpperCase());
    }
}
