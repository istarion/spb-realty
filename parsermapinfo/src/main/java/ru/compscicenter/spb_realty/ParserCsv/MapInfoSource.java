package ru.compscicenter.spb_realty.ParserCsv;

import com.linuxense.javadbf.DBFRow;

public interface MapInfoSource<T> {
    public T getData(DBFRow row);

    public String getAddress(DBFRow row);
}
