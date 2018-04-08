package ru.compscicenter.spb_realty.model;

import java.util.Objects;

public class ZUPalataRecord implements CustomBuildingInfo {
    private long uniqueNumber;  // Уник_номер
    private String kadNumber;  // Кад_Номер
    private String naming;  // Наименование
    private String status;  // Статус
    private String right;  // Вид_Права
    private String dateOnRecord;  // Дата_учета
    private String dateOffRecord;  // Дата_снятия_с_учета
    private String methodOfFormation;  // Способ_образования
    private String dateOfInfoUpdate;  // Дата_записи_изменений
    private Double squareRequested;  // Площадь_заявленная
    private Double squareFact;  // Площадь_фактическая
    private Double squareRefined;  // Площадь_уточнённая
    private String category;  // Категория
    private String permission;  // Вид_разреш_использ_по_докум
    private String oldKadNumber;  // Старый_кад_номер
    private Double kadValue;  // Кадастр_Стоимость
    private Double unitValue;  // Удельн_стоимость
    private String address;  // Адрес
    private String prevKadN;  // Предыдущ_кад_N
    private String dateOfTGR;  // Дата_ТГР
    private String kadEngineer;  // Кадастровый_инженер
    private String kadNumberSoleLanduse;  // Кад_N_единого_землепольз
    private String kadNumberPartE3;  // Кад_N_частей_Е_З
    private String graphicsOwner;  // Чья_графика
    private Long uuid_kzr;
    private String orientingPoint;  // Ориентир
    private String orientingDirection;  // Направл_ориентира
    private String orientingDistance;  // Расстояние_ориент

    public ZUPalataRecord() {
    }

    public ZUPalataRecord(long uniqueNumber, String kadNumber, String naming, String status, String right,
                          String dateOnRecord, String dateOffRecord, String methodOfFormation, String dateOfInfoUpdate,
                          Double squareRequested, Double squareFact, Double squareRefined, String category,
                          String permission, String oldKadNumber, Double kadValue, Double unitValue, String address,
                          String prevKadN, String dateOfTGR, String kadEngineer, String kadNumberSoleLanduse,
                          String kadNumberPartE3, String graphicsOwner, Long uuid_kzr, String orientingPoint,
                          String orientingDirection, String orientingDistance) {
        this.uniqueNumber = uniqueNumber;
        this.kadNumber = kadNumber;
        this.naming = naming;
        this.status = status;
        this.right = right;
        this.dateOnRecord = dateOnRecord;
        this.dateOffRecord = dateOffRecord;
        this.methodOfFormation = methodOfFormation;
        this.dateOfInfoUpdate = dateOfInfoUpdate;
        this.squareRequested = squareRequested;
        this.squareFact = squareFact;
        this.squareRefined = squareRefined;
        this.category = category;
        this.permission = permission;
        this.oldKadNumber = oldKadNumber;
        this.kadValue = kadValue;
        this.unitValue = unitValue;
        this.address = address;
        this.prevKadN = prevKadN;
        this.dateOfTGR = dateOfTGR;
        this.kadEngineer = kadEngineer;
        this.kadNumberSoleLanduse = kadNumberSoleLanduse;
        this.kadNumberPartE3 = kadNumberPartE3;
        this.graphicsOwner = graphicsOwner;
        this.uuid_kzr = uuid_kzr;
        this.orientingPoint = orientingPoint;
        this.orientingDirection = orientingDirection;
        this.orientingDistance = orientingDistance;
    }

