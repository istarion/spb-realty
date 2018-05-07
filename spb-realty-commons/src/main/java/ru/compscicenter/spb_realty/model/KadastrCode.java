package ru.compscicenter.spb_realty.model;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KadastrCode {
    private List<Integer> code;

    public KadastrCode(String code) {
        this.setCode(code);
    }

    public String getCode() {
        return code.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(":"));
    }

    public void setCode(String kadastrCode) {
        this.code = Arrays.stream(kadastrCode.split(":"))
                .map(KadastrCode::getDigits)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public Pattern getRegexp() {
        String regexp = code.stream()
                .map(String::valueOf)
                .map("0*"::concat)
                .collect(Collectors.joining(":"));
        return Pattern.compile("^" + regexp + "$");
    }

    private static String getDigits(String s) {
        return s.codePoints()
                .filter(Character::isDigit)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public String toString() {
        return getCode();
    }
}
