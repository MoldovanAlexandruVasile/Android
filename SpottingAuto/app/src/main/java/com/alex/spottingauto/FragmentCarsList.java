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

public class FragmentCarsList extends Fragment {

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
                if (i == 0) {
                    startActivity(new Intent(getActivity(), ActivityCarAnnouncementDetails.class));
                    getActivity().finish();
                }
            }
        });
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
            return viewEvent;
        }
    }
}