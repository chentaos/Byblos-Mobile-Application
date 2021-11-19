package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import service.Service;

public class ServiceManage extends AppCompatActivity {

    ListView list;
    RadioGroup rg;

    List<Service> services;
    List<Service> services2;
    ArrayAdapter<String> adapter;
    DatabaseReference database1=FirebaseDatabase.getInstance().getReference().child("Services");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_manage);
        list = findViewById(R.id.serviceList);
        services=new ArrayList<>();

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
        final View dialogView = inflater.inflate(R.layout.activity_service_manage_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextRate = dialogView.findViewById(R.id.editNewRate);
        final Button buttonUpdate = dialogView.findViewById(R.id.updateBtn);
        final Button buttonDelete = dialogView.findViewById(R.id.deleteBtn);
        final Button buttonCancel = dialogView.findViewById(R.id.cancelBtn);

        DatabaseReference dr = database1.child(productId);
        String name = dr.getKey();

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCancel.setOnClickListener(view -> b.dismiss());

        buttonUpdate.setOnClickListener(view -> {
            double rate = Double.parseDouble(editTextRate.getText().toString());
            if (!TextUtils.isEmpty(name)) {
                updateService(productId, rate);
            }
            b.dismiss();
        });

        buttonDelete.setOnClickListener(v -> {
            if (services.size() > 3) {
                deleteService(productId);
                store();
            } else {
                showNbRequiredServices();
            }
            b.dismiss();
        });
    }

    private void updateService(String id, double rate) {
        DatabaseReference dr = database1.child(id);
        dr.child("rate").setValue(rate);
        store();
    }

    private void deleteService(String id) {
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long nbServices = snapshot.getChildrenCount();
                if (nbServices > 3){
                    DatabaseReference dr = database1.child(id);
                    dr.removeValue();
                    store();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showNbRequiredServices(){
        Toast.makeText(this,"Need to always have 3 services",Toast.LENGTH_SHORT).show();
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
                ServiceItem p = new ServiceItem(ServiceManage.this, services);
                list.setAdapter(p);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}