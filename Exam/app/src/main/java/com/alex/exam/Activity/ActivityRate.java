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

import com.alex.exam.Database.Bookmark;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;

import static com.alex.exam.AppUtils.AppUtils.sortBookmarksByRating;
import static com.alex.exam.AppUtils.HTTPRequests.deleteBookmark;
import static com.alex.exam.AppUtils.HTTPRequests.rateBookmark;

public class ActivityRate extends AppCompatActivity {

    private List<Bookmark> bookmarks;
    private ProgressBar progressBar;
    private RelativeLayout rellay1;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            bookmarks = sortBookmarksByRating();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bookmarks = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rellay1 = findViewById(R.id.rellay1);
        progressBar = findViewById(R.id.progressBar);
        handler.postDelayed(runnable, 2000);
        ListView listView = findViewById(R.id.productsList);
        getSupportActionBar().hide();
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bookmarks.size();
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
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_rate, null);
            viewEvent.setMinimumHeight(250);
            final Bookmark p = bookmarks.get(position);

            TextView name = viewEvent.findViewById(R.id.t1);
            name.setText(p.getName());

            TextView URL = viewEvent.findViewById(R.id.t3);
            URL.setText(p.getUrl());

            TextView Type = viewEvent.findViewById(R.id.t4);
            Type.setText(p.getType());

            TextView rating = viewEvent.findViewById(R.id.status);
            String r = String.valueOf(p.getRating());
            rating.setText(r);

            Button rateBtn = viewEvent.findViewById(R.id.rateBtn);
            rateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateBookmark(p.getID(), getApplicationContext());
                    Toast.makeText(ActivityRate.this, "Bookmark rated.", Toast.LENGTH_SHORT).show();
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
