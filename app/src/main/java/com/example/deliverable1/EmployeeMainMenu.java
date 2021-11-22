package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployeeMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivty_employee_main_menu);



    }

    public void openSettingOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeMainMenu.this, EmployeeSetting.class);
        startActivity(intent);
    }


}