package com.alex.exam.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.alex.exam.AppUtils.HTTPRequests.IP;
import static com.alex.exam.AppUtils.HTTPRequests.getBookmarksByType;

public class ActivitySection extends AppCompatActivity {
    private OkHttpClient client;
    private ProgressBar progressBar;
    private RelativeLayout rellay1;
    private ImageView refresh;
    private Button mySeenBookmarks;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            mySeenBookmarks.setVisibility(View.VISIBLE);
        }
    };
    private Runnable displayRefreshBtn = new Runnable() {
        @Override
        public void run() {
            refresh.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            rellay1.setVisibility(View.GONE);
            mySeenBookmarks.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        rellay1 = findViewById(R.id.rellay1);
        mySeenBookmarks = findViewById(R.id.myPurchaseProducts);
        refresh = findViewById(R.id.refreshImg);
        progressBar = findViewById(R.id.progressBar);
        Button addBtn = findViewById(R.id.addBtn);
        handler.postDelayed(runnable, 2000);
        getSupportActionBar().hide();

        ListView listView = findViewById(R.id.products);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnected()) {
            client = new OkHttpClient();
            Request request = new Request.Builder().url("ws://" + IP).build();
            EchoWebSocketListener listener = new EchoWebSocketListener();
            WebSocket ws = client.newWebSocket(request, listener);
            client.dispatcher().executorService().shutdown();
        } else {
            handler.postDelayed(displayRefreshBtn, 2000);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cd.isConnected())
                        Toast.makeText(ActivitySection.this, "Still no internet connection.", Toast.LENGTH_SHORT).show();
                    else goToMainAndBack();

                }
            });
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
        }

        mySeenBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivityMySeenBookmarks.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivityAddBookmark.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ActivityMain.types.size();
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
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_type_client, null);
            TextView genre = viewEvent.findViewById(R.id.t1);
            genre.setText(ActivityMain.types.get(position));

            Button seeBookmarks = viewEvent.findViewById(R.id.seeBookmarks);
            seeBookmarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBookmarksByType(ActivityMain.types.get(position), getApplicationContext());
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ActivityBookmarksByType.class);
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

    private class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.d("ClientActivity", "Waiting info...");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.d("Exam", "Receiving: " + text);

            goToMainAndBack();
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.d("Exam", "Receiving bytes: " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Log.d("Exam", "Closing: " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.d("Exam", "Error: " + t.getMessage());
        }
    }
}
