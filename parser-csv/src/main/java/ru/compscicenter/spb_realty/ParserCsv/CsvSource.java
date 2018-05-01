package ru.compscicenter.spb_realty.ParserCsv;


import org.apache.commons.csv.CSVRecord;
import org.bson.conversions.Bson;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.CustomBuildingInfo;

public interface CsvSource<T extends CustomBuildingInfo> {
    T getData(CSVRecord row);

    String getAddress(CSVRecord row);

    Bson getUpdates(CSVRecord row, Building building);
}
