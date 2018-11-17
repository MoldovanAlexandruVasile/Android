package com.alex.spottingauto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.spottingauto.Database.Announcement;

import java.util.List;

public class ActivityCarAnnouncementDetails extends AppCompatActivity {

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

        Intent intent = getIntent();
        Integer pos = Integer.valueOf(intent.getStringExtra("position"));

        List<Announcement> announcements = ActivityMain.myDatabase.DAO().getAnnouncements();
        Announcement announcement = announcements.get(pos);
        setFieldsDataFromAnnouncement(announcement);
    }

    private void setFieldsDataFromAnnouncement(Announcement announcement) {
        TextView offerTV = findViewById(R.id.offerTV);
        offerTV.setText(announcement.getOfferType());

        TextView priceTV = findViewById(R.id.priceTV);
        String text = announcement.getPrice() + announcement.getCurrency();
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
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(ActivityCarAnnouncementDetails.this, ActivityMain.class));
        this.finish();
        return true;
    }
}
