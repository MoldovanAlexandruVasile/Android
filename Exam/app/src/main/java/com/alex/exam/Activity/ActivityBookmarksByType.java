package com.alex.exam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.exam.Database.Bookmark;
import com.alex.exam.R;

import static com.alex.exam.AppUtils.HTTPRequests.deleteBookmark;

public class ActivityBookmarksByType extends AppCompatActivity {
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
        setContentView(R.layout.activity_bookmarks_by_type);
        getSupportActionBar().hide();
        listView = findViewById(R.id.bookmarks);
        progressBar = findViewById(R.id.progressBar);
        handler.postDelayed(runnable, 2000);
        listView.setAdapter(new CustomAdapter());
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ActivityMain.bookmarksByType.size();
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
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_bookmarks_by_type, null);
            viewEvent.setMinimumHeight(250);
            final Bookmark p = ActivityMain.bookmarksByType.get(position);

            TextView name = viewEvent.findViewById(R.id.t1);
            name.setText(p.getName());

            TextView URL = viewEvent.findViewById(R.id.t3);
            URL.setText(p.getUrl());

            TextView Type = viewEvent.findViewById(R.id.t4);
            Type.setText(p.getType());

            TextView rating = viewEvent.findViewById(R.id.status);
            String r = String.valueOf(p.getRating());
            rating.setText(r);

            Button deleteBtn = viewEvent.findViewById(R.id.deleteBtn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteBookmark(p.getID(), getApplicationContext());
                    if (ActivityMain.ctrl.readBookmarksDB().contains(p))
                        ActivityMain.ctrl.deleteBookmark(p.getID());
                    Toast.makeText(ActivityBookmarksByType.this, "Bookmark deleted.", Toast.LENGTH_SHORT).show();
                    goToMainAndBack();
                }
            });
            return viewEvent;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivitySection.class);
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
