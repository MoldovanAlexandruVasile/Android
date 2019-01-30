package com.alex.exam.AppUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alex.exam.Activity.ActivityMain;
import com.alex.exam.Database.Song;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequests {
    public static String IP = "192.168.0.103:2224";
    private static String SONGS_URL = "http://" + IP + "/all";
    private static String DELETE_SONGS_URL = "http://" + IP + "/song/";
    private static String INSERT_SONGS_URL = "http://" + IP + "/song";
    private static String GENRES_URL = "http://" + IP + "/genres";
    private static String SONGS_BY_GENRE = "http://" + IP + "/songs/";

    public static void populateSongs(final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, SONGS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GET_SONGS", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            Song p = new Song();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                p.setTitle(jsonObject.getString("title"));
                                p.setID(jsonObject.getInt("id"));
                                p.setDescription(jsonObject.getString("description"));
                                p.setAlbum(jsonObject.getString("album"));
                                p.setGenre(jsonObject.getString("genre"));
                                p.setYear(jsonObject.getString("year"));
                                ActivityMain.songs.add(p);
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


    public static void populateGenres(final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, GENRES_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GET_GENRES", response.toString());
                        String genres = response.toString();
                        genres = genres.replace("[", "");
                        genres = genres.replace("]", "");
                        genres = genres.replace("\"", "");
                        String[] array = genres.split(",");
                        ActivityMain.genres.addAll(Arrays.asList(array));
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

    public static void deleteSong(final int ID, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = DELETE_SONGS_URL + ID;
        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("DELETE_SONG", String.valueOf(ID));
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

    public static void getSongsByGenre(final String genre, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = SONGS_BY_GENRE + genre;
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GET_SONGS", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            Song p = new Song();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                p.setTitle(jsonObject.getString("title"));
                                p.setID(jsonObject.getInt("id"));
                                p.setDescription(jsonObject.getString("description"));
                                p.setAlbum(jsonObject.getString("album"));
                                p.setGenre(jsonObject.getString("genre"));
                                p.setYear(jsonObject.getString("year"));
                                ActivityMain.songsByGenre.add(p);
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

    public static void addSong(final String n, final String d, final String q, final String p,
                               final String y, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_SONGS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ADD_SONG", n);
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
                parameters.put("title", n);
                parameters.put("description", d);
                parameters.put("album", q);
                parameters.put("genre", p);
                parameters.put("year", y);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public static void buyProductServer(final int id, final int q, final Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, GENRES_URL, new Response.Listener<String>() {
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
