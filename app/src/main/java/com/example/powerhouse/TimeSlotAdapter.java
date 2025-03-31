package com.example.powerhouse;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.powerhouse.entities.Habitat;
import com.example.powerhouse.entities.TimeSlot;

import java.util.List;

public class TimeSlotAdapter extends BaseAdapter {

    private final Context context;
    private List<TimeSlot> time_slots;
    private LayoutInflater inflater;

    public TimeSlotAdapter(Context context, List<TimeSlot> time_slots) {
        this.time_slots = time_slots;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return time_slots.size();
    }

    @Override
    public Object getItem(int position) {
        return time_slots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_time_slot, parent, false);
        }
        TextView redirect = convertView.findViewById(R.id.intent_reserver);
        TextView time_slot_creneau = convertView.findViewById(R.id.time_slot_hour);
        TextView taux = convertView.findViewById(R.id.taux_wattage);

        TimeSlot time_slot = time_slots.get(position);

        String begin = time_slot.getBegin().toString().split(" ")[1].substring(0, 5);
        String end = time_slot.getEnd().toString().split(" ")[1].substring(0, 5);

        redirect.setOnClickListener((v -> {
            Bundle b = new Bundle();
        }));
        time_slot_creneau.setText(begin + " - " + end);
        taux.setText(Math.round(time_slot.getTaux()) + "%");
        if (time_slot.getTaux() < 100)
            taux.setBackgroundColor(Color.RED);
        if (time_slot.getTaux() < 70)
            taux.setBackgroundColor(context.getResources().getColor(R.color.orange));
        if (time_slot.getTaux() < 30)
            taux.setBackgroundColor(Color.GREEN);



        return convertView;
    }
}
