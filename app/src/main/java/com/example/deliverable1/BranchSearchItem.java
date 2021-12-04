package com.example.deliverable1;

import android.app.Activity;
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

import account.Employee;
import branch.Branch;

public class BranchSearchItem extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> branches;
    DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("Services");
    List<String> services;
    List<Employee> employees;
    List<String> employeesUsername;

    public BranchSearchItem(Activity context, List<Branch> branches, List<String> employeesUsername, List<Employee> employees) {
        super(context, R.layout.activity_branch_item, branches);
        this.context = context;
        this.branches = branches;
        this.employeesUsername = employeesUsername;
        this.employees = employees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_branch_search_item, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.txtCustomerName);
        TextView textViewEmployeeName= (TextView) listViewItem.findViewById(R.id.EmployeeName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.Price);
        TextView txtRate = listViewItem.findViewById(R.id.txtRate);
        TextView txtEmployeeAddress = listViewItem.findViewById(R.id.txtEmployeeAddress);

        Branch branch = branches.get(position);
        Employee employee = employees.get(employeesUsername.indexOf(branch.getEmployee()));

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

        if (branch.getRate() != 0)
            txtRate.setText(branch.getRate() + "/5");
        if (employee.getAddress() != null)
            txtEmployeeAddress.setText(employee.getAddress());

        return listViewItem;
    }
}
