package ru.compscicenter.spb_realty.ParserCsv;


import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.CustomBuildingInfo;

public interface CsvSource<T extends CustomBuildingInfo> {

    Building setDataInBuilding(Building building, T data);

    T getData(CSVRecord row);

    String getAddress(CSVRecord row);

    default Building setDataInBuilding(Building building, CSVRecord row) {
        return this.setDataInBuilding(building, this.getData(row));
    }
}
