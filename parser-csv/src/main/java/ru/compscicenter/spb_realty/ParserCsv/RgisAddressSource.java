package ru.compscicenter.spb_realty.ParserCsv;

import org.apache.commons.csv.CSVRecord;
import ru.compscicenter.spb_realty.model.RgisAddressRecord;
import ru.compscicenter.spb_realty.service.GorodGovService;

public class RgisAddressSource implements CsvSource<RgisAddressRecord>{

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
    public String getId(CSVRecord row) {
        return row.get("Номер");
    }
}
