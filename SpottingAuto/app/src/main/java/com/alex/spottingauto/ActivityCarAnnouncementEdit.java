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
import android.widget.TextView;
import android.widget.Toast;

import com.alex.spottingauto.Database.Announcement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ActivityCarAnnouncementEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Integer pos = Integer.valueOf(intent.getStringExtra("position"));

        List<Announcement> announcements = ActivityMain.myDatabase.DAO().getMyAnnouncements(ActivityMain.acct.getEmail());
        final Announcement announcement = announcements.get(pos);
        setFieldsDataFromAnnouncement(announcement);

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
                        ActivityMain.myDatabase.DAO().deleteAnnouncement(announcement);
                        Toast.makeText(getApplicationContext(), "DELETED...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        startActivity(intent);
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
                Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
            }
        });

        CardView updateCardView = findViewById(R.id.updateCardView);
        updateCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString();
                String offerType = offerTypeS.getSelectedItem().toString();
                String price = priceET.getText().toString();
                String currency = currencyS.getSelectedItem().toString();
                String brand = brandS.getSelectedItem().toString();
                String model = modelS.getSelectedItem().toString();
                String year = yearS.getSelectedItem().toString();
                String color = colorS.getSelectedItem().toString();
                String fuelType = fuelTypeS.getSelectedItem().toString();
                String transmission = transmissionS.getSelectedItem().toString();
                String kmOnBoard = kmOnBoardET.getText().toString();
                String kmOrMiles = kmOmS.getSelectedItem().toString();
                String engineCapacity = engineCS.getSelectedItem().toString();
                String doors = doorS.getSelectedItem().toString();
                String seats = seatS.getSelectedItem().toString();
                String contact = contactET.getText().toString();
                String description = descriptionET.getText().toString();
                announcement.setTitle(title);
                announcement.setOfferType(offerType);
                announcement.setPrice(price);
                announcement.setCurrency(currency);
                announcement.setBrand(brand);
                announcement.setModel(model);
                announcement.setYear(year);
                announcement.setColor(color);
                announcement.setFuelType(fuelType);
                announcement.setTransmission(transmission);
                announcement.setOnBoardKM(kmOnBoard);
                announcement.setKmOrMiles(kmOrMiles);
                announcement.setEngineCapacity(engineCapacity);
                announcement.setDoorsNumber(doors);
                announcement.setSeatsNumber(seats);
                announcement.setContact(contact);
                announcement.setDescription(description);
                announcement.setAutor(ActivityMain.acct.getEmail());
                ActivityMain.myDatabase.DAO().updateAnnouncement(announcement);
                Toast.makeText(getApplicationContext(), "UPDATED...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFieldsDataFromAnnouncement(Announcement announcement) {
        EditText titleET = findViewById(R.id.titleET);
        titleET.setText(announcement.getTitle());
        Integer pos;

        Spinner offerS = findViewById(R.id.offerSpinner);
        pos = findPositionInSpinner(offerS, announcement.getOfferType());
        offerS.setSelection(pos);

        EditText priceET = findViewById(R.id.priceET);
        priceET.setText(announcement.getPrice());

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
        String[] values = {"Sell", "Trade", "Not available"};
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
