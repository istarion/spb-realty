package ru.compscicenter.spb_realty.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Building {
    private ObjectId id;
    private String address;
    private List<String> addressAliases = new ArrayList<>();

    private Map<String,GorodGovRecord> gorodGov;
    private Map<String,RgisAddressRecord> rgisAddress;

    public Building() {
    }

    public Building(String address, Map<String, GorodGovRecord> gorodGov, Map<String,RgisAddressRecord> rgisAddress) {
        this.address = address;
        this.gorodGov = gorodGov;
        this.rgisAddress = rgisAddress;
        this.addressAliases.add(address);
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

    public List<String> getAddressAliases() {
        return addressAliases;
    }

    public void setAddressAliases(List<String> addressAliases) {
        this.addressAliases = addressAliases;
    }

    public void addToAddressAliases(String address) {
        if (this.addressAliases == null) {
            this.addressAliases = new ArrayList<>();
        }

        if (!this.addressAliases.contains(address)) {
            this.addressAliases.add(address);
        }
    }

    public Map<String, GorodGovRecord> getGorodGov() {
        return gorodGov;
    }

    public void setGorodGov(Map<String, GorodGovRecord> gorodGov) {
        this.gorodGov = gorodGov;
    }

    public Map<String, RgisAddressRecord> getRgisAddress() {
        return rgisAddress;
    }

    public void setRgisAddress(Map<String, RgisAddressRecord> rgisAddress) {
        this.rgisAddress = rgisAddress;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", addressAliases=" + addressAliases +
                ", gorodGov=" + gorodGov +
                ", rgisAddress=" + rgisAddress +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(getId(), building.getId()) &&
                Objects.equals(getAddress(), building.getAddress()) &&
                Objects.equals(getAddressAliases(), building.getAddressAliases()) &&
                Objects.equals(getGorodGov(), building.getGorodGov()) &&
                Objects.equals(getRgisAddress(), building.getRgisAddress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getAddress(), getAddressAliases(), getGorodGov(), getRgisAddress());
    }
}
