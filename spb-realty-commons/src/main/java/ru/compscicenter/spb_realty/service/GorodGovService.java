package ru.compscicenter.spb_realty.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.compscicenter.spb_realty.model.GorodGovResponse;

import javax.print.Doc;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class GorodGovService {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private static ObjectMapper jacksonMapper = new ObjectMapper();
    static {
        jacksonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }


    public static String normalizeAdress(String address) {
        GorodGovResponse gorodGovResponse = getBuildingObject(address);
        if (gorodGovResponse == null) {
            return "NOT_FOUND";
        }

        return gorodGovResponse.getFullAddress();
    }

    public static String getEas(String address) {
        GorodGovResponse gorodGovResponse = getBuildingObject(address);
        if (gorodGovResponse == null) {
            return null;
        }
        return String.valueOf(gorodGovResponse.getId());
    }

    private static GorodGovResponse getBuildingObject(String address) {
        if (address.equals("NOT_FOUND")) {
            return null;
        }

        Request request = new Request.Builder()
                .url("https://gorod.gov.spb.ru/api/v3.1/address/search/?query=" + address)
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                GorodGovResponse[] gorodGovResponses = jacksonMapper.readValue(
                        response.body().string(), GorodGovResponse[].class
                );

                Set<String> addressSet = Arrays.stream(gorodGovResponses)
                        .map(GorodGovResponse::getFullAddress)
                        .collect(Collectors.toSet());

                Optional<String> resolvedAddress = AddressResolverService.resolveFromSet(address, addressSet);
                if (resolvedAddress.isPresent()) {
                    return Arrays.stream(gorodGovResponses)
                            .filter((resp) -> resp.getFullAddress().equals(resolvedAddress.get()))
                            .map((resp) -> getBuildingById(resp.getId()))
                            .findFirst()
                            .get();
                }

            } else if (response.code() == 503) {
                try {
                    System.out.println("Throttling sleep!");
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return getBuildingObject(address);
            }

        } catch (SocketTimeoutException e ) {
            return getBuildingObject(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static GorodGovResponse getBuildingById(int id){
        Request request = new Request.Builder()
                .url("https://gorod.gov.spb.ru/api/v3.1/maps/get_building_by_id/" + id)
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return jacksonMapper.readValue(
                        response.body().string(), GorodGovResponse.class
                );
            }
        } catch (SocketTimeoutException e ) {
            return getBuildingById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cannot get building by id! Retrying");
        return getBuildingById(id);
    }

    private static Document getBuildingDocument(String address) {
        if (address.equals("NOT_FOUND")) {
            return null;
        }

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
                Set<String> addressSet = severalHousesList.first().getElementsByTag("a").stream()
                        .map((Element a) -> a.getElementsByClass("address").first().ownText())
                        .collect(Collectors.toSet());

                Optional<String> resolvedAddress = AddressResolverService.resolveFromSet(address, addressSet);

                if (resolvedAddress.isPresent()) {
                    String href = severalHousesList.first().getElementsByTag("a").stream()
                            .filter((Element a) -> getTextFromAnchor(a).equals(resolvedAddress.get()))
                            .map((Element a) -> "https://gorod.gov.spb.ru" + a.attr("href") + "problems")
                            .findFirst().get();

                    doc = Jsoup.connect(href)
                            .data("query", address)
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
                            .timeout(30 * 1000)
                            .get();
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

    private static String getTextFromAnchor(Element anchor) {
        return anchor.getElementsByClass("address").first().ownText();
    }
}
