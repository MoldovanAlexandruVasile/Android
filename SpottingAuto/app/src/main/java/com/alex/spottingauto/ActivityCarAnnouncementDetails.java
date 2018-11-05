package com.alex.spottingauto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActivityCarAnnouncementDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Details");
        this.getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_car_announcement_details);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout gallery = this.findViewById(R.id.gallery);
        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
        for (int i = 0; i <= 10; i++){
            View view = inflater.inflate(R.layout.layout_car_announcement_details_image, gallery, false);
            ImageView imageView = view.findViewById(R.id.carImageDetails);
            imageView.setImageResource(R.drawable.audi2018);
            gallery.addView(view);
        }
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
