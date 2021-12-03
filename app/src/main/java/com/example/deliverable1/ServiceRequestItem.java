package com.example.deliverable1;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import branch.Branch;
import service.ServiceRequest;

public class ServiceRequestItem extends ArrayAdapter<ServiceRequest> {
    private Activity context;
    List<ServiceRequest> serviceRequests;
    DatabaseReference dbBranch = FirebaseDatabase.getInstance().getReference().child("Branch");

    String key;

    public ServiceRequestItem(Activity context, List<ServiceRequest> serviceRequests) {
        super(context, R.layout.activity_request_item, serviceRequests);
        this.context = context;
        this.serviceRequests = serviceRequests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_request_item, null, true);

        TextView txtCustomerName = listViewItem.findViewById(R.id.txtCustomerName);
        TextView txtService = listViewItem.findViewById(R.id.txtService);
//        Button btnAccept = listViewItem.findViewById(R.id.btnAccept);
//        Button btnReject = listViewItem.findViewById(R.id.btnReject);

        ServiceRequest sR = serviceRequests.get(position);


        txtCustomerName.setText(sR.getCustomerName());
        txtService.setText(sR.getService());
//        btnAccept.setOnClickListener(v -> {
//            sR.setAccepted(true);
//            sR.setPending(false);
//            dbBranch.child(sR.getParentId()).child("requests")
//                    .child("submittedForms").child(sR.getKey()).setValue(sR);
//
//        });
//
//        btnReject.setOnClickListener(v -> {
//            sR.setAccepted(false);
//            sR.setPending(false);
//            dbBranch.child(sR.getParentId()).child("requests")
//                    .child("submittedForms").child(sR.getKey()).setValue(sR);
//        });

        return listViewItem;
    }
}
