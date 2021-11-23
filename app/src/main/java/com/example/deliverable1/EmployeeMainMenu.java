package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import account.Employee;

public class EmployeeMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivty_employee_main_menu);

        TextView eT = findViewById(R.id.welcomeText);
        eT.setText(getIntent().getStringExtra("welcomeMSG"));

//        TextView detailText = findViewById(R.id.detailText);
//        detailText.setText();
    }

    public void openSettingOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeMainMenu.this, EmployeeSetting.class);
        intent.putExtra("userName",getIntent().getStringExtra("userName"));
        startActivity(intent);
    }


}