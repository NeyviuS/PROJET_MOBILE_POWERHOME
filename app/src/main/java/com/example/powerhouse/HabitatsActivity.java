package com.example.powerhouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.example.powerhouse.R;


import com.example.powerhouse.entities.Habitat;
import com.koushikdutta.ion.Ion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HabitatsActivity extends AppCompatActivity {

    private final static String URL = "http://192.168.1.84/powerhome/getHabitats_v3.php?token=";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView listView;
    private HabitatAdapter adapter;
    private List<Habitat> habitatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitats);

        // Configuration du DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        listView = findViewById(R.id.listHabitats);

        // Initialiser la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Afficher le bouton hamburger
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24); // Icône de menu

        // Configurer le NavigationDrawer
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(HabitatsActivity.this, DashboardActivity.class));
            } else if (item.getItemId() == R.id.nav_habitat) {
                Toast.makeText(HabitatsActivity.this, "Vous êtes déjà sur la page Habitat", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_apropos) {
                startActivity(new Intent(HabitatsActivity.this, AproposActivity.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START); // Fermer le menu après une sélection
            return true;
        });


        // Fetch data from API
        fetchHabitats();
    }

    // Gérer l'ouverture du menu Drawer avec le bouton hamburger
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START); // Ouvrir le menu
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchHabitats() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("user_token", null);
        Ion.with(this)
                .load(URL + token)
                .asString()
                .setCallback((e, result) -> {
                    if (e != null) {
                        Toast.makeText(HabitatsActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        Log.e("HTTP_ERROR", e.getMessage());
                        return;
                    }

                    habitatList = Habitat.getListFromJson(result);
                    adapter = new HabitatAdapter(this, habitatList);
                    listView.setAdapter(adapter);
                });
    }
}
