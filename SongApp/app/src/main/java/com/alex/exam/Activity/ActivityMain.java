package com.alex.exam.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.exam.AppUtils.ConnectionDetector;
import com.alex.exam.Database.Song;
import com.alex.exam.Database.TableControllerSongs;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;

import static com.alex.exam.AppUtils.HTTPRequests.populateGenres;
import static com.alex.exam.AppUtils.HTTPRequests.populateSongs;

public class ActivityMain extends AppCompatActivity {

    public static List<String> genres;
    public static List<Song> songs;
    public static List<Song> songsByGenre;
    public static TableControllerSongs ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        songs = new ArrayList<>();
        songsByGenre = new ArrayList<>();
        genres = new ArrayList<>();
        ctrl = new TableControllerSongs(getApplicationContext());
        String goToClient;
        try {
            goToClient = getIntent().getStringExtra("redirect");
        } catch (Exception ex) {
            goToClient = null;
        }
        if (goToClient != null && goToClient.equals("client")) {
            populateSongs(getApplicationContext());
            populateGenres(getApplicationContext());
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
            populateSongs(getApplicationContext());
            populateGenres(getApplicationContext());
            clerk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ActivityClerk.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
            });
        }
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivityClient.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });
    }

    private void goBackToClient() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityClient.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    private void goBackToClerk() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityClerk.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
