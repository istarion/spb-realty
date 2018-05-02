package ru.compscicenter.spb_realty.service;

import java.util.Optional;
import java.util.Set;

public class AddressResolverService {
    public static Optional<String> resolveFromSet(String address, Set<String> addressSet) {
        return addressSet.stream()
                .filter((addr) -> address.endsWith(addr.substring(addr.lastIndexOf(' ') + 1)))
                .filter((addr) -> getNumbersFromAddress(address).equals(getNumbersFromAddress(addr)))
                .findFirst();
    }

    private static String getNumbersFromAddress(String address) {
        return address.codePoints()
                .filter(Character::isDigit)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