    public long getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(long uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getKadNumber() {
        return kadNumber;
    }

    public void setKadNumber(String kadNumber) {
        this.kadNumber = kadNumber;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getDateOnRecord() {
        return dateOnRecord;
    }

    public void setDateOnRecord(String dateOnRecord) {
        this.dateOnRecord = dateOnRecord;
    }

    public String getDateOffRecord() {
        return dateOffRecord;
    }

    public void setDateOffRecord(String dateOffRecord) {
        this.dateOffRecord = dateOffRecord;
    }

    public String getMethodOfFormation() {
        return methodOfFormation;
    }

    public void setMethodOfFormation(String methodOfFormation) {
        this.methodOfFormation = methodOfFormation;
    }

    public String getDateOfInfoUpdate() {
        return dateOfInfoUpdate;
    }

    public void setDateOfInfoUpdate(String dateOfInfoUpdate) {
        this.dateOfInfoUpdate = dateOfInfoUpdate;
    }

    public Double getSquareRequested() {
        return squareRequested;
    }

    public void setSquareRequested(Double squareRequested) {
        this.squareRequested = squareRequested;
    }

    public Double getSquareFact() {
        return squareFact;
    }

    public void setSquareFact(Double squareFact) {
        this.squareFact = squareFact;
    }

    public Double getSquareRefined() {
        return squareRefined;
    }

    public void setSquareRefined(Double squareRefined) {
        this.squareRefined = squareRefined;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getOldKadNumber() {
        return oldKadNumber;
    }

    public void setOldKadNumber(String oldKadNumber) {
        this.oldKadNumber = oldKadNumber;
    }

    public Double getKadValue() {
        return kadValue;
    }

    public void setKadValue(Double kadValue) {
        this.kadValue = kadValue;
    }

    public Double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(Double unitValue) {
        this.unitValue = unitValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrevKadN() {
        return prevKadN;
    }

    public void setPrevKadN(String prevKadN) {
        this.prevKadN = prevKadN;
    }

    public String getDateOfTGR() {
        return dateOfTGR;
    }

    public void setDateOfTGR(String dateOfTGR) {
        this.dateOfTGR = dateOfTGR;
    }

    public String getKadEngineer() {
        return kadEngineer;
    }

    public void setKadEngineer(String kadEngineer) {
        this.kadEngineer = kadEngineer;
    }

    public String getKadNumberSoleLanduse() {
        return kadNumberSoleLanduse;
    }

    public void setKadNumberSoleLanduse(String kadNumberSoleLanduse) {
        this.kadNumberSoleLanduse = kadNumberSoleLanduse;
    }

    public String getKadNumberPartE3() {
        return kadNumberPartE3;
    }

    public void setKadNumberPartE3(String kadNumberPartE3) {
        this.kadNumberPartE3 = kadNumberPartE3;
    }

    public String getGraphicsOwner() {
        return graphicsOwner;
    }

    public void setGraphicsOwner(String graphicsOwner) {
        this.graphicsOwner = graphicsOwner;
    }

    public Long getUuid_kzr() {
        return uuid_kzr;
    }

    public void setUuid_kzr(Long uuid_kzr) {
        this.uuid_kzr = uuid_kzr;
    }

    public String getOrientingPoint() {
        return orientingPoint;
    }

    public void setOrientingPoint(String orientingPoint) {
        this.orientingPoint = orientingPoint;
    }

    public String getOrientingDirection() {
        return orientingDirection;
    }

    public void setOrientingDirection(String orientingDirection) {
        this.orientingDirection = orientingDirection;
    }

    public String getOrientingDistance() {
        return orientingDistance;
    }

    public void setOrientingDistance(String orientingDistance) {
        this.orientingDistance = orientingDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZUPalataRecord that = (ZUPalataRecord) o;
        return uniqueNumber == that.uniqueNumber &&
                Objects.equals(kadNumber, that.kadNumber) &&
                Objects.equals(naming, that.naming) &&
                Objects.equals(status, that.status) &&
                Objects.equals(right, that.right) &&
                Objects.equals(dateOnRecord, that.dateOnRecord) &&
                Objects.equals(dateOffRecord, that.dateOffRecord) &&
                Objects.equals(methodOfFormation, that.methodOfFormation) &&
                Objects.equals(dateOfInfoUpdate, that.dateOfInfoUpdate) &&
                Objects.equals(squareRequested, that.squareRequested) &&
                Objects.equals(squareFact, that.squareFact) &&
                Objects.equals(squareRefined, that.squareRefined) &&
                Objects.equals(category, that.category) &&
                Objects.equals(permission, that.permission) &&
                Objects.equals(oldKadNumber, that.oldKadNumber) &&
                Objects.equals(kadValue, that.kadValue) &&
                Objects.equals(unitValue, that.unitValue) &&
                Objects.equals(address, that.address) &&
                Objects.equals(prevKadN, that.prevKadN) &&
                Objects.equals(dateOfTGR, that.dateOfTGR) &&
                Objects.equals(kadEngineer, that.kadEngineer) &&
                Objects.equals(kadNumberSoleLanduse, that.kadNumberSoleLanduse) &&
                Objects.equals(kadNumberPartE3, that.kadNumberPartE3) &&
                Objects.equals(graphicsOwner, that.graphicsOwner) &&
                Objects.equals(uuid_kzr, that.uuid_kzr) &&
                Objects.equals(orientingPoint, that.orientingPoint) &&
                Objects.equals(orientingDirection, that.orientingDirection) &&
                Objects.equals(orientingDistance, that.orientingDistance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueNumber, kadNumber, naming, status, right, dateOnRecord, dateOffRecord, methodOfFormation, dateOfInfoUpdate, squareRequested, squareFact, squareRefined, category, permission, oldKadNumber, kadValue, unitValue, address, prevKadN, dateOfTGR, kadEngineer, kadNumberSoleLanduse, kadNumberPartE3, graphicsOwner, uuid_kzr, orientingPoint, orientingDirection, orientingDistance);
    }

    @Override
    public String toString() {
        return "ZUPalataRecord{" +
                "uniqueNumber=" + uniqueNumber +
                ", kadNumber='" + kadNumber + '\'' +
                ", naming='" + naming + '\'' +
                ", status='" + status + '\'' +
                ", right='" + right + '\'' +
                ", dateOnRecord='" + dateOnRecord + '\'' +
                ", dateOffRecord='" + dateOffRecord + '\'' +
                ", methodOfFormation='" + methodOfFormation + '\'' +
                ", dateOfInfoUpdate='" + dateOfInfoUpdate + '\'' +
                ", squareRequested=" + squareRequested +
                ", squareFact=" + squareFact +
                ", squareRefined=" + squareRefined +
                ", category='" + category + '\'' +
                ", permission='" + permission + '\'' +
                ", oldKadNumber='" + oldKadNumber + '\'' +
                ", kadValue=" + kadValue +
                ", unitValue=" + unitValue +
                ", address='" + address + '\'' +
                ", prevKadN='" + prevKadN + '\'' +
                ", dateOfTGR='" + dateOfTGR + '\'' +
                ", kadEngineer='" + kadEngineer + '\'' +
                ", kadNumberSoleLanduse='" + kadNumberSoleLanduse + '\'' +
                ", kadNumberPartE3='" + kadNumberPartE3 + '\'' +
                ", graphicsOwner='" + graphicsOwner + '\'' +
                ", uuid_kzr=" + uuid_kzr +
                ", orientingPoint='" + orientingPoint + '\'' +
                ", orientingDirection='" + orientingDirection + '\'' +
                ", orientingDistance='" + orientingDistance + '\'' +
                '}';
    }
}
