package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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