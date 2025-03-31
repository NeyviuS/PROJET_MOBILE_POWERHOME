package com.example.powerhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.powerhouse.entities.Appliance;
import com.example.powerhouse.entities.Habitat;

import java.util.List;

public class HabitatAdapter extends BaseAdapter {
    private final Context context;
    private List<Habitat> habitatList;
    private LayoutInflater inflater;

    public HabitatAdapter(Context context, List<Habitat> habitatList) {
        this.habitatList = habitatList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return habitatList.size();
    }

    @Override
    public Object getItem(int position) {
        return habitatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_habitats, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView nbAppliances = convertView.findViewById(R.id.nbAppliances);
        TextView floorNumber = convertView.findViewById(R.id.floorNumber);
        LinearLayout appliances = convertView.findViewById(R.id.listAppliances);

        Habitat habitat = habitatList.get(position);
        if (habitat.firstname != null && habitat.lastname != null)
            name.setText(habitat.firstname + " " + habitat.lastname);
        nbAppliances.setText(habitat.appliances.size() + " équipements");
        floorNumber.setText(String.valueOf(habitat.floor));
        appliances.removeAllViews();
        for (Appliance appliance : habitat.appliances) {
            ImageView i = new ImageView(context);
            i.setImageResource(appliance.getIcon());
            i.setLayoutParams(new LinearLayout.LayoutParams(48, 48));
            appliances.addView(i);
        }

        return convertView;
    }
}
