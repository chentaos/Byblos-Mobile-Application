package com.example.deliverable1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import account.Employee;

public class EmployeeSetting extends AppCompatActivity{
    Button profileBtn;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_setting);
        userName = getIntent().getStringExtra("username");
        Log.d("userName" ,userName);


        profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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

                   boolean phoneMathes = phoneNumber.getText().toString().matches("^\\d{10}$");
                   boolean addressMathes = address.getText().toString().matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");

                    if(phoneMathes == true && addressMathes==true){
                        em.setProfile(address.getText().toString(),phoneNumber.getText().toString());
                    }else{
                        Toast.makeText(getApplication(),"Wrong format or empty",Toast.LENGTH_SHORT).show();
                        onClick(v);
                    }

                    b.dismiss();
                });

            }
        });



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
    public void workTimeOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeSetting.this, BranchWorkTime.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(intent);
    }
}
