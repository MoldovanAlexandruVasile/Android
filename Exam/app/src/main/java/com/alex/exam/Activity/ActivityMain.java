package com.alex.exam.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.exam.AppUtils.ConnectionDetector;
import com.alex.exam.Database.Bookmark;
import com.alex.exam.Database.TableControllerBookmarks;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;

import static com.alex.exam.AppUtils.HTTPRequests.populateTypes;
import static com.alex.exam.AppUtils.HTTPRequests.populateUnderratedBookmarks;

public class ActivityMain extends AppCompatActivity {

    public static List<Bookmark> underratedBookmarks;
    public static List<Bookmark> bookmarksByType;
    public static List<String> types;
    public static TableControllerBookmarks ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        underratedBookmarks = new ArrayList<>();
        types = new ArrayList<>();
        bookmarksByType = new ArrayList<>();
        ctrl = new TableControllerBookmarks(getApplicationContext());
        String goToClient;
        try {
            goToClient = getIntent().getStringExtra("redirect");
        } catch (Exception ex) {
            goToClient = null;
        }
        if (goToClient != null && goToClient.equals("client")) {
            populateUnderratedBookmarks(getApplicationContext());
            populateTypes(getApplicationContext());
            goBackToClient();
        } else if (goToClient != null && goToClient.equals("clerk")) {
            goBackToClerk();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Button client = findViewById(R.id.clientBtn);
        Button clerk = findViewById(R.id.clerkBtn);
        TextView info = findViewById(R.id.clerkInfo);

        final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnected()) {
            clerk.setVisibility(View.GONE);
            info.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            populateUnderratedBookmarks(getApplicationContext());
            populateTypes(getApplicationContext());
            clerk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ActivityRate.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
            });
        }
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivitySection.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });
    }

    private void goBackToClient() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivitySection.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    private void goBackToClerk() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityRate.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
