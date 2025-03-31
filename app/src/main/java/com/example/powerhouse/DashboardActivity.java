package com.example.powerhouse;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.powerhouse.entities.Appliance;
import com.example.powerhouse.entities.TimeSlot;
import com.google.android.material.navigation.NavigationView;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TimeSlotAdapter timeslotAdapter;
    private ApplianceAdapter applianceAdapter;
    private CalendarView calendar;
    private ListView timeslotListView, appliancesListView, freeAppliancesListView;
    private List<TimeSlot> time_slot_list;
    private List<Appliance> appliances_list, free_appliances_list;
    private LinearLayout showConsoGlobale, showAppliances, showFreeAppliances;
    private ImageView imgShowConsoGlobale, imgShowAppliances, imgShowFreeAppliances;
    private boolean isVisibleConsoGlobale, isVisibleAppliances, isVisibleFreeAppliances;
    private TextView userName;

    private static String URL_TIMESLOT = "http://192.168.1.84/powerhome/getTimeslotFromDay.php?date=";
    private static String URL_APPLIANCES = "http://192.168.1.84/powerhome/getAppliancesFromUserId.php?user_id=";
    private static String URL_FREE_APPLIANCES = "http://192.168.1.84/powerhome/getFreeAppliances.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Configuration du Drawer et de la Toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Ajouter le bouton du menu hamburger
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Gestion des clics sur le menu du Drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Accueil", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_habitat) {
                // Ouvrir l'activité Habitat
                startActivity(new Intent(DashboardActivity.this, HabitatsActivity.class));
            } else if (id == R.id.nav_apropos) {
                // Ouvrir l'activité À Propos
                startActivity(new Intent(DashboardActivity.this, AproposActivity.class));
            }

            // Fermer le drawer après un clic
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        calendar = findViewById(R.id.calendar);
        timeslotListView = findViewById(R.id.timeslot);
        appliancesListView = findViewById(R.id.list_appliance);
        showConsoGlobale = findViewById(R.id.buttonShowConsoGlobale);
        showAppliances = findViewById(R.id.buttonShowAppliances);
        imgShowAppliances = findViewById(R.id.imgShowAppliances);
        imgShowConsoGlobale = findViewById(R.id.imgShowConsoGlobale);
        isVisibleAppliances = isVisibleConsoGlobale = isVisibleFreeAppliances = true;
        freeAppliancesListView = findViewById(R.id.list_free_appliances);
        showFreeAppliances = findViewById(R.id.buttonShowFreeAppliances);
        imgShowFreeAppliances = findViewById(R.id.imgShowFreeAppliances);
        userName = findViewById(R.id.userName);

        SharedPreferences sp = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userName.setText(sp.getString("firstname", "") + " " + sp.getString("lastname", ""));

        showFreeAppliances.setOnClickListener((v -> {
            freeAppliancesListView.setVisibility(isVisibleFreeAppliances ? GONE : VISIBLE);
            imgShowFreeAppliances.setImageResource(isVisibleFreeAppliances ? R.drawable.baseline_arrow_drop_up_24 : R.drawable.baseline_arrow_drop_down_24);
            isVisibleFreeAppliances = !isVisibleFreeAppliances;
        }));

        showAppliances.setOnClickListener((v -> {
            appliancesListView.setVisibility(isVisibleAppliances ? GONE : VISIBLE);
            imgShowAppliances.setImageResource(isVisibleAppliances ? R.drawable.baseline_arrow_drop_up_24 : R.drawable.baseline_arrow_drop_down_24);
            isVisibleAppliances = !isVisibleAppliances;
        }));

        showConsoGlobale.setOnClickListener((v -> {
            calendar.setVisibility(isVisibleConsoGlobale ? GONE : VISIBLE);
            timeslotListView.setVisibility(isVisibleConsoGlobale ? GONE : VISIBLE);
            imgShowConsoGlobale.setImageResource(isVisibleConsoGlobale ? R.drawable.baseline_arrow_drop_up_24 : R.drawable.baseline_arrow_drop_down_24);
            isVisibleConsoGlobale = !isVisibleConsoGlobale;
        }));

        calendar.setOnDateChangeListener(((view, year, month, dayOfMonth) -> updateListCreneau(year, month, dayOfMonth)));

        fetchOwnAppliances();
        fetchFreeAppliances();
    }

    private void fetchOwnAppliances() {
        SharedPreferences sp = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int id = Integer.parseInt(sp.getString("user_id", ""));
        Ion.with(this).load("GET", URL_APPLIANCES + id).asString().setCallback((e, result) -> {
            if (e != null) {
                Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                return;
            }

            appliances_list = Appliance.getListFromJson(result);
            applianceAdapter = new ApplianceAdapter(this, appliances_list, R.layout.item_appliance);
            appliancesListView.setAdapter(applianceAdapter);
        });
    }

    private void fetchFreeAppliances() {
        Ion.with(this).load("GET", URL_FREE_APPLIANCES).asString().setCallback((e, result) -> {
            if (e != null) {
                Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                return;
            }

            free_appliances_list = Appliance.getListFromJson(result);
            applianceAdapter = new ApplianceAdapter(this, free_appliances_list, R.layout.item_free_appliance);
            freeAppliancesListView.setAdapter(applianceAdapter);
        });
    }

    private void updateListCreneau(int year, int month, int dayOfMonth) {
        Ion.with(this).load("GET", URL_TIMESLOT + year + "-" + (month + 1) + "-" + dayOfMonth).asString().setCallback((e, result) -> {
            if (e != null) {
                Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                return;
            }

            time_slot_list = TimeSlot.getListFromJson(result);
            timeslotAdapter = new TimeSlotAdapter(this, time_slot_list);
            timeslotListView.setAdapter(timeslotAdapter);
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
