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

public class covidState {
    String url="",cityUppCase="",info;
    Context context;

    public covidState(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<covidStatemodel> covidStatemodel);

    }


    public void getcovidState(String cityName,VolleyResponseListener volleyResponseListener){
        List<covidStatemodel> covidReport = new ArrayList<>();
        //get json object
        //check if users gives city name
        if (!cityName.isEmpty()){
            cityUppCase=cityName.substring(0, 1).toUpperCase() + cityName.substring(1).toLowerCase();
        }

        if (cityUppCase.isEmpty()){
            url ="https://covid19-api.weedmark.systems/api/v1/stats?country=";
        }else{
            url ="https://covid19-api.weedmark.systems/api/v1/stats?country="+cityUppCase;
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,url,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data= response.getJSONObject("data");
                    JSONArray cityArray= data.getJSONArray("covid19Stats");
                    //get data
                    for (int i=0;i<cityArray.length();i++){

                        covidStatemodel getdata = new covidStatemodel();
                        JSONObject cityInfo = (JSONObject) cityArray.get(i);
                        getdata.setCity(cityInfo.getString("city"));
                        getdata.setProvince(cityInfo.getString("province"));
                        getdata.setCountry(cityInfo.getString("country"));
                        getdata.setLastUpdate(cityInfo.getString("lastUpdate"));
                        getdata.setKeyId(cityInfo.getString("keyId"));
                        getdata.setConfirmed(cityInfo.getString("confirmed"));
                        getdata.setDeaths(cityInfo.getString("deaths"));
                        getdata.setRecovered(cityInfo.getString("recovered"));
                        covidReport.add(getdata);
                    }
                    volleyResponseListener.onResponse(covidReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"something wrong", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("something wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

}
