package com.alex.exam.AppUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alex.exam.Activity.ActivityMain;
import com.alex.exam.Database.Product;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequests {
    public static String IP = "192.168.0.103:2024";
    private static String PRODUCTS_URL = "http://" + IP + "/all";
    private static String DELETE_PRODUCTS_URL = "http://" + IP + "/product/";
    private static String INSERT_PRODUCTS_URL = "http://" + IP + "/product";
    private static String BUY_PRODUCTS_URL = "http://" + IP + "/buyProduct";

    public static void populateProducts(final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, PRODUCTS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GET_PRODUCTS", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            Product p = new Product();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                p.setName(jsonObject.getString("name"));
                                p.setID(jsonObject.getInt("id"));
                                p.setDescription(jsonObject.getString("description"));
                                p.setPrice(jsonObject.getInt("price"));
                                p.setQuantity(jsonObject.getInt("quantity"));
                                p.setStatus(jsonObject.getString("status"));
                                ActivityMain.products.add(p);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.toString());
                        Toast.makeText(context, "ERROR: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(objectRequest);
    }

    public static void deleteProduct(final int ID, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = DELETE_PRODUCTS_URL + ID;
        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("DELETE_PRODUCT", String.valueOf(ID));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
                Toast.makeText(context, "ERROR: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null)
                    responseString = String.valueOf(response.statusCode);
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(request);
    }

    public static void addProduct(final String n, final String d, final String q, final String p, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_PRODUCTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ADD_PRODUCT", n);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
                Toast.makeText(context, "ERROR: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("name", n);
                parameters.put("description", d);
                parameters.put("quantity", q);
                parameters.put("price", p);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public static void buyProductServer(final int id, final int q, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, BUY_PRODUCTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BUY_PRODUCT", String.valueOf(id));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
                Toast.makeText(context, "ERROR: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("id", String.valueOf(id));
                parameters.put("quantity", String.valueOf(q));
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
