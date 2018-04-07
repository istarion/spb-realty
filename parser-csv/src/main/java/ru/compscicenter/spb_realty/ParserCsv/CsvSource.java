package ru.compscicenter.spb_realty.ParserCsv;


import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.CustomBuildingInfo;

public interface CsvSource<T extends CustomBuildingInfo> {
    public T getData(CSVRecord row);

    public String getAddress(CSVRecord row);

    public String getId(CSVRecord row);
}
