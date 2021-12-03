package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import account.Employee;

public class EmployeeMainMenu extends AppCompatActivity {
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivty_employee_main_menu);
        userName = getIntent().getStringExtra("userName");
        Employee em = new Employee(userName,"");
        TextView eT = findViewById(R.id.welcomeText);

        em.getMyRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String address = snapshot.child(userName).child("address").getValue(String.class);
                String phoneNumber = snapshot.child(userName).child("PhoneNumber").getValue(String.class);
                String welcomeMsg = getIntent().getStringExtra("welcomeMSG");
                String result = welcomeMsg + "\n your address is " + address + "\nyour phone number is "+ phoneNumber;
                eT.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openSettingOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeMainMenu.this, EmployeeSetting.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));

        startActivity(intent);
    }

    public void pendingServiceOnClick(View view){
        Intent intent = new Intent(EmployeeMainMenu.this, EmployeeServiceRequest.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

}