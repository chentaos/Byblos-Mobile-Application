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

import java.util.List;

import branch.Branch;
import service.Service;

public class BranchSearchItem extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> branches;
    DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("Services");
    List<String> services;

    public BranchSearchItem(Activity context, List<Branch> branches, List<String> services) {
        super(context, R.layout.activity_branch_item, branches);
        this.context = context;
        this.branches = branches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_branch_search_item, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.BranchName);
        TextView textViewEmployeeName= (TextView) listViewItem.findViewById(R.id.EmployeeName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.Price);


        Branch branch = branches.get(position);

        database1.child(branch.getService()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    textViewPrice.setText(snapshot.child("rate").getValue(int.class) + "$");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textViewName.setText(branch.getName());
        textViewEmployeeName.setText(branch.getEmployee());
        return listViewItem;
    }
}
