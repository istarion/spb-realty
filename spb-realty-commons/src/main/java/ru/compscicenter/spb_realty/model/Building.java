package ru.compscicenter.spb_realty.model;

import org.bson.types.ObjectId;

import java.util.*;

public final class Building {
    private ObjectId id;
    private String address;
    private List<String> addressAliases;
    private String fiasCode;
    private String easCode;
    private String kadastrCode;

    private Map<String,GorodGovRecord> gorodGov;
    private Map<String,RgisAddressRecord> rgisAddress;
    private Map<String,ZUPalataRecord> zuPalataRecord;

    public Building() {
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

    public Map<String, ZUPalataRecord> getZuPalataRecordMap() {
        return zuPalataRecord;
    }

    public void setZuPalataRecordMap(Map<String, ZUPalataRecord> zuPalataRecordMap) {
        this.zuPalataRecord = zuPalataRecordMap;
    }

    public String getFiasCode() {
        return fiasCode;
    }

    public void setFiasCode(String fiasCode) {
        this.fiasCode = fiasCode;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public String getKadastrCode() {
        return kadastrCode;
    }

    public void setKadastrCode(String kadastrCode) {
        this.kadastrCode = kadastrCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(getId(), building.getId()) &&
                Objects.equals(getAddress(), building.getAddress()) &&
                Objects.equals(getAddressAliases(), building.getAddressAliases()) &&
                Objects.equals(getFiasCode(), building.getFiasCode()) &&
                Objects.equals(getEasCode(), building.getEasCode()) &&
                Objects.equals(getKadastrCode(), building.getKadastrCode()) &&
                Objects.equals(getGorodGov(), building.getGorodGov()) &&
                Objects.equals(getRgisAddress(), building.getRgisAddress()) &&
                Objects.equals(zuPalataRecord, building.zuPalataRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getAddressAliases(), getFiasCode(), getEasCode(), getKadastrCode(), getGorodGov(), getRgisAddress(), zuPalataRecord);
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", addressAliases=" + addressAliases +
                ", fiasCode='" + fiasCode + '\'' +
                ", easCode='" + easCode + '\'' +
                ", kadastrCode='" + kadastrCode + '\'' +
                ", gorodGov=" + gorodGov +
                ", rgisAddress=" + rgisAddress +
                ", zuPalataRecord=" + zuPalataRecord +
                '}';
    }
}
