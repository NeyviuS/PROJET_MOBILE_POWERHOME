package com.example.powerhouse;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.powerhouse.entities.Appliance;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class ApplianceAdapter extends BaseAdapter {

    private static String URL_REMOVE = "http://192.168.1.84/powerhome/removeApplianceFromHabitat.php?appliance_id=";
    private static String URL_AJOUT = "http://192.168.1.84/powerhome/addAppliance.php?appliance_id=";

    private Context context;
    private List<Appliance> appliance_list;
    private LayoutInflater inflater;
    private int layoutId;


    public ApplianceAdapter(Context context, List<Appliance> appliance_list, int layoutId) {
        this.appliance_list = appliance_list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.layoutId = layoutId;
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
            convertView = inflater.inflate(layoutId, parent, false);
        }
        ImageView applianceImage = convertView.findViewById(R.id.icon_appliance);
        TextView applianceName = convertView.findViewById(R.id.appliance_name),
                applianceConso = convertView.findViewById(R.id.conso);


        Appliance appliance = appliance_list.get(position);


        applianceImage.setImageResource(appliance.getIcon());
        applianceName.setText(appliance.name);
        applianceConso.setText("Consommation : " + appliance.wattage + "W");

        TextView buttonAction = convertView.findViewById(R.id.actionAppliance);

        if (layoutId == R.layout.item_appliance) {

            buttonAction.setOnClickListener((v -> {
                Ion.with(context)
                        .load("GET", URL_REMOVE + appliance.id)
                        .asString()
                        .setCallback((e, result) -> {
                            if (e != null) {
                                Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            appliance_list.remove(position);
                            notifyDataSetChanged();
                            ((Activity) context).recreate();
                            Toast.makeText(context, "Votre appareil a bien été supprimé.", Toast.LENGTH_LONG).show();
                        });

            }));
        } else if (layoutId == R.layout.item_free_appliance) {
            buttonAction.setOnClickListener((v -> {
                SharedPreferences sp = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
                Ion.with(context)
                        .load("GET", URL_AJOUT + appliance.id + "&user_id=" + sp.getString("user_id", ""))
                        .asString()
                        .setCallback((e, result) -> {
                            if (e != null) {
                                Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            appliance_list.remove(position);
                            notifyDataSetChanged();
                            ((Activity) context).recreate();
                            Toast.makeText(context, "L'appareil vous appartient désormais.", Toast.LENGTH_LONG).show();
                        });

            }));
        }

        return convertView;

    }
}
