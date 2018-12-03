package com.alex.spottingauto;

import android.content.Intent;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyAnnouncements extends Fragment {
    //private static final String ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/announcementcontroller.php?view=all";
    private static final String ANNOUNCEMENT_URL = "http://192.168.0.102:8012/Announcements/announcementcontroller.php?view=all";
    private static List<Announcement> announcementList;
    private static RequestQueue requestQueue;
    private static CustomAdapter customAdapter;
    private static ListView listView;
    private static View carsListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("My announcements");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        announcementList = new ArrayList<>();
        carsListView = inflater.inflate(R.layout.layout_my_announcements, container, false);
        listView = carsListView.findViewById(R.id.myCarsList);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ANNOUNCEMENT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("announcement");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Announcement announcement = new Gson().fromJson(resultsArray.get(i).toString(), Announcement.class);
                                if (announcement.getAutor().equals(ActivityMain.acct.getEmail()))
                                    announcementList.add(announcement);
                            }
                            customAdapter = new CustomAdapter();
                            listView.setAdapter(customAdapter);
                            listView.setAdapter(customAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(getActivity(), ActivityCarAnnouncementEdit.class);
                                    intent.putExtra("position", String.valueOf(i));
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    getActivity().finish();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
            return announcementList.size();
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
            try {
                Announcement announcement = announcementList.get(position);
                ImageView imageView = viewEvent.findViewById(R.id.logoImg);
                if (!announcement.getImage_url().isEmpty())
                    Glide.with(getActivity().getApplicationContext())
                            .load(announcement.getImage_url())
                            .into(imageView);
                TextView titleTV = viewEvent.findViewById(R.id.titleTV);
                titleTV.setText(announcement.getTitle());
                TextView brand = viewEvent.findViewById(R.id.brandTextView);
                brand.setText(announcement.getBrand());
                TextView year = viewEvent.findViewById(R.id.yearTextView);
                year.setText(announcement.getYear());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return viewEvent;
        }
    }
}
