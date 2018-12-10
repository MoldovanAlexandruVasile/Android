package com.alex.spottingauto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCarAnnouncementDetails extends AppCompatActivity {
    private static RequestQueue requestQueue;
    //private static final String ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/announcementcontroller.php?view=all";
    private static final String ANNOUNCEMENT_URL = "http://192.168.0.103:8012/Announcements/announcementcontroller.php?view=all";
    //private static final String INSERT_ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/insertAnnouncement.php";
    private static final String INSERT_ANNOUNCEMENT_URL = "http://192.168.0.103:8012/Announcements/insertAnnouncement.php";
    private static List<Announcement> announcementList;
    private static Announcement announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Details");
        this.getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_car_announcement_details);
        ConnectionDetector cd = new ConnectionDetector(this);

        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();

        if (ActivityMain.offlineManagement.size() != 0 && new ConnectionDetector(getApplicationContext()).isConnected()){
            while (ActivityMain.offlineManagement.size() > 0){
                Announcement currentAnnouncement = ActivityMain.offlineManagement.get(0);
                final String title = currentAnnouncement.getTitle();
                final String offerType = currentAnnouncement.getOfferType();
                final String price = currentAnnouncement.getPrice();
                final String currencyFinal = currentAnnouncement.getCurrency();
                final String brand = currentAnnouncement.getBrand();
                final String model = currentAnnouncement.getModel();
                final String year = currentAnnouncement.getYear();
                final String color = currentAnnouncement.getColor();
                final String fuelType = currentAnnouncement.getFuelType();
                final String transmission = currentAnnouncement.getTransmission();
                final String kmOnBoard = currentAnnouncement.getOnBoardKM();
                final String kmOrMiles = currentAnnouncement.getKmOrMiles();
                final String engineCapacity = currentAnnouncement.getEngineCapacity();
                final String doorsNumber = currentAnnouncement.getDoorsNumber();
                final String seatsNumber = currentAnnouncement.getSeatsNumber();
                final String contact = currentAnnouncement.getContact();
                final String description = currentAnnouncement.getDescription();

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, INSERT_ANNOUNCEMENT_URL, new Response.Listener<String>() {
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
                        parameters.put("image_url", "");
                        parameters.put("autor", ActivityMain.acct.getEmail());
                        parameters.put("title", title);
                        parameters.put("offerType", offerType);
                        parameters.put("price", price);
                        parameters.put("currency", currencyFinal);
                        parameters.put("brand", brand);
                        parameters.put("model", model);
                        parameters.put("year", year);
                        parameters.put("color", color);
                        parameters.put("fuelType", fuelType);
                        parameters.put("transmission", transmission);
                        parameters.put("onBoardKM", kmOnBoard);
                        parameters.put("kmOrMiles", kmOrMiles);
                        parameters.put("engineCapacity", engineCapacity);
                        parameters.put("doorsNumber", doorsNumber);
                        parameters.put("seatsNumber", seatsNumber);
                        parameters.put("contact", contact);
                        parameters.put("description", description);
                        return parameters;
                    }
                };
                requestQueue.add(request);
                ActivityMain.offlineManagement.remove(0);
            }
        }

        announcementList = new ArrayList<>();
        Intent intent = getIntent();
        final Integer pos = Integer.valueOf(intent.getStringExtra("position"));
        requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                            announcement = announcementList.get(pos);
                            setFieldsDataFromAnnouncement();
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
    }

    private void setFieldsDataFromAnnouncement() {
        ImageView imageView = findViewById(R.id.carImage);
        if (!announcement.getImage_url().isEmpty())
            Glide.with(getApplicationContext())
                    .load(announcement.getImage_url())
                    .into(imageView);

        TextView offerTV = findViewById(R.id.offerTV);
        offerTV.setText(announcement.getOfferType());

        TextView priceTV = findViewById(R.id.priceTV);
        String currency = "";
        if (announcement.getCurrency().equals("Euro"))
            currency = " €";
        else if (announcement.getCurrency().equals("Lyra"))
            currency = " £";
        else if (announcement.getCurrency().equals("Dollar"))
            currency = " $";

        String text = announcement.getPrice() + currency;
        priceTV.setText(text);

        TextView brandTV = findViewById(R.id.brandTV);
        brandTV.setText(announcement.getBrand());

        TextView modelTV = findViewById(R.id.modelTV);
        modelTV.setText(announcement.getModel());

        TextView yearTV = findViewById(R.id.yearTV);
        yearTV.setText(announcement.getYear());

        TextView colorTV = findViewById(R.id.colorTV);
        colorTV.setText(announcement.getColor());

        TextView fuelTV = findViewById(R.id.fuelTV);
        fuelTV.setText(announcement.getFuelType());

        TextView transmissionTV = findViewById(R.id.transmisionTV);
        transmissionTV.setText(announcement.getTransmission());

        TextView onBoard = findViewById(R.id.kmOnBoardTV);
        text = announcement.getOnBoardKM() + announcement.getKmOrMiles();
        onBoard.setText(text);

        TextView engineCTV = findViewById(R.id.engineCapacityTV);
        engineCTV.setText(announcement.getEngineCapacity());

        TextView doorsTV = findViewById(R.id.doorsNoTV);
        doorsTV.setText(announcement.getDoorsNumber());

        TextView seatsTV = findViewById(R.id.seatsNoTV);
        seatsTV.setText(announcement.getSeatsNumber());

        TextView contact = findViewById(R.id.contactTV);
        contact.setText(announcement.getContact());

        TextView descrTV = findViewById(R.id.descriptionTV);
        descrTV.setText(announcement.getDescription());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityCarAnnouncementDetails.this, ActivityMain.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(ActivityCarAnnouncementDetails.this, ActivityMain.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
        return true;
    }
}
