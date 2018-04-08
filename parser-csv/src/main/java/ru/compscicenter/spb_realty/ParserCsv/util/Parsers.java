package ru.compscicenter.spb_realty.ParserCsv.util;

public class Parsers {
    public static Double parseDouble(String input) {
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long parseLong(String input) {
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
