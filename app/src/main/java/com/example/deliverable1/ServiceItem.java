package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import service.Service;

public class ServiceItem extends ArrayAdapter<Service> {
    private Activity context;
    List<Service> sev;


    public ServiceItem(Activity context, List<Service> sev) {
        super(context, R.layout.activity_service_item,sev);
        this.context = context;
        this.sev = sev;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_service_item, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.serviceName);
        TextView textViewRate = (TextView) listViewItem.findViewById(R.id.serviceRate);

        Service product = sev.get(position);
        textViewName.setText(product.getName());
        textViewRate.setText("Hourly Rate: "+String.valueOf(product.getRate()));
        return listViewItem;
    }
}