package ru.compscicenter.spb_realty.ParserGorodGov;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.compscicenter.spb_realty.model.GorodGovRecord;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class Parser {
    public Parser() {
    }

    public GorodGovRecord parse(int id) {
        try {
            Document doc = Jsoup.connect(
                    "https://gorod.gov.spb.ru/facilities/" + String.valueOf(id) + "/info/"
            ).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                    .timeout(30 * 1000)
                    .get();

            Element address_header = doc.select("h1").first();
            return new GorodGovRecord(id, address_header.text());
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 404) {
                System.out.println("Got 404.");
            } else {
                e.printStackTrace();
            }
        } catch (SocketTimeoutException e) {
            return parse(id);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
