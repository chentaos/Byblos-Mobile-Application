package com.example.deliverable1;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import branch.Branch;
import service.Service;

public class BranchItem extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> branches;
    DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("Services");
    List<String> services;

    public BranchItem(Activity context, List<Branch> branches, List<String> services) {
        super(context, R.layout.activity_branch_item, branches);
        this.context = context;
        this.branches = branches;
        this.services = services;

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

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.BranchName);
        TextView textViewService = (TextView) listViewItem.findViewById(R.id.ServiceName);


        Branch branch = branches.get(position);

        if (!services.contains(branch.getService())){
            textViewService.setTextColor(Color.RED);
            textViewService.setText(branch.getService() + "(no longer offered)");
        } else{
            textViewService.setText(branch.getService());
        }

        textViewName.setText(branch.getName());
        return listViewItem;
    }
}
