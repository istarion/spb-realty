package ru.compscicenter.spb_realty.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class GorodGovService {
    public static String normalizeAdress(String address) throws IOException {
//        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
//        HttpRequest request = requestFactory.buildGetRequest(
//                new GenericUrl("https://gorod.gov.spb.ru/facilities/search/?next=&query=" + URLEncoder.encode(address, "UTF-8"))
//        );
//
//        HttpResponse response = request.execute();
//        String settedCookies = response.getHeaders().getHeaderStringValues("set-cookie").get(0);
//        HttpCookie cookie = HttpCookie.parse(settedCookies).get(0);

        Document doc = Jsoup.connect("https://gorod.gov.spb.ru/facilities/search/")
                .data("query", address)
//                .cookie(cookie.getName(), cookie.getValue())
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                .get();

        Elements severalHousesList = doc.select("ul.favorite");

        if (!severalHousesList.isEmpty()) {
            String url = severalHousesList.first().getElementsByTag("a").first().attr("href");
            doc = Jsoup.connect("https://gorod.gov.spb.ru" + url + "info")
                    .data("query", address)
//                  .cookie(cookie.getName(), cookie.getValue())
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                    .get();
        }

        Element address_header = doc.select("h1").first();
        return address_header.text();
    }
}
