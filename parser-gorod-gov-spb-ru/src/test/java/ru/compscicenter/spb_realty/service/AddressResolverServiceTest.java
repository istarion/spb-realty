package ru.compscicenter.spb_realty.service;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddressResolverServiceTest {

    @Test
    void resolveFromSet() {
        assertEquals(
                "Расстанная улица, дом 2, корпус 1, литера А",
                AddressResolverService.resolveFromSet(
                        "г.Санкт-Петербург, Расстанная улица, дом 2, корпус 1, литера А",
                        Set.of(
                                "Расстанная улица, дом 2, корпус 2, литера А",
                                "Расстанная улица, дом 2, корпус 1, литера А",
                                "Расстанная улица, дом 1, литера А",
                                "Расстанная улица, дом 1, корпус 2, литера А"
                        )
                ).get()
        );

        assertFalse(
                AddressResolverService.resolveFromSet(
                        "г.Санкт-Петербург, Расстанная улица, дом 1, корпус 1, литера А",
                        Set.of(
                                "Расстанная улица, дом 2, корпус 2, литера А",
                                "Расстанная улица, дом 2, корпус 1, литера А",
                                "Расстанная улица, дом 1, литера А",
                                "Расстанная улица, дом 1, корпус 2, литера А"
                        )
                ).isPresent()
        );
    }
}