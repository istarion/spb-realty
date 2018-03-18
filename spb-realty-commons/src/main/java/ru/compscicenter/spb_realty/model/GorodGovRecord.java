package ru.compscicenter.spb_realty.model;

import java.util.Objects;

public class GorodGovRecord {
    private int id;
    private String address;

    public GorodGovRecord() {}

    public GorodGovRecord(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "GorodGovRecord{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GorodGovRecord that = (GorodGovRecord) o;
        return getId() == that.getId() &&
                Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getAddress());
    }
}
