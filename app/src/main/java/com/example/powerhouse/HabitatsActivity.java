package com.example.powerhouse;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import com.koushikdutta.ion.Ion;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.powerhouse.entities.Habitat;

public class HabitatsActivity extends AppCompatActivity {
    private final static String URL = "http://192.168.1.84/powerhome/getHabitats_v3.php?token=";

    private ListView listView;
    private HabitatAdapter adapter;
    private List<Habitat> habitatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_habitats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listHabitats);
        fetchHabitats();
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