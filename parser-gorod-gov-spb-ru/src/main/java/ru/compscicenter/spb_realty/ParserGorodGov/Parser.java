package ru.compscicenter.spb_realty.ParserGorodGov;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.compscicenter.spb_realty.model.GorodGovRecord;

import java.io.IOException;

public class Parser {
    public Parser() {
    }

    public GorodGovRecord parse(int id) {
        try{
            Document doc = Jsoup.connect(
                    "https://gorod.gov.spb.ru/facilities/" + String.valueOf(id) + "/info/"
            ).get();

            Element address_header = doc.select("h1").first();
            return new GorodGovRecord(id, address_header.text());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
