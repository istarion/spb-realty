package ru.compscicenter.spb_realty.ParserCsv;


import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.CustomBuildingInfo;

public interface CsvSource<T extends CustomBuildingInfo> {

    public Building setDataInBuilding(Building building, T data);

    public T getData(CSVRecord row);

    public String getAddress(CSVRecord row);

    default Building setDataInBuilding(Building building, CSVRecord row) {
        return this.setDataInBuilding(building, this.getData(row));
    }
}
