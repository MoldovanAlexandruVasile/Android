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

import com.alex.exam.Database.Song;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;

import static com.alex.exam.AppUtils.AppUtils.sortSongsByAlbumAndTitle;
import static com.alex.exam.AppUtils.HTTPRequests.deleteSong;
import static com.alex.exam.AppUtils.HTTPRequests.populateSongs;

public class ActivityClerk extends AppCompatActivity {

    private List<Song> songs;
    private ProgressBar progressBar;
    private RelativeLayout rellay1;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            songs = sortSongsByAlbumAndTitle();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        songs = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk);
        rellay1 = findViewById(R.id.rellay1);
        progressBar = findViewById(R.id.progressBar);
        handler.postDelayed(runnable, 2000);
        ListView listView = findViewById(R.id.productsList);
        Button addBtn = findViewById(R.id.addBtn);
        getSupportActionBar().hide();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivityAddSong.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return songs.size();
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
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_song_clerk, null);
            viewEvent.setMinimumHeight(250);
            final Song song = songs.get(position);

            TextView title = viewEvent.findViewById(R.id.t1);
            title.setText(song.getTitle());

            TextView album = viewEvent.findViewById(R.id.t3);
            album.setText(song.getAlbum());

            TextView genre = viewEvent.findViewById(R.id.t4);
            genre.setText(song.getGenre());

            TextView year = viewEvent.findViewById(R.id.status);
            year.setText(song.getYear());

            ImageView imageView = viewEvent.findViewById(R.id.removeProduct);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteSong(song.getID(), getApplicationContext());
                    populateSongs(getApplicationContext());
                    Toast.makeText(ActivityClerk.this, "Song deleted.", Toast.LENGTH_SHORT).show();
                    goBackToMenu();
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

    public void goBackToMenu() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
        intent.putExtra("redirect", "clerk");
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);
    }
}
