package com.example.deliverable1;

import account.*;

import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private User user;
    ProgressBar progressB;

    private enum AccountType {admin,customer,employee};
    private AccountType type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressB = findViewById(R.id.progressBar);
        progressB.setVisibility(View.INVISIBLE);


        //handle radioButtons, add a listener and change User whenever the user type changes.
        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioAdmin:
                        type=AccountType.admin;
                        break;
                    case R.id.radioEmployee:
                        type=AccountType.employee;
                        break;
                    case R.id.radioCustomer:
                        type=AccountType.customer;
                        break;
                }
            }
        });

        //Set a button clicked
        RadioButton a=(RadioButton) findViewById(R.id.radioAdmin);
        a.setChecked(true);
    }



    public void loginOnClick(View view) {
        String userName = ((EditText)findViewById(R.id.userName)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();

        //or use setError to specify where is missing.
        if(userName.isEmpty()){
           Toast.makeText(getApplicationContext(),"User name not filled",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(getApplicationContext(),"password not filled",Toast.LENGTH_SHORT).show();
            return;
        }

        progressB.setVisibility(View.VISIBLE);

        switch(type){
            case admin:
                user=new Admin(userName,password);
                break;
            case customer:
                user=new Customer(userName,password);
                break;
            case employee:
                user=new Employee(userName,password);
                break;
        }

        // user login
        user.login(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                progressB.setVisibility(View.INVISIBLE);
                loadUserMainPage();
            }

            @Override
            public void onFail(String errorInfo) {
                Toast.makeText(MainActivity.this,errorInfo,Toast.LENGTH_SHORT).show();
                progressB.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void registerOnClick(View view) {

        //TODO: move to a new page for registration
        EditText nameT = findViewById(R.id.userName);
        EditText passT=findViewById(R.id.password);
        String userName = nameT.getText().toString().trim();
        String password = passT.getText().toString().trim();

        if(userName.isEmpty()){
            Toast.makeText(getApplicationContext(),"User name not filled",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(getApplicationContext(),"password not filled",Toast.LENGTH_SHORT).show();
            return;
        }

        switch(type){
            case admin:
                user=new Admin(userName,password);
                break;
            case customer:
                user=new Customer(userName,password);
                break;
            case employee:
                user=new Employee(userName,password);
                break;
        }
        progressB.setVisibility(View.VISIBLE);

        //user login
        user.register(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"registered",Toast.LENGTH_SHORT).show();
                progressB.setVisibility(View.INVISIBLE);

                //loadUserMainPage();
            }

            @Override
            public void onFail(String errorInfo) {
                Log.d("Login","register fail");
                Toast.makeText(MainActivity.this,errorInfo,Toast.LENGTH_SHORT).show();
                nameT.setText("");
                passT.setText("");
                progressB.setVisibility(View.INVISIBLE);
            }
        });
    }

    // start a new page.
    public void loadUserMainPage(){
        Intent intent = new Intent(MainActivity.this, UserMainMenu.class);
        intent.putExtra("welcomeMSG",user.welcomeMSG());
        startActivity(intent);
    }

}