package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import service.Service;

public class ServiceRequirementEdit extends AppCompatActivity {

    ListView requirement;
    ArrayAdapter<String> adapter;
    String[] requirements={"Name", "Date of birth", "Address", "Email", "License type",
            "Preferred car type", "Date and time to pick up and return", "Maximum number of kilometers",
            "Area to be used", "Moving start and end location", "Number of movers", "Expected number of boxes"};

    boolean[] req=new boolean[12];

    EditText name,rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requirement_edit);

        requirement=findViewById(R.id.requirementList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,requirements);
        requirement.setAdapter(adapter);

        name=findViewById(R.id.serviceName);
        rate=findViewById(R.id.rate);
    }

    public void create(View view){
        if(valid()){
            for(int i=0;i<requirement.getCount();i++){
                req[i]=requirement.isItemChecked(i);
            }

            String type="";
            Service s=new Service(name.getText().toString(),Double.parseDouble(rate.getText().toString()), req);

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Services");

            myRef.child(s.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(s.getName())) {
                        errorMessage();
                    } else {
                        s.writeToDB();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Log.i("serviceCreate",s.toString());
            finish();
        }
    }

    private void errorMessage(){
        Toast.makeText(this,"This service name already exist",Toast.LENGTH_SHORT).show();
    }
    private boolean valid(){
        if(name.getText().toString().isEmpty()||rate.getText().toString().isEmpty()||Double.parseDouble(rate.getText().toString())==0){
            Toast.makeText(this,"name and rate must be filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}