package com.alex.spottingauto;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class FragmentCarsList extends Fragment {
    private static RequestQueue requestQueue;
    private static final String ANNOUNCEMENT_URL = "http://192.168.0.102:8012/Announcements/announcementcontroller.php?view=all";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Announcements");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View carsListView = inflater.inflate(R.layout.layout_cars_list, container, false);
        ListView listView = carsListView.findViewById(R.id.carsList);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ActivityCarAnnouncementDetails.class);
                intent.putExtra("position", String.valueOf(i));
                startActivity(intent);
                getActivity().finish();
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ANNOUNCEMENT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        Announcement announcement = new Gson().fromJson(response.toString(), Announcement.class);
                        Log.e("Rest", announcement.getautor());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                });

        requestQueue.add(objectRequest);
        return carsListView;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 1;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View viewEvent = layoutInflater.inflate(R.layout.layout_custom_row_car_list, null);
            final String DB_IMAGE_URL = "image_url";
            final String DB_TITLE = "title";
            final String DB_BRAND = "brand";
            final String DB_YEAR = "year";
            try {
                ImageView imageView = viewEvent.findViewById(R.id.logoImg);
                //Drawable drawable = Drawable.createFromStream((InputStream) new URL(announcementsArray.getJSONObject(position).getString(DB_IMAGE_URL)).getContent(), "src");
                //imageView.setImageDrawable(drawable);
                TextView titleTV = viewEvent.findViewById(R.id.titleTV);
                TextView brand = viewEvent.findViewById(R.id.brandTextView);
                TextView year = viewEvent.findViewById(R.id.yearTextView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return viewEvent;
        }
    }
}