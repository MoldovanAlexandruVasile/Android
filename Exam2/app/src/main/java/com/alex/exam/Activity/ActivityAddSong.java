package com.alex.exam.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.exam.R;

import static com.alex.exam.AppUtils.HTTPRequests.addSong;

public class ActivityAddSong extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        getSupportActionBar().hide();
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText title = findViewById(R.id.t1);
                final EditText description = findViewById(R.id.t2);
                final EditText album = findViewById(R.id.t3);
                final EditText genre = findViewById(R.id.t4);
                final EditText year = findViewById(R.id.t5);
                final String n = title.getText().toString();
                final String d = description.getText().toString();
                final String q = album.getText().toString();
                final String p = genre.getText().toString();
                final String y = year.getText().toString();
                addSong(n, d, q, p, y, getApplicationContext());
                Toast.makeText(ActivityAddSong.this, "Song added.", Toast.LENGTH_SHORT).show();
                goBackToMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityClerk.class);
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