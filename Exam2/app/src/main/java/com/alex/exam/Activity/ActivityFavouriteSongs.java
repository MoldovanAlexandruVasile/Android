package com.alex.exam.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alex.exam.Database.Song;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityFavouriteSongs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_songs);
        getSupportActionBar().hide();
        ListView listView = findViewById(R.id.myProducts);
        List<String> your_array_list = new ArrayList<>();
        for (Song song : ActivityMain.ctrl.readProductsDB())
            your_array_list.add(song.getTitle());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityClient.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);
    }

}
