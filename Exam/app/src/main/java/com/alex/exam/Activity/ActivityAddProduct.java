package com.alex.exam.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.exam.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.alex.exam.AppUtils.HTTPRequests.IP;
import static com.alex.exam.AppUtils.HTTPRequests.addProduct;

public class ActivityAddProduct extends AppCompatActivity {
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = new OkHttpClient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
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
                addProduct(n, d, q, p, getApplicationContext());
                Toast.makeText(ActivityAddProduct.this, "Product added.", Toast.LENGTH_SHORT).show();
                //
                Request request = new Request.Builder().url("ws://" + IP).build();
                EchoWebSocketListener listener = new EchoWebSocketListener(n);
                WebSocket ws = client.newWebSocket(request, listener);
                ws.send("New product: " + n);
                client.dispatcher().executorService().shutdown();
                //
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

    public void output(final String txt) {
        runOnUiThread(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                Toast.makeText(ActivityAddProduct.this, txt, Toast.LENGTH_SHORT);
            }
        });
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        private String product;

        public EchoWebSocketListener(String p) {
            this.product = p;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("Hello, it's SSaurel !");
            webSocket.send("What's up ?");
            webSocket.send(ByteString.decodeHex("deadbeef"));
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("Receiving " + product);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error " + t.getMessage());
        }
    }
}