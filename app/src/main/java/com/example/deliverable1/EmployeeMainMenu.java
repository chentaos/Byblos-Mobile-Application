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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivty_employee_main_menu);
        String userName = getIntent().getStringExtra("userName");
        Employee em = new Employee(userName,"");
        TextView eT = findViewById(R.id.welcomeText);

        em.getMyEmRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String address = snapshot.child(userName).child("adress").getValue(String.class);
                String result = getIntent().getStringExtra("welcomeMSG") + address;
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
        intent.putExtra("userName",getIntent().getStringExtra("userName"));
        startActivity(intent);
    }


}