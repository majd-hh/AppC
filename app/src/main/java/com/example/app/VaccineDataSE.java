package com.example.app;

public class VaccineDataSE {

    private String YearWeekISO;
    private String FirstDose;
    private String SecondDose;
    private String NumberDosesReceived;
    private String Region;
    private String antalVaccinerad;
    private String andelVaccinerad;
    private String Vaccinationsstatus; //for which doss (1 or 2)
    private String Age;

    public VaccineDataSE(){

    }

    public VaccineDataSE(String yearWeekISO, String firstDose, String secondDose, String numberDosesReceived, String region, String antalVaccinerad, String andelVaccinerad, String vaccinationsstatus, String age) {
        YearWeekISO = yearWeekISO;
        FirstDose = firstDose;
        SecondDose = secondDose;
        NumberDosesReceived = numberDosesReceived;
        Region = region;
        this.antalVaccinerad = antalVaccinerad;
        this.andelVaccinerad = andelVaccinerad;
        Vaccinationsstatus = vaccinationsstatus;
        Age = age;
    }

    @Override
    public String toString() {
        return  "YearWeekISO= " + YearWeekISO + "\n" +
                " Region= " + Region + "\n" +
                " Age= " + Age + "\n" +
                " NumberDosesReceived= " + "\n" +
                " FirstDose= " + FirstDose + "\n" +
                " SecondDose= " + SecondDose + "\n" +
                " antalVaccinerad= " + antalVaccinerad + "\n" +
                " andelVaccinerad= " + andelVaccinerad + "\n" +
                " Vaccinationsstatus= " + Vaccinationsstatus + "\n" +

                '}';
    }

    public String getYearWeekISO() {
        return YearWeekISO;
    }

    public void setYearWeekISO(String yearWeekISO) {
        YearWeekISO = yearWeekISO;
    }

    public String getFirstDose() {
        return FirstDose;
    }

    public void setFirstDose(String firstDose) {
        FirstDose = firstDose;
    }

    public String getSecondDose() {
        return SecondDose;
    }

    public void setSecondDose(String secondDose) {
        SecondDose = secondDose;
    }

    public String getNumberDosesReceived() {
        return NumberDosesReceived;
    }

    public void setNumberDosesReceived(String numberDosesReceived) {
        NumberDosesReceived = numberDosesReceived;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getAntalVaccinerad() {
        return antalVaccinerad;
    }

    public void setAntalVaccinerad(String antalVaccinerad) {
        this.antalVaccinerad = antalVaccinerad;
    }

    public String getAndelVaccinerad() {
        return andelVaccinerad;
    }

    public void setAndelVaccinerad(String andelVaccinerad) {
        this.andelVaccinerad = andelVaccinerad;
    }

    public String getVaccinationsstatus() {
        return Vaccinationsstatus;
    }

    public void setVaccinationsstatus(String vaccinationsstatus) {
        Vaccinationsstatus = vaccinationsstatus;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }
}
