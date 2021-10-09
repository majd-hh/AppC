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

public class swedenVaccineData {
    String url="https://hex.cse.kau.se/~abdualna100/JSON/data.json";
    Context context;
    public swedenVaccineData(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<VaccineData> VaccineData);

    }

    public void getVaccineDataSweden(String Age,String counties,com.example.app.swedenVaccineData.VolleyResponseListener volleyResponseListener){
        String finalCounties="",AGE="";

        if (counties.isEmpty()||counties.equals("SE")){
            finalCounties="Sweden";
        }
        else {
            finalCounties=counties;
        }
        if (Age.isEmpty()){
            AGE="ALL";
        }
        else {
            AGE=Age;
        }

        System.out.println("\n \n "+ "*****Country innan:::  "+"  AGE:: " +AGE+"  finalCounties:: " + counties);
        //get json object
        List<VaccineData> ViccenReport = new ArrayList<>();
        String finalAGE1 = AGE;
        String finalCounties1 = finalCounties;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ViccenData= response.getJSONArray("records");
                    System.out.println("\n \n "+ "*********** VaccineData Array i info:::  " +ViccenData.length()+"************\n \n  ");
                    System.out.println("\n \n "+ "*****FCountry:::  "+"  finalAGE:: " + finalAGE1 +"  finalVaccine:: " + finalCounties1);
                    //get data
                    for (int i=0;i<ViccenData.length();i++){
                        VaccineData getdata = new VaccineData();
                        JSONObject Vaccineinfo = (JSONObject) ViccenData.get(i);

                        if ( ( Vaccineinfo.getString("ReportingCountry").equals("SE") ) ) {

                            if (  (Vaccineinfo.getString("TargetGroup").equals(finalAGE1)) )
                            {

                                if (finalCounties1.equals("Sweden") )
                                {
                                    getdata.setYearWeekISO(Vaccineinfo.getString("YearWeekISO"));
                                    getdata.setFirstDose(Vaccineinfo.getInt("FirstDose"));
                                    getdata.setSecondDose(Vaccineinfo.getInt("SecondDose"));
                                    getdata.setNumberDosesReceived(Vaccineinfo.getString("NumberDosesReceived"));
                                    if (getdata.getNumberDosesReceived().equals("")) {
                                        getdata.setNumberDosesReceived("0");
                                    }
                                    getdata.setRegion(Vaccineinfo.getString("Region"));
                                    getdata.setPopulation(Vaccineinfo.getInt("Population"));
                                    getdata.setReportingCountry(Vaccineinfo.getString("ReportingCountry"));
                                    getdata.setTargetGroup(Vaccineinfo.getString("TargetGroup"));
                                    getdata.setVaccine(Vaccineinfo.getString("Vaccine"));
                                    getdata.setDenominator(Vaccineinfo.getString("Denominator"));
                                    ViccenReport.add(getdata);
                                }



                                if ( (Vaccineinfo.getString("Region").equals(finalCounties1)) )
                                {
                                    getdata.setYearWeekISO(Vaccineinfo.getString("YearWeekISO"));
                                    getdata.setFirstDose(Vaccineinfo.getInt("FirstDose"));
                                    getdata.setFirstDoseRefused("");
                                    getdata.setSecondDose(Vaccineinfo.getInt("SecondDose"));
                                    getdata.setNumberDosesReceived(Vaccineinfo.getString("NumberDosesReceived"));
                                    if (getdata.getNumberDosesReceived().equals("")) {
                                        getdata.setNumberDosesReceived("0");
                                    }
                                    getdata.setRegion(Vaccineinfo.getString("Region"));
                                    getdata.setPopulation(Vaccineinfo.getInt("Population"));
                                    getdata.setReportingCountry(Vaccineinfo.getString("ReportingCountry"));
                                    getdata.setTargetGroup(Vaccineinfo.getString("TargetGroup"));
                                    getdata.setVaccine(Vaccineinfo.getString("Vaccine"));
                                    getdata.setDenominator(Vaccineinfo.getString("Denominator"));
                                    ViccenReport.add(getdata);
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


    public void getVaccineDistributedSE(String counties,com.example.app.swedenVaccineData.VolleyResponseListener volleyResponseListener){

        //get json object
        List<VaccineData> ViccenReport = new ArrayList<>();
        String finalcounteis=counties;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ViccenData= response.getJSONArray("Vaccinationer tidsserie");
                    System.out.println("\n \n "+ "*********** VaccineData Array i info:::  " +ViccenData.length()+"************\n \n  ");
                    System.out.println("\n \n "+ "***********finalcounteis  json func:::  " +finalcounteis+"************\n \n  ");

                    //get data
                    for (int i=0;i<ViccenData.length();i++){
                        VaccineData getdata = new VaccineData();
                        JSONObject Vaccineinfo = (JSONObject) ViccenData.get(i);

                            getdata.setYearWeekISO(Vaccineinfo.getString("Vecka"));
                            getdata.setRegion(Vaccineinfo.getString("Region"));
                            getdata.setNumberDosesReceived(Vaccineinfo.getString("Antal vaccinationer"));
                            ViccenReport.add(getdata);
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
});MySingleton.getInstance(context).addToRequestQueue(request);

    }





}