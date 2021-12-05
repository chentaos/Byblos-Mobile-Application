package com.example.deliverable1;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import branch.Branch;
import service.Service;

public class BranchItem extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> branches;
    DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("Services");
    List<String> services;
    List<Service> servicesList;

    public BranchItem(Activity context, List<Branch> branches, List<String> services, List<Service> servicesList) {
        super(context, R.layout.activity_branch_item, branches);
        this.context = context;
        this.branches = branches;
        this.services = services;
        this.servicesList = servicesList;

//        database1.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                services.clear();
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//
//                    Service s= postSnapshot.getValue(Service.class);
//                    services.add(s.getName());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_branch_item, null, true);

        TextView txtHourlyRate = (TextView) listViewItem.findViewById(R.id.txtHourlyRate);
        TextView textViewService = (TextView) listViewItem.findViewById(R.id.ServiceName);
        TextView txtCurrentRate = listViewItem.findViewById(R.id.txtCurrentRate);
        txtCurrentRate.setVisibility(View.VISIBLE);

        Branch branch = branches.get(position);
        Service s = servicesList.get(services.indexOf(branch.getService()));

        if (!services.contains(branch.getService())){
            textViewService.setTextColor(Color.RED);
            textViewService.setText(branch.getService() + "(no longer offered)");
        } else{
            textViewService.setText(branch.getService());
        }

        txtHourlyRate.setText("Hourly Rate: "  + s.getRate());

        if(branch.getRate() != null && branch.getRate().getAverageRate() != 0){
            txtCurrentRate.setText(branch.getRate().getAverageRate() + "/5");
        } else {
            txtCurrentRate.setText("no rate");
        }

        return listViewItem;
    }
}
