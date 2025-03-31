package com.example.powerhouse.entities;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeSlot {


    public String  begin;
    public String end;
    public double taux;

    public TimeSlot(String begin, String end, double taux) {
        this.begin = begin;
        this.end = end;
        this.taux = taux;
    }

    public double getTaux() {
        return taux;
    }

    public String getEnd() {
        return end;
    }

    public String getBegin() {
        return begin;
    }

    public static List<TimeSlot> getListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<TimeSlot>>(){}.getType();
        List<TimeSlot> list = gson.fromJson(json, type);
        return list != null ? list : new ArrayList<>();
    }
}
