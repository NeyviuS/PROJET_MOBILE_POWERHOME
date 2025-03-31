package com.example.powerhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.powerhouse.entities.Appliance;
import com.example.powerhouse.entities.TimeSlot;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class ApplianceAdapter extends BaseAdapter {

    private static String URL = "http://192.168.1.84/powerhome/removeApplianceFromHabitat.php?appliance_id=";

    private Context context;
    private List<Appliance> appliance_list;
    private LayoutInflater inflater;


    public ApplianceAdapter(Context context, List<Appliance> appliance_list) {
        this.appliance_list = appliance_list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return appliance_list.size();
    }

    @Override
    public Object getItem(int position) {
        return appliance_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_appliance, parent, false);
        }
        ImageView applianceImage = convertView.findViewById(R.id.icon_appliance);
        TextView applianceName = convertView.findViewById(R.id.appliance_name),
                applianceConso = convertView.findViewById(R.id.conso);


        Appliance appliance = appliance_list.get(position);


        applianceImage.setImageResource(appliance.getIcon());
        applianceName.setText(appliance.name);
        applianceConso.setText(Integer.toString(appliance.wattage));

        TextView buttonDelete = convertView.findViewById(R.id.deleteAppliance);

        buttonDelete.setOnClickListener((v -> {
            Ion.with(context)
                    .load("GET", URL + appliance.id)
                    .asString()
                    .setCallback((e, result) -> {
                        if (e != null) {
                            Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        appliance_list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Votre appareil a bien été supprimé.", Toast.LENGTH_LONG).show();
                    });

        }));

        return convertView;

    }
}
