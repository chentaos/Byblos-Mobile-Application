package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ServiceManage extends AppCompatActivity {

    ListView list;
    RadioGroup rg;
    String type="CarRental";
    List<String> services;
    ArrayAdapter<String> adapter;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_manage);
        rg = findViewById(R.id.serviceRadioGroup);
        list = (ListView) findViewById(R.id.serviceList);

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
                display();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RadioButton a = (RadioButton) findViewById(R.id.RadioService1);
        a.setChecked(true);
    }

    private void store(){
        database= FirebaseDatabase.getInstance().getReference("Services").child(type);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String p = postSnapshot.getKey().toString();
                    Log.i("Servivi",p);
                    services.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void display(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,services);
        list.setAdapter(adapter);
    }

}