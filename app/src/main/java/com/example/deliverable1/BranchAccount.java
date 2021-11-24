package com.example.deliverable1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import branch.Branch;
import service.Service;

public class BranchAccount extends AppCompatActivity {
    ListView list;
    List<Branch> branches;
    List<String> services;
    DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("Branch");
    DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().child("Services");
    String employeeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_services);

        branches = new ArrayList<>();
        services = new ArrayList<>();
        list = findViewById(R.id.services);
        employeeName = getIntent().getStringExtra("username");
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            String idService = branches.get(position).getName();
            showUpdateDeleteDialog(idService, branches.get(position).getService());
            return false;
        });
        database2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Service s= postSnapshot.getValue(Service.class);
                    services.add(s.getName());
                    store();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String branchId, final String service){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_branch_dialog, null);
        dialogBuilder.setView(dialogView);

//        final Button buttonUpdate = dialogView.findViewById(R.id.updateBtn);
//        final Button buttonCreate = dialogView.findViewById(R.id.createBtn);
        final Button buttonDelete = dialogView.findViewById(R.id.deleteBtn);
        final Button buttonCancel = dialogView.findViewById(R.id.cancelBtn);

        DatabaseReference dr = database1.child(branchId);
        String name = dr.getKey();

        dialogBuilder.setTitle(name);

        if (services.contains(service)){
            buttonDelete.setVisibility(View.GONE);
        }

        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonCancel.setOnClickListener(view -> b.dismiss());

        buttonDelete.setOnClickListener(v -> {
            deleteBranch(branchId);
            store();
            b.dismiss();
        });
    }

    private void deleteBranch(String id) {
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference dr = database1.child(id);
                dr.removeValue();
                store();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void store(){
        branches.clear();
        database1.orderByChild("employee").equalTo(employeeName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branches.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Branch s= postSnapshot.getValue(Branch.class);
                    branches.add(s);
                }
                BranchItem p = new BranchItem(BranchAccount.this, branches, services);
                list.setAdapter(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
