package ru.compscicenter.spb_realty.service;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.net.SocketTimeoutException;


public class GorodGovService {
    public static String normalizeAdress(String address) {
        Document doc = getBuildingDocument(address);
        if (doc == null) {
            return "NOT_FOUND";
        }

        String normalizedAddress = doc.select("h1").first().text();
        if (normalizedAddress.startsWith("Поиск дома")) {
            return "NOT_FOUND";
        }
        return normalizedAddress;
    }

    public static String getEas(String address) {
        Document doc = getBuildingDocument(address);
        String uri = doc.baseUri();
        String eas = uri.substring(uri.indexOf("/facilities/") + 12, uri.indexOf("/problems"));
        return eas;
    }

    private static Document getBuildingDocument(String address) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://gorod.gov.spb.ru/facilities/search/")
                    .data("query", address)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                    .timeout(30 * 1000)
                    .get();

            Elements severalHousesList = doc.select("ul.favorite");

            if (!severalHousesList.isEmpty()) {
                doc = null;
                for (Element a : severalHousesList.first().getElementsByTag("a")) {
                    String elemText = a.getElementsByClass("address").first().ownText();
                    if (elemText.endsWith(address.substring(address.lastIndexOf(' ') + 1))) {
                        doc = Jsoup.connect("https://gorod.gov.spb.ru" + a.attr("href") + "info")
                                .data("query", address)
                                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                                .timeout(30 * 1000)
                                .get();
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            return getBuildingDocument(address);
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 503) {
                try {
                    System.out.println("Throttling sleep!");
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return getBuildingDocument(address);
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
