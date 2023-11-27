package com.example.finalproject;

import android.content.Context;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DataSource {

    private static DataSource instance;

    private RequestQueue requestQueue;

    public static DataSource getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DataSource(context);
        }
        return instance;
    }
    private DataSource(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public void search(String country, Consumer<List<City>> onSuccess, Consumer<VolleyError> onError) {
        String postData = "[out:json];area[name~\""+country.replaceAll("\\\\", "\\\\").replaceAll("\"", "\\\"")
                +"\",i];node[place=city][population][wikidata](area);out qt;";

        String url = "https://overpass-api.de/api/interpreter";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONArray json = new JSONObject(response).getJSONArray("elements");
                ArrayList<City> cities = new ArrayList<>();
                for (int i = 0; i < json.length(); i++)
                {
                    JSONObject node = json.getJSONObject(i);
                    JSONObject tags = node.getJSONObject("tags");
                    cities.add(new City(tags.getString("name"), tags.getInt("population"), node.getDouble("lat"), node.getDouble("lon"), tags.getString("wikidata")));
                }
                onSuccess.accept(cities);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, onError::accept){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("data", postData);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
