package com.alex.spottingauto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.spottingauto.Database.Announcement;

import java.util.List;

public class FragmentCarsList extends Fragment {

    private static List<Announcement> announcements;

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
        announcements = ActivityMain.myDatabase.DAO().getAnnouncements();
        if (announcements.size() == 0) {
            carsListView = inflater.inflate(R.layout.layout_my_announcements_empty, container, false);
            return carsListView;
        } else {
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
        }
        return carsListView;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return announcements.size();
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
            if (!announcements.get(position).getOfferType().toUpperCase().equals("NOT AVAILABLE")) {
                TextView titleTV = viewEvent.findViewById(R.id.titleTV);
                titleTV.setText(announcements.get(position).getTitle());
                TextView brand = viewEvent.findViewById(R.id.brandTextView);
                brand.setText(announcements.get(position).getBrand());
                TextView year = viewEvent.findViewById(R.id.yearTextView);
                year.setText(announcements.get(position).getYear());
            }
            return viewEvent;
        }
    }
}