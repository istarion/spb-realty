package ru.compscicenter.spb_realty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GorodGovResponse {
    private int district;

    @JsonProperty("full_address")
    private String fullAddress;

    private int id;
    private String latitude;
    private String longitude;

    @JsonProperty("problems_count")
    private int problemsCount;

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getProblemsCount() {
        return problemsCount;
    }

    public void setProblemsCount(int problemsCount) {
        this.problemsCount = problemsCount;
    }

    @Override
    public String toString() {
        return "GorodGovResponse{" +
                "district=" + district +
                ", fullAddress='" + fullAddress + '\'' +
                ", id=" + id +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", problemsCount=" + problemsCount +
                '}';
    }
}
