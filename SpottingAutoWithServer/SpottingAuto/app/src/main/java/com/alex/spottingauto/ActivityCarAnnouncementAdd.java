package com.alex.spottingauto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCarAnnouncementAdd extends AppCompatActivity {
    //private static final String INSERT_ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/insertAnnouncement.php";
    private static final String INSERT_ANNOUNCEMENT_URL = "http://192.168.0.103:8012/Announcements/insertAnnouncement.php";
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Add");
        this.getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_add_announcement);
        ImageView image = findViewById(R.id.carImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

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
        fillColorSpinner("Colors");

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
                else if (item.equals("Lamborghini"))
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

        final EditText titleET = findViewById(R.id.titleET);
        final Spinner offerTypeS = findViewById(R.id.offerSpinner);
        final EditText priceET = findViewById(R.id.priceET);
        final Spinner currencyS = findViewById(R.id.currencySpinner);
        final Spinner brandS = findViewById(R.id.brandSpinner);
        final Spinner modelS = findViewById(R.id.modelSpinner);
        final Spinner yearS = findViewById(R.id.yearSpinner);
        final Spinner colorS = findViewById(R.id.colorSpinner);
        final Spinner fuelTypeS = findViewById(R.id.fuelSpinner);
        final Spinner transmissionS = findViewById(R.id.transmissionSpinner);
        final EditText kmOnBoardET = findViewById(R.id.kmOnBoardET);
        final Spinner kmOmS = findViewById(R.id.kmOrMilesSpinner);
        final Spinner engineCS = findViewById(R.id.engineCapacitySpinner);
        final Spinner doorS = findViewById(R.id.doorsNoSpinner);
        final Spinner seatS = findViewById(R.id.seatsNoSpinner);
        final EditText contactET = findViewById(R.id.contactET);
        final EditText descriptionET = findViewById(R.id.descriptionET);

        if (ActivityMain.offlineManagement.size() != 0 && new ConnectionDetector(getApplicationContext()).isConnected()) {
            while (ActivityMain.offlineManagement.size() > 0) {
                Announcement currentAnnouncement = ActivityMain.offlineManagement.get(0);
                final String title = currentAnnouncement.getTitle();
                final String offerType = currentAnnouncement.getOfferType();
                final String price = currentAnnouncement.getPrice();
                final String currencyFinal = currentAnnouncement.getCurrency();
                final String brand = currentAnnouncement.getBrand();
                final String model = currentAnnouncement.getModel();
                final String year = currentAnnouncement.getYear();
                final String color = currentAnnouncement.getColor();
                final String fuelType = currentAnnouncement.getFuelType();
                final String transmission = currentAnnouncement.getTransmission();
                final String kmOnBoard = currentAnnouncement.getOnBoardKM();
                final String kmOrMiles = currentAnnouncement.getKmOrMiles();
                final String engineCapacity = currentAnnouncement.getEngineCapacity();
                final String doorsNumber = currentAnnouncement.getDoorsNumber();
                final String seatsNumber = currentAnnouncement.getSeatsNumber();
                final String contact = currentAnnouncement.getContact();
                final String description = currentAnnouncement.getDescription();

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, INSERT_ANNOUNCEMENT_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("image_url", "");
                        parameters.put("autor", ActivityMain.acct.getEmail());
                        parameters.put("title", title);
                        parameters.put("offerType", offerType);
                        parameters.put("price", price);
                        parameters.put("currency", currencyFinal);
                        parameters.put("brand", brand);
                        parameters.put("model", model);
                        parameters.put("year", year);
                        parameters.put("color", color);
                        parameters.put("fuelType", fuelType);
                        parameters.put("transmission", transmission);
                        parameters.put("onBoardKM", kmOnBoard);
                        parameters.put("kmOrMiles", kmOrMiles);
                        parameters.put("engineCapacity", engineCapacity);
                        parameters.put("doorsNumber", doorsNumber);
                        parameters.put("seatsNumber", seatsNumber);
                        parameters.put("contact", contact);
                        parameters.put("description", description);
                        return parameters;
                    }
                };
                requestQueue.add(request);
                ActivityMain.offlineManagement.remove(0);
            }
        }

        CardView saveCardView = findViewById(R.id.saveCardView);
        saveCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                final String title = titleET.getText().toString();
                final String offerType = offerTypeS.getSelectedItem().toString();
                final String price = priceET.getText().toString();
                final String currency = currencyS.getSelectedItem().toString();
                String currencyText = "";
                if (currency.equals("€"))
                    currencyText = "Euro";
                else if (currency.equals("£"))
                    currencyText = "Lyra";
                else if (currency.equals("$"))
                    currencyText = "Dollar";
                final String currencyFinal = currencyText;
                final String brand = brandS.getSelectedItem().toString();
                final String model = modelS.getSelectedItem().toString();
                final String year = yearS.getSelectedItem().toString();
                final String color = colorS.getSelectedItem().toString();
                final String fuelType = fuelTypeS.getSelectedItem().toString();
                final String transmission = transmissionS.getSelectedItem().toString();
                final String kmOnBoard = kmOnBoardET.getText().toString();
                final String kmOrMiles = kmOmS.getSelectedItem().toString();
                final String engineCapacity = engineCS.getSelectedItem().toString();
                final String doorsNumber = doorS.getSelectedItem().toString();
                final String seatsNumber = seatS.getSelectedItem().toString();
                final String contact = contactET.getText().toString();
                final String description = descriptionET.getText().toString();
                if (cd.isConnected()) {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, INSERT_ANNOUNCEMENT_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> parameters = new HashMap<>();
                            parameters.put("image_url", "");
                            parameters.put("autor", ActivityMain.acct.getEmail());
                            parameters.put("title", title);
                            parameters.put("offerType", offerType);
                            parameters.put("price", price);
                            parameters.put("currency", currencyFinal);
                            parameters.put("brand", brand);
                            parameters.put("model", model);
                            parameters.put("year", year);
                            parameters.put("color", color);
                            parameters.put("fuelType", fuelType);
                            parameters.put("transmission", transmission);
                            parameters.put("onBoardKM", kmOnBoard);
                            parameters.put("kmOrMiles", kmOrMiles);
                            parameters.put("engineCapacity", engineCapacity);
                            parameters.put("doorsNumber", doorsNumber);
                            parameters.put("seatsNumber", seatsNumber);
                            parameters.put("contact", contact);
                            parameters.put("description", description);
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Toast.makeText(getApplicationContext(), "ANNOUNCEMENT ADDED...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityCarAnnouncementAdd.this, ActivityMain.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Your announcement will be added when you will be online",
                            Toast.LENGTH_SHORT).show();
                    Announcement newAnnouncement = new Announcement("", ActivityMain.acct.getEmail(), title, offerType,
                            price, currencyFinal, brand, model, year, color, fuelType, transmission, kmOnBoard, kmOrMiles,
                            engineCapacity, doorsNumber, seatsNumber, contact, description);
                    ActivityMain.offlineManagement.add(newAnnouncement);
                }
            }
        });
    }

    private void fillColorSpinner(String fileName) {
        String[] values = readFromFile(fileName);
        Spinner spinner = this.findViewById(R.id.colorSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
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
        startActivity(new Intent(ActivityCarAnnouncementAdd.this, ActivityMain.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(ActivityCarAnnouncementAdd.this, ActivityMain.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.carImage);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }

    private void openGallery() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
    }
}
