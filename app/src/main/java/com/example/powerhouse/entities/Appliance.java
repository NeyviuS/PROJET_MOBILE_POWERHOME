package com.example.powerhouse.entities;

import androidx.annotation.DrawableRes;

import com.example.powerhouse.R;

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
    public ApplianceType type;


    public Appliance() {
        bookings = new ArrayList<>();
    }

    public Appliance(int id, String name, String reference, int wattage, int type) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.wattage = wattage;
        bookings = new ArrayList<>();
        this.type = ApplianceType.values()[type];
    }

    public @DrawableRes int getIcon() {
        if (type == ApplianceType.ASPIRATEUR)
            return R.drawable.ic_aspirateur;
        if (type == ApplianceType.FER_A_REPASSER)
            return R.drawable.ic_fer_a_repasser;
        if (type == ApplianceType.MACHINE_A_LAVER)
            return R.drawable.ic_machine_a_laver;
        return R.drawable.ic_climatiseur;
    }
}
