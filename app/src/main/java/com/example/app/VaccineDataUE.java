package com.example.app;

public class VaccineDataUE {

    private String YearWeekISO;
    private int FirstDose;
    private String  FirstDoseRefused;
    private int SecondDose;
    private int UnknownDose;
    private String NumberDosesReceived;
    private String Region;
    private int Population;
    private String ReportingCountry;
    private String TargetGroup;
    private String Vaccine;
    private String Denominator;


    public VaccineDataUE(){

    }


    public VaccineDataUE(String yearWeekISO, int firstDose, String firstDoseRefused,
                         int secondDose, int unknownDose, String numberDosesReceived,
                         String region, int population, String reportingCountry,
                         String targetGroup, String vaccine, String denominator) {
        YearWeekISO = yearWeekISO;
        FirstDose = firstDose;
        FirstDoseRefused = firstDoseRefused;
        SecondDose = secondDose;
        UnknownDose = unknownDose;
        NumberDosesReceived = numberDosesReceived;
        Region = region;
        Population = population;
        ReportingCountry = reportingCountry;
        TargetGroup = targetGroup;
        Vaccine = vaccine;
        Denominator = denominator;
    }


    @Override
    public String toString() {
        return "VaccineData{" +"\n"+
                "YearWeekISO= " + YearWeekISO + "\n" +
                "FirstDose= " + FirstDose +"\n"+
                "FirstDoseRefused= " + FirstDoseRefused +"\n"+
                "SecondDose= " + SecondDose +"\n"+
                "UnknownDose= " + UnknownDose +"\n"+
                "NumberDosesReceived= " + NumberDosesReceived +"\n"+
                "Region= " + Region + "\n"+
                "Population= " + Population +"\n"+
                "ReportingCountry= " + ReportingCountry + "\n" +
                "TargetGroup= " + TargetGroup + "\n"+
                "Vaccine= " + Vaccine + "\n" +
                "Denominator= " + Denominator +"\n"+
                '}';
    }

    public String getYearWeekISO() {
        return YearWeekISO;
    }

    public void setYearWeekISO(String yearWeekISO) {
        YearWeekISO = yearWeekISO;
    }

    public int getFirstDose() {
        return FirstDose;
    }

    public void setFirstDose(int firstDose) {
        FirstDose = firstDose;
    }

    public String getFirstDoseRefused() {
        return FirstDoseRefused;
    }

    public void setFirstDoseRefused(String firstDoseRefused) {
        FirstDoseRefused = firstDoseRefused;
    }

    public int getSecondDose() {
        return SecondDose;
    }

    public void setSecondDose(int secondDose) {
        SecondDose = secondDose;
    }

    public int getUnknownDose() {
        return UnknownDose;
    }

    public void setUnknownDose(int unknownDose) {
        UnknownDose = unknownDose;
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

    public int getPopulation() {
        return Population;
    }

    public void setPopulation(int population) {
        Population = population;
    }

    public String getReportingCountry() {
        return ReportingCountry;
    }

    public void setReportingCountry(String reportingCountry) {
        ReportingCountry = reportingCountry;
    }

    public String getTargetGroup() {
        return TargetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        TargetGroup = targetGroup;
    }

    public String getVaccine() {
        return Vaccine;
    }

    public void setVaccine(String vaccine) {
        Vaccine = vaccine;
    }

    public String getDenominator() {
        return Denominator;
    }

    public void setDenominator(String denominator) {
        Denominator = denominator;
    }
}
