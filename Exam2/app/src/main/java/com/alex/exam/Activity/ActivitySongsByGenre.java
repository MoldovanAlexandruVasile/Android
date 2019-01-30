package com.alex.exam.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alex.exam.Database.Song;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitySongsByGenre extends AppCompatActivity {
    private ProgressBar progressBar;
    private ListView listView;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_by_genre);
        getSupportActionBar().hide();
        listView = findViewById(R.id.songs);
        progressBar = findViewById(R.id.progressBar);
        handler.postDelayed(runnable, 2000);

        List<String> your_array_list = new ArrayList<>();
        for (Song song : ActivityMain.songsByGenre)
            your_array_list.add(song.getTitle());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Song song = ActivityMain.songsByGenre.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySongsByGenre.this);
                builder.setTitle(song.getTitle());
                String message = "The song is part of the album '" + song.getAlbum() + "'." +
                        " And appeared in " + song.getYear() + "." +
                        " The song description '" + song.getDescription() + "'.";
                builder.setMessage(message);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Mark as favourite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ActivityMain.ctrl.insertSong(song))
                            Toast.makeText(ActivitySongsByGenre.this, "Marked as favourite.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ActivitySongsByGenre.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityClient.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);
    }
}
