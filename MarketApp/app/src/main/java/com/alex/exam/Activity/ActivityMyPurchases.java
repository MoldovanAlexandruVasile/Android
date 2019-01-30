package com.alex.exam.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.exam.Database.Product;
import com.alex.exam.R;

import java.util.List;

public class ActivityMyPurchases extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        getSupportActionBar().hide();
        ListView listView = findViewById(R.id.myProducts);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {
        private List<Product> products;

        public CustomAdapter() {
            products = ActivityMain.ctrl.readProductsDB();
        }

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
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_my_product, null);
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
            return viewEvent;
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        Intent intent = new Intent(getApplicationContext(), ActivityClient.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(intent);
    }

}
