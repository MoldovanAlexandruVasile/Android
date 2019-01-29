package com.alex.exam.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.exam.AppUtils.ConnectionDetector;
import com.alex.exam.Database.Product;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.alex.exam.AppUtils.AppUtils.getAvailable;
import static com.alex.exam.AppUtils.HTTPRequests.buyProductServer;

public class ActivityClient extends AppCompatActivity {

    private List<Product> products;
    private ProgressBar progressBar;
    private RelativeLayout rellay1;
    private ImageView refresh;
    private Button myPurchases;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            myPurchases.setVisibility(View.VISIBLE);
            products = getAvailable();
        }
    };
    private Runnable displayRefreshBtn = new Runnable() {
        @Override
        public void run() {
            refresh.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            rellay1.setVisibility(View.GONE);
            myPurchases.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        products = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        rellay1 = findViewById(R.id.rellay1);
        myPurchases = findViewById(R.id.myPurchaseProducts);
        refresh = findViewById(R.id.refreshImg);
        progressBar = findViewById(R.id.progressBar);
        handler.postDelayed(runnable, 2000);
        getSupportActionBar().hide();

        ListView listView = findViewById(R.id.products);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnected()) {

        } else {
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

        myPurchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivityMyPurchases.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return products.size();
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
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_product_client, null);
            viewEvent.setMinimumHeight(250);
            final Product p = products.get(position);
            final Button buyBtn = viewEvent.findViewById(R.id.buyBtn);
            buyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyProduct(p);
                }
            });

            TextView name = viewEvent.findViewById(R.id.t1);
            name.setText(p.getName());

            TextView quantity = viewEvent.findViewById(R.id.t3);
            String q = String.valueOf(p.getQuantity());
            quantity.setText(q);

            TextView price = viewEvent.findViewById(R.id.t4);
            String pr = String.valueOf(p.getPrice());
            price.setText(pr);
            return viewEvent;
        }
    }

    private void buyProduct(final Product p) {
        final Dialog customDialog = new Dialog(ActivityClient.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_buy_pop_up);
        TextView title = customDialog.findViewById(R.id.t1);
        title.setText(p.getName());
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText quantityET = customDialog.findViewById(R.id.t3);
                String quantity = quantityET.getText().toString();
                if (quantity.isEmpty())
                    Toast.makeText(ActivityClient.this, "Please fill quantity.", Toast.LENGTH_SHORT).show();
                else if (Integer.valueOf(quantity) > p.getQuantity())
                    Toast.makeText(ActivityClient.this, "Not enough quantity.", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(ActivityClient.this, "Product bought.", Toast.LENGTH_SHORT).show();
                    buyProductServer(p.getID(), Integer.valueOf(quantity), getApplicationContext());
                    showInfo(p, customDialog, quantity);
                    p.setQuantity(Integer.valueOf(quantity));
                    p.setPrice(Integer.valueOf(quantity) * p.getPrice());
                    ActivityMain.ctrl.insertProduct(p);
                }
            }
        });

        CardView noCardView = customDialog.findViewById(R.id.noPopUpCardView);
        noCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void showInfo(Product p, final Dialog prevDialog, String q) {
        final Dialog customDialog = new Dialog(ActivityClient.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_info_product);
        TextView name = customDialog.findViewById(R.id.name);
        TextView description = customDialog.findViewById(R.id.description);
        TextView quantity = customDialog.findViewById(R.id.quantity);
        TextView price = customDialog.findViewById(R.id.price);
        name.setText(p.getName());
        description.setText(p.getDescription());
        quantity.setText(q);
        Integer priceC = Integer.valueOf(q) * p.getPrice();
        price.setText(String.valueOf(priceC));
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                prevDialog.dismiss();
                goToMainAndBack();
            }
        });

        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
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
