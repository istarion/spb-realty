package ru.compscicenter.spb_realty.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import ru.compscicenter.spb_realty.model.GorodGovResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GorodGovServiceTest {

    @org.junit.jupiter.api.Test
    void normalizeAdress() throws IOException {
        assertEquals(GorodGovService.normalizeAdress("Блохина 17"),
                "г.Санкт-Петербург, улица Блохина, дом 17, литера А");
    }

    @Test
    void test() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("https://gorod.gov.spb.ru/api/v3.1/address/search/?query=Блохина 17")
                .build();


        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            GorodGovResponse[] gorodGovResponses = mapper.readValue(response.body().string(), GorodGovResponse[].class);
            System.out.println(gorodGovResponses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}