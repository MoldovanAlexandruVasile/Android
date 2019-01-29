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

import com.alex.exam.Database.Product;
import com.alex.exam.R;

import java.util.ArrayList;
import java.util.List;

import static com.alex.exam.AppUtils.AppUtils.sortProductsByQuantity;
import static com.alex.exam.AppUtils.HTTPRequests.deleteProduct;
import static com.alex.exam.AppUtils.HTTPRequests.populateProducts;

public class ActivityClerk extends AppCompatActivity {

    private List<Product> products;
    private ProgressBar progressBar;
    private RelativeLayout rellay1;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            products = sortProductsByQuantity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        products = new ArrayList<>();
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
                Intent intent = new Intent(getApplicationContext(), ActivityAddProduct.class);
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
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_product_clerk, null);
            viewEvent.setMinimumHeight(250);
            final Product p = products.get(position);

            TextView name = viewEvent.findViewById(R.id.t1);
            name.setText(p.getName());

            TextView quantity = viewEvent.findViewById(R.id.t3);
            String q = String.valueOf(p.getQuantity());
            quantity.setText(q);

            TextView price = viewEvent.findViewById(R.id.t4);
            String pr = String.valueOf(p.getPrice());
            price.setText(pr);

            TextView status = viewEvent.findViewById(R.id.status);
            status.setText(p.getStatus());

            ImageView imageView = viewEvent.findViewById(R.id.removeProduct);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProduct(p.getID(), getApplicationContext());
                    populateProducts(getApplicationContext());
                    Toast.makeText(ActivityClerk.this, "Product deleted.", Toast.LENGTH_SHORT).show();
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
