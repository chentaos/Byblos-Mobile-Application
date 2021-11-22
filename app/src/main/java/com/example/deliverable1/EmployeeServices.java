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
    String employeeName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_services);

        services = new ArrayList<>();
        list = findViewById(R.id.services);
        employeeName = getIntent().getStringExtra("username");
        Toast.makeText(getApplicationContext(), employeeName, Toast.LENGTH_SHORT).show();
        store();
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String idService = services.get(position).getName();
                showUpdateDeleteDialog(idService);
                return false;
            }
        });
    }
    private void showUpdateDeleteDialog(final String productId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_service_manage_employee_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editNewBranchName = dialogView.findViewById(R.id.editNewBranchName);
        final Button buttonUpdate = dialogView.findViewById(R.id.updateBtn);
        final Button buttonCreate = dialogView.findViewById(R.id.createBtn);
        final Button buttonDelete = dialogView.findViewById(R.id.deleteBtn);
        final Button buttonCancel = dialogView.findViewById(R.id.cancelBtn);

        DatabaseReference dr = database1.child(productId);
        String name = dr.getKey();

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonUpdate.setVisibility(View.GONE);
        buttonDelete.setVisibility(View.GONE);
        buttonCancel.setOnClickListener(view -> b.dismiss());

        buttonCreate.setOnClickListener(view -> {
            String serviceName = editNewBranchName.getText().toString();
            if (!TextUtils.isEmpty(serviceName)) {
                Branch branch = new Branch(employeeName, name, serviceName, null, null);
                FirebaseDatabase.getInstance().getReference().child("Branch").child(serviceName).setValue(branch);
//                updateService(productId, rate);
            }
            b.dismiss();
        });

//        buttonDelete.setOnClickListener(v -> {
//            if (services.size() > 3) {
//                deleteService(productId);
//                store();
//            } else {
//                showNbRequiredServices();
//            }
//            b.dismiss();
//        });
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
