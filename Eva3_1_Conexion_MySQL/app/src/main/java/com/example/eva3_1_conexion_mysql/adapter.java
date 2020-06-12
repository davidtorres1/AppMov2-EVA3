package com.example.eva3_1_conexion_mysql;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class adapter extends ArrayAdapter<JSONObject> {
    Context context;
    int resource;
    List<JSONObject> objects;

    public adapter(@NonNull Context context, int resource, @NonNull List<JSONObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(resource,parent,false);
        }

        TextView tvFirstName, tvLastName;
        tvFirstName = convertView.findViewById(R.id.tvName);
        tvLastName = convertView.findViewById(R.id.tvLastName);

        try {
            tvFirstName.setText(objects.get(position).getString("first_name"));
            tvLastName.setText(objects.get(position).getString("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
}
