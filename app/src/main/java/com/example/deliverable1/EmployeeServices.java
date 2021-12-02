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

public class EmployeeServices extends AppCompatActivity {
    ListView list;
    List<Service> services;
    DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("Services");
    DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().child("Branch");
    String employeeName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_services);

        services = new ArrayList<>();
        list = findViewById(R.id.services);
        employeeName = getIntent().getStringExtra("username");
        store();
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            String idService = services.get(position).getName();
            showUpdateDeleteDialog(idService);
            return false;
        });
    }
    private void showUpdateDeleteDialog(final String idService){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_service_manage_employee_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAdd = dialogView.findViewById(R.id.addBtn);
        final Button buttonCancel = dialogView.findViewById(R.id.cancelBtn);

        DatabaseReference dr = database1.child(idService);
        String name = dr.getKey();

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCancel.setOnClickListener(view -> b.dismiss());

        buttonAdd.setOnClickListener(view -> {
            String serviceName = idService + "_" + employeeName;
            database2.child(serviceName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        errorNameAlreadyExist();
                    } else{
                        Branch branch = new Branch(employeeName, name, serviceName, null, null);
                        database2.child(serviceName).setValue(branch);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            b.dismiss();
        });

    }

    private void errorEmptyService(){
        Toast.makeText(this,"Service name can't be empty",Toast.LENGTH_SHORT).show();
    }

    private void errorNameAlreadyExist(){
        Toast.makeText(this,"The service already exist",Toast.LENGTH_SHORT).show();
    }

    private void store(){
        services.clear();
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Service s= postSnapshot.getValue(Service.class);
                    services.add(s);
                }
                ServiceItem p = new ServiceItem(EmployeeServices.this, services);
                list.setAdapter(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
