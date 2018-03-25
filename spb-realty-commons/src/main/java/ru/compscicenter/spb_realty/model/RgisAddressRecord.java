package ru.compscicenter.spb_realty.model;

public class RgisAddressRecord implements MongoRecord{
    private String number;  // Номер
    private String address;  // Адрес
    private String houseNumber; // Номер дома
    private String building;  // Корпус
    private String litera;  // Литера
    private String shortCharacteristics;  // Краткая_хар_ка
    private String naming;  // Наименование
    private String floors;  // Этажность
    private String syncDate;  // Дата_синхр
    private String codePref;  // Код_преф
    private String PO_Sign;  // Признак_ПО
    private String buildingType;  // Тип_здания
    private String ID;  // ID
    private String quarterCode;  // Код_квартала
    private String buildingNumber;  // Номер_здания
    private String quarter;  // Квартал
    private String id_;  // id_
    private String landNumber;  // Номер_участка
    private String constructionNumber;  // Номер_сооружения
    private String dachaNumber;  // Номер_дачи
    private String okato;  // Окато
    private String mailIndex;  // Почт_индекс
    private String KLADR_code;  // Код_КЛАДР
    private String FIAS_prefix_code;  // Код_ФИАС_префикс
    private String FIAS_house_code;  // Код_ФИАС_дом
    private String yearOfConstruction;  // Год_постройки

    private RgisAddressRecord() {
    }

    public RgisAddressRecord(String number, String address, String houseNumber, String building, String litera,
                             String shortCharacteristics, String naming, String floors, String syncDate,
                             String codePref, String PO_Sign, String buildingType, String ID, String quarterCode,
                             String buildingNumber, String quarter, String id_, String landNumber,
                             String constructionNumber, String dachaNumber, String okato, String mailIndex,
                             String KLADR_code, String FIAS_prefix_code, String FIAS_house_code, String yearOfConstruction) {
        this.number = number;
        this.address = address;
        this.houseNumber = houseNumber;
        this.building = building;
        this.litera = litera;
        this.shortCharacteristics = shortCharacteristics;
        this.naming = naming;
        this.floors = floors;
        this.syncDate = syncDate;
        this.codePref = codePref;
        this.PO_Sign = PO_Sign;
        this.buildingType = buildingType;
        this.ID = ID;
        this.quarterCode = quarterCode;
        this.buildingNumber = buildingNumber;
        this.quarter = quarter;
        this.id_ = id_;
        this.landNumber = landNumber;
        this.constructionNumber = constructionNumber;
        this.dachaNumber = dachaNumber;
        this.okato = okato;
        this.mailIndex = mailIndex;
        this.KLADR_code = KLADR_code;
        this.FIAS_prefix_code = FIAS_prefix_code;
        this.FIAS_house_code = FIAS_house_code;
        this.yearOfConstruction = yearOfConstruction;
    }

    public RgisAddressRecord(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLitera() {
        return litera;
    }

    public void setLitera(String litera) {
        this.litera = litera;
    }

    public String getShortCharacteristics() {
        return shortCharacteristics;
    }

    public void setShortCharacteristics(String shortCharacteristics) {
        this.shortCharacteristics = shortCharacteristics;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    public String getCodePref() {
        return codePref;
    }

    public void setCodePref(String codePref) {
        this.codePref = codePref;
    }

    public String getPO_Sign() {
        return PO_Sign;
    }

    public void setPO_Sign(String PO_Sign) {
        this.PO_Sign = PO_Sign;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQuarterCode() {
        return quarterCode;
    }

    public void setQuarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public String getLandNumber() {
        return landNumber;
    }

    public void setLandNumber(String landNumber) {
        this.landNumber = landNumber;
    }

    public String getConstructionNumber() {
        return constructionNumber;
    }

    public void setConstructionNumber(String constructionNumber) {
        this.constructionNumber = constructionNumber;
    }

    public String getDachaNumber() {
        return dachaNumber;
    }

    public void setDachaNumber(String dachaNumber) {
        this.dachaNumber = dachaNumber;
    }

    public String getOkato() {
        return okato;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    public String getMailIndex() {
        return mailIndex;
    }

    public void setMailIndex(String mailIndex) {
        this.mailIndex = mailIndex;
    }

    public String getKLADR_code() {
        return KLADR_code;
    }

    public void setKLADR_code(String KLADR_code) {
        this.KLADR_code = KLADR_code;
    }

    public String getFIAS_prefix_code() {
        return FIAS_prefix_code;
    }

    public void setFIAS_prefix_code(String FIAS_prefix_code) {
        this.FIAS_prefix_code = FIAS_prefix_code;
    }

    public String getFIAS_house_code() {
        return FIAS_house_code;
    }

    public void setFIAS_house_code(String FIAS_house_code) {
        this.FIAS_house_code = FIAS_house_code;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    @Override
    public String toString() {
        return "RgisAddressRecord{" +
                "number='" + number + '\'' +
                ", address='" + address + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", building='" + building + '\'' +
                ", litera='" + litera + '\'' +
                ", shortCharacteristics='" + shortCharacteristics + '\'' +
                ", naming='" + naming + '\'' +
                ", floors='" + floors + '\'' +
                ", syncDate='" + syncDate + '\'' +
                ", codePref='" + codePref + '\'' +
                ", PO_Sign='" + PO_Sign + '\'' +
                ", buildingType='" + buildingType + '\'' +
                ", ID='" + ID + '\'' +
                ", quarterCode='" + quarterCode + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", quarter='" + quarter + '\'' +
                ", id_='" + id_ + '\'' +
                ", landNumber='" + landNumber + '\'' +
                ", constructionNumber='" + constructionNumber + '\'' +
                ", dachaNumber='" + dachaNumber + '\'' +
                ", okato='" + okato + '\'' +
                ", mailIndex='" + mailIndex + '\'' +
                ", KLADR_code='" + KLADR_code + '\'' +
                ", FIAS_prefix_code='" + FIAS_prefix_code + '\'' +
                ", FIAS_house_code='" + FIAS_house_code + '\'' +
                ", yearOfConstruction='" + yearOfConstruction + '\'' +
                '}';
    }
}
