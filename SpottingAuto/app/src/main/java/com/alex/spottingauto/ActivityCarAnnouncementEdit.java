package com.alex.spottingauto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityCarAnnouncementEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Edit");
        this.getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_car_announcement_edit);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout gallery = this.findViewById(R.id.gallery);
        for (int i = 0; i <= 10; i++) {
            View view = inflater.inflate(R.layout.layout_car_announcement_details_image, gallery, false);
            ImageView imageView = view.findViewById(R.id.carImageDetails);
            imageView.setImageResource(R.drawable.audi2018);
            gallery.addView(view);
        }
        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
        fillOfferSpinner();
        fillBrandSpinner();
        fillModelSpinner("AcuraModels");
        fillYearSpinner();
        fillFuelSpinner();
        fillTransmissionSpinner();
        fillDoorsSpinner();
        fillSeatsSpinner();
        fillKmOrMilesSpinner();
        fillCurrencySpinner();
        fillEngineCapacity();

        final Spinner brandSpinner = this.findViewById(R.id.brandSpinner);
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String item = brandSpinner.getSelectedItem().toString();
                if (item.equals("Acura"))
                    fillModelSpinner("AcuraModels");
                else if (item.equals("Alfa Romeo"))
                    fillModelSpinner("AlfaRomeoModels");
                else if (item.equals("Aston Martin"))
                    fillModelSpinner("AstonMartinModels");
                else if (item.equals("Audi"))
                    fillModelSpinner("AudiModels");
                else if (item.equals("Bentley"))
                    fillModelSpinner("BentleyModels");
                else if (item.equals("BMW"))
                    fillModelSpinner("BMWModels");
                else if (item.equals("Bugatti"))
                    fillModelSpinner("BugattiModels");
                else if (item.equals("Buick"))
                    fillModelSpinner("BuickModels");
                else if (item.equals("Cadillac"))
                    fillModelSpinner("CadillacModels");
                else if (item.equals("Chevrolet"))
                    fillModelSpinner("ChevroletModels");
                else if (item.equals("Chrysler"))
                    fillModelSpinner("ChryslerModels");
                else if (item.equals("Citroen"))
                    fillModelSpinner("CitroenModels");
                else if (item.equals("Dodge"))
                    fillModelSpinner("DodgeModels");
                else if (item.equals("Ferrari"))
                    fillModelSpinner("FerrariModels");
                else if (item.equals("Fiat"))
                    fillModelSpinner("FiatModels");
                else if (item.equals("Ford"))
                    fillModelSpinner("FordModels");
                else if (item.equals("Geely"))
                    fillModelSpinner("GeelyModels");
                else if (item.equals("Honda"))
                    fillModelSpinner("HondaModels");
                else if (item.equals("Hyundai"))
                    fillModelSpinner("HyundaiModels");
                else if (item.equals("Infiniti"))
                    fillModelSpinner("InfinitiModels");
                else if (item.equals("Jaguar"))
                    fillModelSpinner("JaguarModels");
                else if (item.equals("Jeep"))
                    fillModelSpinner("JeepModels");
                else if (item.equals("Kia"))
                    fillModelSpinner("KiaModels");
                else if (item.equals("Koenigsegg"))
                    fillModelSpinner("KoenigseggModels");
                else if (item.equals("LamborghiniModels"))
                    fillModelSpinner("LamborghiniModels");
                else if (item.equals("Land Rover"))
                    fillModelSpinner("LandRoverModels");
                else if (item.equals("Lexus"))
                    fillModelSpinner("LexusModels");
                else if (item.equals("Maserati"))
                    fillModelSpinner("MaseratiModels");
                else if (item.equals("Mazda"))
                    fillModelSpinner("MazdaModels");
                else if (item.equals("McLaren"))
                    fillModelSpinner("McLarenModels");
                else if (item.equals("Mercedes-Benz"))
                    fillModelSpinner("MercedesBenzModels");
                else if (item.equals("Mini"))
                    fillModelSpinner("MiniModels");
                else if (item.equals("Mitsubishi"))
                    fillModelSpinner("MitsubishiModels");
                else if (item.equals("Nissan"))
                    fillModelSpinner("NissanModels");
                else if (item.equals("Pagani"))
                    fillModelSpinner("PaganiModels");
                else if (item.equals("Peugeot"))
                    fillModelSpinner("PeugeotModels");
                else if (item.equals("Porsche"))
                    fillModelSpinner("PorscheModels");
                else if (item.equals("Ram"))
                    fillModelSpinner("RamModels");
                else if (item.equals("Renault"))
                    fillModelSpinner("RenaultModels");
                else if (item.equals("Rolls Royce"))
                    fillModelSpinner("RollsRoyceModels");
                else if (item.equals("Saab"))
                    fillModelSpinner("SaabModels");
                else if (item.equals("Subaru"))
                    fillModelSpinner("SubaruModels");
                else if (item.equals("Suzuki"))
                    fillModelSpinner("SuzukiModels");
                else if (item.equals("Tata Motors"))
                    fillModelSpinner("TataMotorsModels");
                else if (item.equals("Tesla"))
                    fillModelSpinner("TeslaModels");
                else if (item.equals("Toyota"))
                    fillModelSpinner("ToyotaModels");
                else if (item.equals("Volkswagen"))
                    fillModelSpinner("VolkswagenModels");
                else if (item.equals("Volvo"))
                    fillModelSpinner("VolvoModels");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fillEngineCapacity() {
        String[] values = {"1.0", "1.2", "1.4", "1.6", "1.8", "1.9", "2.0", "3.0"};
        Spinner spinner = this.findViewById(R.id.engineCapacitySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }


    private void fillTransmissionSpinner() {
        String[] values = {"Manual", "Automated"};
        Spinner spinner = this.findViewById(R.id.transmissionSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillDoorsSpinner() {
        String[] values = {"2", "3", "4", "5"};
        Spinner spinner = this.findViewById(R.id.doorsNoSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillCurrencySpinner() {
        String[] values = {"€", "$", "£"};
        Spinner spinner = this.findViewById(R.id.currencySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillKmOrMilesSpinner() {
        String[] values = {"KM", "Miles"};
        Spinner spinner = this.findViewById(R.id.kmOrMilesSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillSeatsSpinner() {
        String[] values = {"2", "3", "4", "5"};
        Spinner spinner = this.findViewById(R.id.seatsNoSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillOfferSpinner() {
        String[] values = {"Sell", "Trade"};
        Spinner spinner = this.findViewById(R.id.offerSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillFuelSpinner() {
        String[] values = {"Gasoline", "Diesel"};
        Spinner spinner = this.findViewById(R.id.fuelSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillBrandSpinner() {
        String[] values = readFromFile("Brand.txt");
        Spinner spinner = this.findViewById(R.id.brandSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillYearSpinner() {
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> years = new ArrayList<>();
        for (Integer y = year; y >= 1950; y--)
            years.add(y);
        String[] values = new String[years.size()];
        for (Integer i = 0; i < years.size(); i++)
            values[i] = String.valueOf(years.get(i));
        Spinner spinner = this.findViewById(R.id.yearSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private void fillModelSpinner(String fileName) {
        String[] values = readFromFile(fileName);
        Spinner spinner = this.findViewById(R.id.modelSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    private String[] readFromFile(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            List<String> list = new ArrayList<>();
            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
            String[] data = new String[list.size()];
            for (Integer i = 0; i < list.size(); i++)
                data[i] = list.get(i);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityCarAnnouncementEdit.this, ActivityMain.class));
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(ActivityCarAnnouncementEdit.this, ActivityMain.class));
        this.finish();
        return true;
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }
}
