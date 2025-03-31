package com.example.powerhouse.entities;

import androidx.annotation.DrawableRes;

import com.example.powerhouse.R;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Appliance {

    public enum ApplianceType {
        ASPIRATEUR,
        FER_A_REPASSER,
        MACHINE_A_LAVER,
        CLIMATISEUR
    }

    public int id;
    public String name;
    public String reference;
    public int wattage;
    public List<Booking> bookings;
    public int type;


    public Appliance() {
        bookings = new ArrayList<>();
    }

    public Appliance(int id, String name, String reference, int wattage, int type) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.wattage = wattage;
        this.bookings = new ArrayList<>();
        this.type = type;
    }

    public @DrawableRes int getIcon() {
        if (type == ApplianceType.ASPIRATEUR.ordinal())
            return R.drawable.ic_aspirateur;
        if (type == ApplianceType.FER_A_REPASSER.ordinal())
            return R.drawable.ic_fer_a_repasser;
        if (type == ApplianceType.MACHINE_A_LAVER.ordinal())
            return R.drawable.ic_machine_a_laver;
        return R.drawable.ic_climatiseur;
    }

    public static List<Appliance> getListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Appliance>>(){}.getType();
        List<Appliance> list = gson.fromJson(json, type);
        return list != null ? list : new ArrayList<>();
    }

}
