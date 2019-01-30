package com.alex.exam.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.exam.AppUtils.ConnectionDetector;

import com.alex.exam.R;

import static com.alex.exam.AppUtils.HTTPRequests.getSongsByGenre;

public class ActivityClient extends AppCompatActivity {
    private ProgressBar progressBar;
    private RelativeLayout rellay1;
    private ImageView refresh;
    private Button favouriteSongs;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            favouriteSongs.setVisibility(View.VISIBLE);
        }
    };
    private Runnable displayRefreshBtn = new Runnable() {
        @Override
        public void run() {
            refresh.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            rellay1.setVisibility(View.GONE);
            favouriteSongs.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        rellay1 = findViewById(R.id.rellay1);
        favouriteSongs = findViewById(R.id.favouriteSongs);
        refresh = findViewById(R.id.refreshImg);
        progressBar = findViewById(R.id.progressBar);
        handler.postDelayed(runnable, 2000);
        getSupportActionBar().hide();

        ListView songsList = findViewById(R.id.genres);
        CustomAdapter customAdapter = new CustomAdapter();
        songsList.setAdapter(customAdapter);
        final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnected()) {
            handler.postDelayed(displayRefreshBtn, 2000);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cd.isConnected())
                        Toast.makeText(ActivityClient.this, "Still no internet connection.", Toast.LENGTH_SHORT).show();
                    else goToMainAndBack();

                }
            });
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
        }

        favouriteSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivityFavouriteSongs.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ActivityMain.genres.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_genre_client, null);
            TextView genre = viewEvent.findViewById(R.id.t1);
            genre.setText(ActivityMain.genres.get(position));

            Button seeSongs = viewEvent.findViewById(R.id.seeSongs);
            seeSongs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSongsByGenre(ActivityMain.genres.get(position), getApplicationContext());
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ActivitySongsByGenre.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
            });
            return viewEvent;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);
    }

    private void goToMainAndBack() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
        intent.putExtra("redirect", "client");
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);
    }
}
