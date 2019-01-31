package com.alex.exam.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.exam.R;

import static com.alex.exam.AppUtils.HTTPRequests.addBookmark;

public class ActivityAddBookmark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookmark);
        getSupportActionBar().hide();
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText name = findViewById(R.id.t1);
                final EditText description = findViewById(R.id.t2);
                final EditText quantity = findViewById(R.id.t3);
                final EditText price = findViewById(R.id.t4);
                final String n = name.getText().toString();
                final String d = description.getText().toString();
                final String q = quantity.getText().toString();
                final String p = price.getText().toString();
                addBookmark(n, d, q, p, getApplicationContext());
                Toast.makeText(ActivityAddBookmark.this, "Bookmark added.", Toast.LENGTH_SHORT).show();
                goBackToMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityRate.class);
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