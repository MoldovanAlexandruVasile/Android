package com.alex.exam.AppUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alex.exam.Activity.ActivityMain;
import com.alex.exam.Database.Bookmark;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequests {
    public static String IP = "192.168.43.22:2230";
    private static String UNDERRATED_BOOKMARKS_URL = "http://" + IP + "/underrated";
    private static String RATE_URL = "http://" + IP + "/rate/";
    private static String DELETE_BOOKMARK_URL = "http://" + IP + "/bookmark/";
    private static String INSERT_BOOKMARK_URL = "http://" + IP + "/bookmark";
    private static String GET_TYPES_URL = "http://" + IP + "/types";
    private static String GET_BOOKMARK_TYPE_URL = "http://" + IP + "/bookmarks/";

    public static void populateUnderratedBookmarks(final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, UNDERRATED_BOOKMARKS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GET_BOOKMARKS", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            Bookmark b = new Bookmark();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                b.setID(jsonObject.getInt("id"));
                                b.setName(jsonObject.getString("name"));
                                b.setDescription(jsonObject.getString("description"));
                                b.setUrl(jsonObject.getString("url"));
                                b.setType(jsonObject.getString("type"));
                                b.setRating(jsonObject.getInt("rating"));
                                ActivityMain.underratedBookmarks.add(b);
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

    public static void populateTypes(final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, GET_TYPES_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GET_TYPES", response.toString());
                        String types = response.toString();
                        types = types.replace("[", "");
                        types = types.replace("]", "");
                        types = types.replace("\"", "");
                        String[] array = types.split(",");
                        ActivityMain.types.addAll(Arrays.asList(array));
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

    public static void rateBookmark(final int ID, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, RATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RATE_BOOKMARK", String.valueOf(ID));
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

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("id", String.valueOf(ID));
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public static void deleteBookmark(final int ID, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = DELETE_BOOKMARK_URL + ID;
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

    public static void addBookmark(final String n, final String d, final String q, final String p, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_BOOKMARK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ADD_BOOKMARK", n);
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
                parameters.put("url", q);
                parameters.put("type", p);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public static void getBookmarksByType(final String type, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = GET_BOOKMARK_TYPE_URL + type;
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ActivityMain.bookmarksByType = new ArrayList<>();
                        Log.e("GET_BOOKMARKS", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            Bookmark p = new Bookmark();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                p.setID(jsonObject.getInt("id"));
                                p.setName(jsonObject.getString("name"));
                                p.setDescription(jsonObject.getString("description"));
                                p.setUrl(jsonObject.getString("url"));
                                p.setType(jsonObject.getString("type"));
                                p.setRating(jsonObject.getInt("rating"));
                                ActivityMain.bookmarksByType.add(p);
                                if (!ActivityMain.ctrl.readBookmarksDB().contains(p))
                                    ActivityMain.ctrl.insertBookmark(p);
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

    public static void buyProductServer(final int id, final int q, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, GET_TYPES_URL, new Response.Listener<String>() {
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
