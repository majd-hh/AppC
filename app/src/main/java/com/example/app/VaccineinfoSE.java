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

public class VaccineinfoSE {
    String url="https://hex.cse.kau.se/~abdualna100/JSON/data.json";
    Context context;
    public VaccineinfoSE(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<VaccineDataSE> VaccineData);

    }
    //getVaccineDistributedSE from json
    public void getVaccineDistributedSE(String counties, VaccineinfoSE.VolleyResponseListener volleyResponseListener){
        //get json object
        List<VaccineDataSE> ViccenReport = new ArrayList<>();
        String finalcounteis=counties;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ViccenData= response.getJSONArray("Doss Distributed");
                    System.out.println("\n \n "+ "*********** VaccineData Array i info:::  " +ViccenData.length()+"************\n \n  ");
                    System.out.println("\n \n "+ "***********finalcounteis  json func:::  " +finalcounteis+"************\n \n  ");

                    //get data
                    for (int i=0;i<ViccenData.length();i++){
                        VaccineDataSE getdata = new VaccineDataSE();
                        JSONObject Vaccineinfo = (JSONObject) ViccenData.get(i);
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

    //getVaccineAdministeredSE from json
    public void getVaccineAdministeredSE(String counties, VaccineinfoSE.VolleyResponseListener volleyResponseListener){
        //get json object
        List<VaccineDataSE> ViccenReport = new ArrayList<>();
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
                        VaccineDataSE getdata = new VaccineDataSE();
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
    //get weekly Vaccine data (doss1,doss2) from json.
    public void getVaccineDataSE(String counties, String age, String doss, VaccineinfoSE.VolleyResponseListener volleyResponseListener){

        //get json object
        List<VaccineDataSE> ViccenReport = new ArrayList<>();
        String finalcounteis=counties;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ViccenData= response.getJSONArray("Vaccinerade tidsserie");
                    System.out.println("\n \n "+ "*********** VaccineData Array i info:::  " +ViccenData.length()+"************\n \n  ");
                    System.out.println("finalcounteis  json func:: " +finalcounteis+"  age json: " + age+ "  doss: "+doss);

                    //get data
                    for (int i=0;i<ViccenData.length();i++){
                        VaccineDataSE getdata = new VaccineDataSE();
                        JSONObject Vaccineinfo = (JSONObject) ViccenData.get(i);
                        getdata.setYearWeekISO(Vaccineinfo.getString("Vecka"));
                        getdata.setRegion(Vaccineinfo.getString("Region"));
                        getdata.setAntalVaccinerad(Vaccineinfo.getString("Antal vaccinerade"));
                        getdata.setAndelVaccinerad(Vaccineinfo.getString("Andel vaccinerade"));
                        getdata.setVaccinationsstatus(Vaccineinfo.getString("Vaccinationsstatus"));

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
    //get Vaccine data(doss1,doss2) by Age from json.
    public void getVaccineDataSEAge(String counties, String age, String doss, VaccineinfoSE.VolleyResponseListener volleyResponseListener){

        //get json object
        List<VaccineDataSE> ViccenReport = new ArrayList<>();
        String finalcounteis=counties;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ViccenData= response.getJSONArray("Vaccinerade ålder");
                    System.out.println("\n \n "+ "*********** VaccineData Array i info:::  " +ViccenData.length()+"************\n \n  ");
                    System.out.println("finalcounteis  json func:: " +finalcounteis+"  age json: " + age+ "  doss: "+doss);

                    //get data
                    for (int i=0;i<ViccenData.length();i++){
                        VaccineDataSE getdata = new VaccineDataSE();
                        JSONObject Vaccineinfo = (JSONObject) ViccenData.get(i);
                        getdata.setRegion(Vaccineinfo.getString("Region"));
                        getdata.setAge(Vaccineinfo.getString("Åldersgrupp"));
                        getdata.setAntalVaccinerad(Vaccineinfo.getString("Antal vaccinerade"));
                        getdata.setAndelVaccinerad(Vaccineinfo.getString("Andel vaccinerade"));
                        getdata.setVaccinationsstatus(Vaccineinfo.getString("Vaccinationsstatus"));

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
