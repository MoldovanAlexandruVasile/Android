package com.alex.spottingauto;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ActivityAdmin extends AppCompatActivity {

    //    private static final String ANNOUNCEMENT_URL = "http://192.168.0.103:8012/Announcements/announcementcontroller.php?view=all";
//    private static final String DELETE_ANNOUNCEMENT_URL = "http://192.168.0.103:8012/Announcements/deleteAnnouncement.php";
    private static final String ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/announcementcontroller.php?view=all";
    private static final String DELETE_ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/deleteAnnouncement.php";
    private static List<Announcement> announcementList;
    private static ListView listView;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Manage announcements");
        announcementList = new ArrayList<>();

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();

        listView = findViewById(R.id.allAnnouncements);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ANNOUNCEMENT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("announcement");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Announcement announcement = new Gson().fromJson(resultsArray.get(i).toString(), Announcement.class);
                                announcementList.add(announcement);
                            }
                            CustomAdapter customAdapter = new CustomAdapter();
                            listView.setAdapter(customAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog customDialog = new Dialog(ActivityAdmin.this);
                final Integer position = i;
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setCanceledOnTouchOutside(false);
                customDialog.setContentView(R.layout.layout_custom_pop_up);
                TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
                textView.setText(R.string.delete_announcement);
                CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);
                yesCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue requestDeleteQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, DELETE_ANNOUNCEMENT_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> parameters = new HashMap<>();
                                String ID = String.valueOf(announcementList.get(position).getID());
                                Log.e("RestID: ", ID);
                                parameters.put("ID", ID);
                                return parameters;
                            }
                        };
                        requestDeleteQueue.add(request);
                        Toast.makeText(getApplicationContext(), "DELETED...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityAdmin.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                    }
                });

                CardView noCardView = customDialog.findViewById(R.id.noPopUpCardView);
                noCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.layout_custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText(R.string.go_to_log_in);
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);
        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                finish();
                                Toast.makeText(getApplicationContext(), "You've been logged out.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                startActivity(intent);
                            }
                        });
            }
        });

        CardView noCardView = customDialog.findViewById(R.id.noPopUpCardView);
        noCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return announcementList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_car_list_admin, null);
            try {
                Announcement announcement = announcementList.get(position);
                ImageView imageView = viewEvent.findViewById(R.id.logoImg);
                if (!announcement.getImage_url().isEmpty())
                    Glide.with(getApplicationContext())
                            .load(announcement.getImage_url())
                            .into(imageView);
                TextView titleTV = viewEvent.findViewById(R.id.titleTV);
                titleTV.setText(announcement.getTitle());
                TextView brand = viewEvent.findViewById(R.id.brandTextView);
                brand.setText(announcement.getBrand());
                TextView year = viewEvent.findViewById(R.id.yearTextView);
                year.setText(announcement.getYear());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return viewEvent;
        }
    }
}
