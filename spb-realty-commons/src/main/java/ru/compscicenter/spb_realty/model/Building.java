package ru.compscicenter.spb_realty.model;

import org.bson.types.ObjectId;

import java.util.Map;
import java.util.Objects;

public final class Building {
    private ObjectId id;
    private String address;
    private Map<String,GorodGovRecord> gorodGov;

    public Building() {
    }

    public Building(String address, Map<String, GorodGovRecord> gorodGov) {
        this.address = address;
        this.gorodGov = gorodGov;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, GorodGovRecord> getGorodGov() {
        return gorodGov;
    }

    public void setGorodGov(Map<String, GorodGovRecord> gorodGov) {
        this.gorodGov = gorodGov;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", gorodGov=" + gorodGov +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(getId(), building.getId()) &&
                Objects.equals(getAddress(), building.getAddress()) &&
                Objects.equals(getGorodGov(), building.getGorodGov());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getAddress(), getGorodGov());
    }
}
