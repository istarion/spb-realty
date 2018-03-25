package ru.compscicenter.spb_realty.ParserCsv;


import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.MongoRecord;

public interface CsvSource<T extends MongoRecord> {
    public T getData(CSVRecord row);

    public String getAddress(CSVRecord row);

    public String getId(CSVRecord row);
}
