package ru.compscicenter.spb_realty.ParserCsv;

import com.mongodb.client.model.Updates;
import org.apache.commons.csv.CSVRecord;
import org.bson.conversions.Bson;
import ru.compscicenter.spb_realty.model.Building;
import ru.compscicenter.spb_realty.model.RgisAddressRecord;
import ru.compscicenter.spb_realty.model.ZUPalataRecord;

import java.util.HashMap;
import java.util.Map;

import static ru.compscicenter.spb_realty.ParserCsv.util.Parsers.parseDouble;
import static ru.compscicenter.spb_realty.ParserCsv.util.Parsers.parseLong;

public class ZuPalataSource implements CsvSource<ZUPalataRecord> {

    public Building setDataInBuilding(Building building, ZUPalataRecord data) {
        Map<String, ZUPalataRecord> zuPalataRecordMap = building.getZuPalataRecordMap();
        if (zuPalataRecordMap == null) {
            zuPalataRecordMap = new HashMap<>();
            building.setZuPalataRecordMap(zuPalataRecordMap);
        }

        String key = String.valueOf(data.getUniqueNumber());
        zuPalataRecordMap.put(key, data);

        return building;
    }

    @Override
    public ZUPalataRecord getData(CSVRecord row) {
        long uniqueNumber = Long.parseLong(row.get("Уник_номер"));
        Double squareRequested = parseDouble(row.get("Площадь_заявленная"));
        Double squareFact = parseDouble(row.get("Площадь_фактическая"));
        Double squareRefined = parseDouble(row.get("Площадь_уточнённая"));
        Double kadValue = parseDouble(row.get("Кадастр_Стоимость"));
        Double unitValue = parseDouble(row.get("Удельн_стоимость"));
        Double uuid_kzr = parseDouble(row.get("Площадь_фактическая"));

        return new ZUPalataRecord(
                uniqueNumber, row.get("Кад_Номер"), row.get("Наименование"), row.get("Статус"), row.get("Вид_Права"),
                row.get("Дата_учета"), row.get("Дата_снятия_с_учета"), row.get("Способ_образования"),
                row.get("Дата_записи_изменений"), squareRequested, squareFact, squareRefined, row.get("Категория"),
                row.get("Вид_разреш_использ_по_докум"), row.get("Старый_кад_номер"), kadValue, unitValue,
                row.get("Адрес"), row.get("Предыдущ_кад_N"), row.get("Дата_ТГР"), row.get("Кадастровый_инженер"),
                row.get("Кад_N_единого_землепольз"), row.get("Кад_N_частей_Е_З"), row.get("Чья_графика"), uuid_kzr,
                row.get("Ориентир"), row.get("Направл_ориентира"), row.get("Расстояние_ориент")
        );
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
    public Bson getUpdates(CSVRecord row) {
        ZUPalataRecord zuPalataRecord = this.getData(row);
        String key = "zuPalataRecord." + zuPalataRecord.getUniqueNumber();
        return Updates.combine(Updates.set(key, zuPalataRecord), Updates.currentTimestamp("lastModified"));
    }
}
