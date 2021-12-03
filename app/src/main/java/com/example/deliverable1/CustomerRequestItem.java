package com.example.deliverable1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.MessageFormat;
import java.util.List;

import service.ServiceRequest;

public class CustomerRequestItem extends ArrayAdapter<ServiceRequest> {
    private Activity context;
    List<ServiceRequest> serviceRequests;
    DatabaseReference dbBranch = FirebaseDatabase.getInstance().getReference().child("Branch");

    String key;

    public CustomerRequestItem(Activity context, List<ServiceRequest> serviceRequests) {
        super(context, R.layout.activity_request_item, serviceRequests);
        this.context = context;
        this.serviceRequests = serviceRequests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_customer_request_item, null, true);

        TextView txtCustomerName = listViewItem.findViewById(R.id.txtCustomerName);
        TextView txtService = listViewItem.findViewById(R.id.txtService);
        TextView txtStatus = listViewItem.findViewById(R.id.txtStatus);

        ServiceRequest sR = serviceRequests.get(position);

        txtCustomerName.setText(sR.getCustomerName());
        txtService.setText(sR.getService());
        txtStatus.setText(MessageFormat.format("Status: {0}", sR.isPending() ? "pending" : sR.isAccepted() ? "accepted" : "rejected"));
        return listViewItem;
    }
}
