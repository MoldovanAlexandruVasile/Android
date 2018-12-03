package com.alex.spottingauto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCarAnnouncementEdit extends AppCompatActivity {
//    private static final String ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/announcementcontroller.php?view=all";
//    private static final String DELETE_ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/deleteAnnouncement.php";
//    private static final String UPDATE_ANNOUNCEMENT_URL = "http://192.168.43.22:8012/Announcements/updateAnnouncement.php";
private static final String ANNOUNCEMENT_URL = "http://192.168.0.102:8012/Announcements/announcementcontroller.php?view=all";
    private static final String DELETE_ANNOUNCEMENT_URL = "http://192.168.0.102:8012/Announcements/deleteAnnouncement.php";
    private static final String UPDATE_ANNOUNCEMENT_URL = "http://192.168.0.102:8012/Announcements/updateAnnouncement.php";
    private static List<Announcement> announcementList;
    private static RequestQueue requestQueue, requestDeleteQueue, requestUpdateQueue;
    private static Announcement announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        announcementList = new ArrayList<>();
        this.setTitle("Edit");
        this.getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_car_announcement_edit);

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

        Intent intent = getIntent();
        final Integer pos = Integer.valueOf(intent.getStringExtra("position"));
        requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                            Switch avalability = findViewById(R.id.availabilitySwitch);
                            announcement = announcementList.get(pos);
                            if (announcement.getOfferType().equals("Not available"))
                                avalability.setChecked(false);
                            else avalability.setChecked(true);
                            setFieldsDataFromAnnouncement(announcement);
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

        CardView deleteCardView = findViewById(R.id.deleteCardView);
        deleteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                if (cd.isConnected()) {
                    final Dialog customDialog = new Dialog(ActivityCarAnnouncementEdit.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setCanceledOnTouchOutside(false);
                    customDialog.setContentView(R.layout.layout_custom_pop_up);
                    TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
                    textView.setText(R.string.delete_announcement);
                    CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);
                    yesCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestDeleteQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest request = new StringRequest(Request.Method.POST, DELETE_ANNOUNCEMENT_URL, new Response.Listener<String>() {
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
                                    String ID = String.valueOf(announcement.getID());
                                    Log.e("ID", ID);
                                    parameters.put("ID", ID);
                                    return parameters;
                                }
                            };
                            requestDeleteQueue.add(request);
                            Toast.makeText(getApplicationContext(), "DELETED...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            finish();
                        }
                    });

                    CardView noCardView = customDialog.findViewById(R.id.noPopUpCardView);
                    noCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                        }
                    });
                    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customDialog.show();
                } else
                    Toast.makeText(getApplicationContext(), "CANNOT PERFORM OPERATION WHILE OFFLINE", Toast.LENGTH_SHORT).show();
            }
        });

        CardView updateCardView = findViewById(R.id.updateCardView);
        updateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                if (cd.isConnected()) {
                    final String title = titleET.getText().toString();
                    final String offerType = offerTypeS.getSelectedItem().toString();
                    final String price = priceET.getText().toString();

                    String currency = currencyS.getSelectedItem().toString();
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
                    Switch avalability = findViewById(R.id.availabilitySwitch);
                    final Boolean toggle = avalability.isChecked();

                    requestUpdateQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, UPDATE_ANNOUNCEMENT_URL, new Response.Listener<String>() {
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
                            parameters.put("ID", String.valueOf(announcement.getID()));
                            parameters.put("image_url", "");
                            parameters.put("autor", ActivityMain.acct.getEmail());
                            parameters.put("title", title);
                            if (toggle)
                                parameters.put("offerType", offerType);
                            else parameters.put("offerType", "Not available");
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
                    requestUpdateQueue.add(request);
                    Toast.makeText(getApplicationContext(), "UPDATED...", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "CANNOT PERFORM OPERATION WHILE OFFLINE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFieldsDataFromAnnouncement(Announcement announcement) {
        ImageView imageView = findViewById(R.id.carImage);
        if (!announcement.getImage_url().isEmpty())
            Glide.with(getApplicationContext())
                    .load(announcement.getImage_url())
                    .into(imageView);

        EditText titleET = findViewById(R.id.titleET);
        titleET.setText(announcement.getTitle());
        Integer pos;

        Spinner offerS = findViewById(R.id.offerSpinner);
        pos = findPositionInSpinner(offerS, announcement.getOfferType());
        offerS.setSelection(pos);

        EditText priceET = findViewById(R.id.priceET);
        priceET.setText(announcement.getPrice());

        String currency = "";
        if (announcement.getCurrency().equals("Euro"))
            currency = "€";
        else if (announcement.getCurrency().equals("Lyra"))
            currency = "£";
        else if (announcement.getCurrency().equals("Dollar"))
            currency = "$";
        Spinner currencyS = findViewById(R.id.currencySpinner);
        pos = findPositionInSpinner(currencyS, currency);
        currencyS.setSelection(pos);

        Spinner brandS = findViewById(R.id.brandSpinner);
        pos = findPositionInSpinner(brandS, announcement.getBrand());
        brandS.setSelection(pos);

        Spinner modelS = findViewById(R.id.modelSpinner);
        pos = findPositionInSpinner(modelS, announcement.getModel());
        modelS.setSelection(pos);

        Spinner yearS = findViewById(R.id.yearSpinner);
        pos = findPositionInSpinner(yearS, announcement.getYear());
        yearS.setSelection(pos);

        Spinner colorS = findViewById(R.id.colorSpinner);
        pos = findPositionInSpinner(colorS, announcement.getColor());
        colorS.setSelection(pos);

        Spinner fuelS = findViewById(R.id.fuelSpinner);
        pos = findPositionInSpinner(fuelS, announcement.getFuelType());
        fuelS.setSelection(pos);

        Spinner transmissionS = findViewById(R.id.transmissionSpinner);
        pos = findPositionInSpinner(transmissionS, announcement.getTransmission());
        transmissionS.setSelection(pos);

        EditText kmOnBoard = findViewById(R.id.kmOnBoardET);
        kmOnBoard.setText(announcement.getOnBoardKM());

        Spinner kmOrMilesS = findViewById(R.id.kmOrMilesSpinner);
        pos = findPositionInSpinner(kmOrMilesS, announcement.getKmOrMiles());
        kmOrMilesS.setSelection(pos);

        Spinner engineCS = findViewById(R.id.engineCapacitySpinner);
        pos = findPositionInSpinner(engineCS, announcement.getEngineCapacity());
        engineCS.setSelection(pos);

        Spinner doorsS = findViewById(R.id.doorsNoSpinner);
        pos = findPositionInSpinner(doorsS, announcement.getDoorsNumber());
        doorsS.setSelection(pos);

        Spinner seatsS = findViewById(R.id.seatsNoSpinner);
        pos = findPositionInSpinner(seatsS, announcement.getSeatsNumber());
        seatsS.setSelection(pos);

        EditText contactET = findViewById(R.id.contactET);
        contactET.setText(announcement.getContact());

        EditText descriptionET = findViewById(R.id.descriptionET);
        descriptionET.setText(announcement.getDescription());
    }

    private Integer findPositionInSpinner(Spinner spinner, String text) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(text)) {
                return i;
            }
        }
        return -1;
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

    private void openGallery() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
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
        startActivity(new Intent(ActivityCarAnnouncementEdit.this, ActivityMain.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(ActivityCarAnnouncementEdit.this, ActivityMain.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
