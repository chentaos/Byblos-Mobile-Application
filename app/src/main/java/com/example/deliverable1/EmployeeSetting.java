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

    public void openProfileOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeSetting.this, EditEmployeeProfile.class);
        startActivity(intent);
    }
}
