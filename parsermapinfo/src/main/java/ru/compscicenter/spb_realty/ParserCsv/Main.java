package ru.compscicenter.spb_realty.ParserCsv;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) throws IOException {
        DBFReader reader = null;
        try {
            reader = new DBFReader(
                    new FileInputStream("/mnt/storage/Downloads/Temp/ADRES.dat"),
                    Charset.forName("windows-1251")
            );
            System.out.println(reader);
        } finally {
            DBFUtils.close(reader);
        }
    }
}
