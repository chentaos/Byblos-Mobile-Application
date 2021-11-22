package com.example.deliverable1;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import account.Employee;

public class EmployeeSetting extends AppCompatActivity{

    Button profileBtn;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_setting);
        userName = getIntent().getStringExtra("userName");
        Log.d("userName" ,userName);


        profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EmployeeSetting.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.avtivity_employee_profile_dialog, null);
                dialogBuilder.setView(dialogView);


                final EditText address = dialogView.findViewById(R.id.employeeAdress);
                final EditText phoneNumber = dialogView.findViewById(R.id.employeePhoneNumber);
                final Button buttonSubmit = dialogView.findViewById(R.id.profileSbmitBtn);
                final Button buttonCancel = dialogView.findViewById(R.id.profileCancelBtn);

                final AlertDialog b = dialogBuilder.create();
                b.show();

                buttonCancel.setOnClickListener(view -> b.dismiss());

                buttonSubmit.setOnClickListener(view -> {
                    Employee em = new Employee(userName, "aaaa");
                    em.setProfile(address.getText().toString(),phoneNumber.getText().toString());
                    b.dismiss();
                });

                return false;
            }
        });



    }



}
