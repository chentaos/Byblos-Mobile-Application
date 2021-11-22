package com.example.deliverable1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployeeSetting extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_setting);

    }

    public void openServicesOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeSetting.this, EmployeeServices.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(intent);
    }

    public void openBranchAccountOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeSetting.this, BranchAccount.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(intent);
    }
}
