package com.alex.spottingauto;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleApiClient googleApiClient;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.drawable.gradient);
        setSupportActionBar(toolbar);

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            NavigationView navigationView = findViewById(R.id.nav_view);
            ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.profilePicture);
            if (personPhoto != null)
                Picasso.with(this).load(personPhoto).into(imageView);
            else
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.logo));
            TextView firstName = navigationView.getHeaderView(0).findViewById(R.id.userName);
            firstName.setText(personName);

            TextView gmail = navigationView.getHeaderView(0).findViewById(R.id.gmail);
            gmail.setText(personEmail);
        }

        fragmentManager.beginTransaction().
                replace(R.id.content_frame, new FragmentCarsList())
                .commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityMain.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.layout_custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to go back at log in screen?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);
        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
                startActivity(intent);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cars_list)
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FragmentCarsList())
                    .commit();
        else if (id == R.id.nav_add_announcement){
            Intent intent = new Intent(getApplicationContext(), ActivityCarAnnouncementAdd.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_my_announcements) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FragmentMyAnnouncements())
                    .commit();
        } else if (id == R.id.nav_exit) {
            confirmLogOut();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void confirmLogOut() {
        final Dialog customDialog = new Dialog(ActivityMain.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.layout_custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to log out?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);
        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogIn();
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

    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

    private void goToLogIn() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getApplicationContext(), "You've been logged out.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
                        startActivity(intent);
                    }
                });
    }
}
