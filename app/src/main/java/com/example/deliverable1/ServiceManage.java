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
    String type="CarRental";
    List<String> services;
    ArrayAdapter<String> adapter;
    DatabaseReference database1=FirebaseDatabase.getInstance().getReference("Services");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_manage);
        rg = findViewById(R.id.serviceRadioGroup);
        list = (ListView) findViewById(R.id.serviceList);

        services=new ArrayList<>();
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String idService = services.get(position);
                showUpdateDeleteDialog(idService);
                return false;
            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.RadioService1:
                        type="CarRental";
                        break;
                    case R.id.RadioService2:
                        type="TruckRental";
                        break;
                    case R.id.RadioService3:
                        type="MovingAssistant";
                        break;
                }
                store();
//                display();
            }
        });
    }
    private void showUpdateDeleteDialog(final String productId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_service_manage_dialog, null);
        dialogBuilder.setView(dialogView);


        final EditText editTextRate = (EditText) dialogView.findViewById(R.id.editNewRate);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancelBtn);

        DatabaseReference dr = database1.child(type).child(productId);
        String name = dr.getKey();

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double rate = Double.parseDouble(editTextRate.getText().toString());
                if (!TextUtils.isEmpty(name)) {
                    updateService(productId, rate);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteService(productId);
                b.dismiss();
            }
        });
    }

    private void updateService(String id, double rate) {
        DatabaseReference dr = database1.child(type).child(id);
        dr.child("rate").setValue(rate);
    }

    private void deleteService(String id) {
        DatabaseReference dr = database1.child(type).child(id);
        dr.removeValue();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RadioButton a = (RadioButton) findViewById(R.id.RadioService1);
        a.setChecked(true);
    }

    private void store(){
        Log.i("Servivi",type);
        Toast.makeText(getApplicationContext(),type, Toast.LENGTH_SHORT).show();
        Log.i("Servivi","1");
        services.clear();
        database1.child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    String p = postSnapshot.getKey();
                    Log.i("Servivi",p);
                    services.add(p);
                }
                display();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void display(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,services);
        list.setAdapter(adapter);
    }

}