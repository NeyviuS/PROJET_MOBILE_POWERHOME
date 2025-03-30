package com.example.powerhouse.entities;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Habitat {

    public int id;
    public int floor;
    public double area;
    public String firstname, lastname;
    public List<Appliance> appliances;

    public Habitat() {
        appliances = new ArrayList<>();
    }

    public Habitat(int id, int floor, double area) {
        this.id = id;
        this.floor = floor;
        this.area = area;
        appliances = new ArrayList<>();
    }

    public static Habitat getFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Habitat.class);

    }

    public static List<Habitat> getListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Habitat>>(){}.getType();
        List<Habitat> list = gson.fromJson(json, type);
        return list;
    }
}
