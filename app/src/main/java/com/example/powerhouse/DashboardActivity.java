package com.example.powerhouse;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.powerhouse.entities.Appliance;
import com.example.powerhouse.entities.TimeSlot;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private TimeSlotAdapter timeslotAdapter;
    private ApplianceAdapter applianceAdapter;
    private CalendarView calendar;
    private ListView timeslotListView, appliancesListView;
    private List<TimeSlot> time_slot_list;
    private List<Appliance> appliances_list;
    private LinearLayout showConsoGlobale, showAppliances;
    private ImageView imgShowConsoGlobale, imgShowAppliances;
    private boolean isVisibleConsoGlobale, isVisibleAppliances;

    private static String URL_TIMESLOT = "http://192.168.1.84/powerhome/getTimeslotFromDay.php?date=";
    private static String URL_APPLIANCES = "http://192.168.1.84/powerhome/getAppliancesFromUserId.php?user_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        calendar = findViewById(R.id.calendar);
        timeslotListView = findViewById(R.id.timeslot);
        appliancesListView = findViewById(R.id.list_appliance);
        showConsoGlobale = findViewById(R.id.buttonShowConsoGlobale);
        showAppliances = findViewById(R.id.buttonShowAppliances);
        imgShowAppliances = findViewById(R.id.imgShowAppliances);
        imgShowConsoGlobale = findViewById(R.id.imgShowConsoGlobale);
        isVisibleAppliances = isVisibleConsoGlobale = true;

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


        SharedPreferences sp = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int id = Integer.parseInt(sp.getString("user_id", ""));
        Ion.with(this).load("GET", URL_APPLIANCES + id).asString().setCallback((e, result) -> {
            if (e != null) {
                Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                return;
            }

            appliances_list = Appliance.getListFromJson(result);
            applianceAdapter = new ApplianceAdapter(this, appliances_list);
            appliancesListView.setAdapter(applianceAdapter);
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
}