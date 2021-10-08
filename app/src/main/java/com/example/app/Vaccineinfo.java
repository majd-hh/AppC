package com.example.app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Vaccineinfo {


    String url="https://opendata.ecdc.europa.eu/covid19/vaccine_tracker/json/";
    Context context;
    public Vaccineinfo(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<VaccineData> VaccineData);

    }

    public void getVaccineData(String country,String Age,String vaccineType,com.example.app.Vaccineinfo.VolleyResponseListener volleyResponseListener){

        String countryName="",AGE="",Vaccine="";


        if (country.isEmpty()){
            countryName="SE";
            // System.out.println("\n \n \n"+"countryName.isEmpty()"+"\n \n \n");
        }
        else {
            countryName=country;
        }

        if (Age.isEmpty()){
            AGE="ALL";
        }
        else {
            AGE=Age;
        }
        if (vaccineType.isEmpty()){
            Vaccine="ALL";
        }
        else {
            Vaccine=vaccineType;
        }



        String finalCountryName = countryName;
        String finalAGE = AGE;
        String finalVaccine = Vaccine;

        System.out.println("\n \n "+ "*****Country:::  "+countryName+"  AGE:: " +AGE+"  Vaccine:: " + Vaccine);
        //get json object
        List<VaccineData> ViccenReport = new ArrayList<>();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ViccenData= response.getJSONArray("records");
                    System.out.println("\n \n "+ "*********** VaccineData Array i info:::  " +ViccenData.length()+"************\n \n  ");
                    System.out.println("\n \n "+ "*****FCountry:::  "+finalCountryName+"  finalAGE:: " +finalAGE+"  finalVaccine:: " + finalVaccine);
                    //get data
                    for (int i=0;i<ViccenData.length();i++){
                        VaccineData getdata = new VaccineData();
                        JSONObject Vaccineinfo = (JSONObject) ViccenData.get(i);

                        if ( ( Vaccineinfo.getString("ReportingCountry").equals(finalCountryName) ) ) {
                            if (  (Vaccineinfo.getString("TargetGroup").equals(finalAGE))) {
                                if (finalVaccine.equals("ALL")){
                                    System.out.println("in ALL\n");
                                    getdata.setYearWeekISO(Vaccineinfo.getString("YearWeekISO"));
                                    getdata.setFirstDose(Vaccineinfo.getInt("FirstDose"));
                                    getdata.setFirstDoseRefused("");
                                    getdata.setSecondDose(Vaccineinfo.getInt("SecondDose"));
                                    getdata.setUnknownDose(0);
                                    getdata.setNumberDosesReceived(Vaccineinfo.getString("NumberDosesReceived"));
                                    if (getdata.getNumberDosesReceived().equals("")) {
                                        getdata.setNumberDosesReceived("0");
                                    }
                                    getdata.setRegion("");
                                    getdata.setPopulation(Vaccineinfo.getInt("Population"));
                                    getdata.setReportingCountry(Vaccineinfo.getString("ReportingCountry"));
                                    getdata.setTargetGroup(Vaccineinfo.getString("TargetGroup"));
                                    getdata.setVaccine(Vaccineinfo.getString("Vaccine"));
                                    getdata.setDenominator(Vaccineinfo.getString("Denominator"));
                                    ViccenReport.add(getdata);
                                }else {
                                    if ( (Vaccineinfo.getString("Vaccine").equals(finalVaccine))&&
                                            ( Vaccineinfo.getString("ReportingCountry").equals(finalCountryName) )&&
                                            (Vaccineinfo.getString("TargetGroup").equals(finalAGE))) {
                                        System.out.println("else\n");

                                        getdata.setYearWeekISO(Vaccineinfo.getString("YearWeekISO"));
                                        getdata.setFirstDose(Vaccineinfo.getInt("FirstDose"));
                                        getdata.setFirstDoseRefused("");
                                        getdata.setSecondDose(Vaccineinfo.getInt("SecondDose"));
                                        getdata.setUnknownDose(0);
                                        getdata.setNumberDosesReceived(Vaccineinfo.getString("NumberDosesReceived"));
                                        if (getdata.getNumberDosesReceived().equals("")) {
                                            getdata.setNumberDosesReceived("0");
                                        }
                                        getdata.setRegion("");
                                        getdata.setPopulation(Vaccineinfo.getInt("Population"));
                                        getdata.setReportingCountry(Vaccineinfo.getString("ReportingCountry"));
                                        getdata.setTargetGroup(Vaccineinfo.getString("TargetGroup"));
                                        getdata.setVaccine(Vaccineinfo.getString("Vaccine"));
                                        getdata.setDenominator(Vaccineinfo.getString("Denominator"));
                                        ViccenReport.add(getdata);
                                    }if (finalVaccine.equals("COM")){
                                        System.out.println("in com\n");
                                        getdata.setYearWeekISO(Vaccineinfo.getString("YearWeekISO"));
                                        getdata.setFirstDose(Vaccineinfo.getInt("FirstDose"));
                                        getdata.setFirstDoseRefused("");
                                        getdata.setSecondDose(Vaccineinfo.getInt("SecondDose"));
                                        getdata.setUnknownDose(0);
                                        getdata.setNumberDosesReceived(Vaccineinfo.getString("NumberDosesReceived"));
                                        if (getdata.getNumberDosesReceived().equals("")) {
                                            getdata.setNumberDosesReceived("0");
                                        }
                                        getdata.setRegion("");
                                        getdata.setPopulation(Vaccineinfo.getInt("Population"));
                                        getdata.setReportingCountry(Vaccineinfo.getString("ReportingCountry"));
                                        getdata.setTargetGroup(Vaccineinfo.getString("TargetGroup"));
                                        getdata.setVaccine(Vaccineinfo.getString("Vaccine"));
                                        getdata.setDenominator(Vaccineinfo.getString("Denominator"));
                                        ViccenReport.add(getdata);}
                                    if (finalVaccine.equals("MOD")){
                                        System.out.println("in mod\n");
                                        getdata.setYearWeekISO(Vaccineinfo.getString("YearWeekISO"));
                                        getdata.setFirstDose(Vaccineinfo.getInt("FirstDose"));
                                        getdata.setFirstDoseRefused("");
                                        getdata.setSecondDose(Vaccineinfo.getInt("SecondDose"));
                                        getdata.setUnknownDose(0);
                                        getdata.setNumberDosesReceived(Vaccineinfo.getString("NumberDosesReceived"));
                                        if (getdata.getNumberDosesReceived().equals("")) {
                                            getdata.setNumberDosesReceived("0");
                                        }
                                        getdata.setRegion("");
                                        getdata.setPopulation(Vaccineinfo.getInt("Population"));
                                        getdata.setReportingCountry(Vaccineinfo.getString("ReportingCountry"));
                                        getdata.setTargetGroup(Vaccineinfo.getString("TargetGroup"));
                                        getdata.setVaccine(Vaccineinfo.getString("Vaccine"));
                                        getdata.setDenominator(Vaccineinfo.getString("Denominator"));
                                        ViccenReport.add(getdata);}if (finalVaccine.equals("AZ")){
                                        System.out.println("in AZ\n");
                                        getdata.setYearWeekISO(Vaccineinfo.getString("YearWeekISO"));
                                        getdata.setFirstDose(Vaccineinfo.getInt("FirstDose"));
                                        getdata.setFirstDoseRefused("");
                                        getdata.setSecondDose(Vaccineinfo.getInt("SecondDose"));
                                        getdata.setUnknownDose(0);
                                        getdata.setNumberDosesReceived(Vaccineinfo.getString("NumberDosesReceived"));
                                        if (getdata.getNumberDosesReceived().equals("")) {
                                            getdata.setNumberDosesReceived("0");
                                        }
                                        getdata.setRegion("");
                                        getdata.setPopulation(Vaccineinfo.getInt("Population"));
                                        getdata.setReportingCountry(Vaccineinfo.getString("ReportingCountry"));
                                        getdata.setTargetGroup(Vaccineinfo.getString("TargetGroup"));
                                        getdata.setVaccine(Vaccineinfo.getString("Vaccine"));
                                        getdata.setDenominator(Vaccineinfo.getString("Denominator"));
                                        ViccenReport.add(getdata);}
                                }
                            }

                        }

                    }
                    volleyResponseListener.onResponse(ViccenReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError("something wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

}


