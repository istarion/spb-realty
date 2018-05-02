package ru.compscicenter.spb_realty.ParserCsv;

import com.mongodb.client.model.Updates;
import org.apache.commons.csv.CSVRecord;
import org.bson.conversions.Bson;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.RgisAddressRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RgisAddressSource implements CsvSource<RgisAddressRecord> {

    public Building setDataInBuilding(Building building, RgisAddressRecord rgisAddressRecord) {
        Map<String, RgisAddressRecord> rgisAddressMap = building.getRgisAddress();
        if (rgisAddressMap == null) {
            rgisAddressMap = new HashMap<>();
            building.setRgisAddress(rgisAddressMap);
        }

        rgisAddressMap.put(rgisAddressRecord.getNumber(), rgisAddressRecord);

        return building;
    }

    @Override
    public RgisAddressRecord getData(CSVRecord row) {
        return new RgisAddressRecord(row.get("Номер"), row.get("Адрес"), row.get("Номер_дома"),
                row.get("Корпус"), row.get("Литера"), row.get("Краткая_хар_ка"), row.get("Наименование"),
                row.get("Этажность"), row.get("Дата_синхр"), row.get("Код_преф"),
                row.get("Признак_ПО"), row.get("Тип_здания"), row.get("ID"), row.get("Код_квартала"),
                row.get("Номер_здания"), row.get("Квартал"), row.get("id_"), row.get("Номер_участка"),
                row.get("Номер_сооружения"), row.get("Номер_дачи"), row.get("Окато"), row.get("Почт_индекс"),
                row.get("Код_КЛАДР"), row.get("Код_ФИАС_префикс"), row.get("Код_ФИАС_дом"), row.get("Год_постройки"));
    }

    @Override
    public String getAddress(CSVRecord row) {
        String address = row.get("Адрес");

        if (Character.isDigit(address.charAt(0))) {
            // Удаляем индекс из начала
            address = address.split(",", 2)[1].trim();
        }
        // Удаляем Санкт-Перербург/ г. Санкт-Петербург из начала
        return address.split(",", 2)[1].trim();
    }

    @Override
    public Bson getUpdates(CSVRecord row, Building building) {
        RgisAddressRecord rgisAddressRecord = this.getData(row);
        String key = "rgisAddress." + rgisAddressRecord.getNumber();

        String fiasCode = rgisAddressRecord.getFIAS_house_code();
        if (fiasCode == null) {
            fiasCode = building.getFiasCode();
        }
        String kladrCode = rgisAddressRecord.getKLADR_code();
        if (kladrCode == null) {
            kladrCode = building.getKladrCode();
        }

        return Updates.combine(
                Updates.set(key, rgisAddressRecord),
                Updates.currentTimestamp("lastModified"),
                Updates.set("fiasCode", fiasCode),
                Updates.set("kladrCode", kladrCode)
        );
    }

    public String getId(CSVRecord row) {
        return row.get("Номер");
    }
}
