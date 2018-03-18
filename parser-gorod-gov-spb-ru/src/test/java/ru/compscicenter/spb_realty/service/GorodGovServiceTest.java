package ru.compscicenter.spb_realty.service;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GorodGovServiceTest {

    @org.junit.jupiter.api.Test
    void normalizeAdress() throws IOException {
        assertEquals(GorodGovService.normalizeAdress("Блохина"),
                "г.Санкт-Петербург, улица Блохина, дом 7, литера А");
        assertEquals(GorodGovService.normalizeAdress("Блохина 17"),
                "г.Санкт-Петербург, улица Блохина, дом 17, литера А");
    }
}