package ru.compscicenter.spb_realty.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;


public class GorodGovService {
    public static String normalizeAdress(String address) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://gorod.gov.spb.ru/facilities/search/")
                    .data("query", address)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                    .timeout(30*1000)
                    .get();

            Elements severalHousesList = doc.select("ul.favorite");

            if (!severalHousesList.isEmpty()) {
                String url = severalHousesList.first().getElementsByTag("a").first().attr("href");
                doc = Jsoup.connect("https://gorod.gov.spb.ru" + url + "info")
                        .data("query", address)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                        .timeout(30*1000)
                        .get();
            }
        } catch (SocketTimeoutException e) {
            return normalizeAdress(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String normalizedAddress = doc.select("h1").first().text();
        if (normalizedAddress.startsWith("Поиск дома")) {
            return "NOT_FOUND";
        }
        return normalizedAddress;
    }
}
