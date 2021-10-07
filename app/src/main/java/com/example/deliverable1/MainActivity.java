package com.example.deliverable1;

import account.*;

import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private User user;
    ProgressBar progressB;

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
                        user = new Admin();
                        break;
                    case R.id.radioEmployee:
                        user = new Employee();
                        break;
                    case R.id.radioCustomer:
                        user = new Customer();
                        break;
                    default:
                        //?
                }
            }
        });
    }


    public void loginOnClick(View view) {

        String userName = ((EditText)findViewById(R.id.userName)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();

        //or use setError to specify where is missing.
        if(user == null || userName.isEmpty() || password.isEmpty()){
           Toast.makeText(getApplicationContext(),"information not filled",Toast.LENGTH_SHORT).show();
            return;
        }

        progressB.setVisibility(View.VISIBLE);

        user.setUserName(userName);
        user.setPasswd(password);

        // user login
        user.login(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                progressB.setVisibility(View.INVISIBLE);
                loadUserMainPage();
            }

            @Override
            public void onFail(String errorInfo) {
                Log.d("Login","login fail");
                Toast.makeText(MainActivity.this,errorInfo,Toast.LENGTH_SHORT).show();
                progressB.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void registerOnClick(View view) {
        //TODO: move to a new page for registration
        EditText nameT = findViewById(R.id.userName);
        String userName = nameT.getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();

        //or use setError to specify where is missing.
        if(user == null || userName.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(),"information not filled",Toast.LENGTH_SHORT).show();
            return;
        }
        progressB.setVisibility(View.VISIBLE);

        user.setUserName(userName);
        user.setPasswd(password);

        //user login
        user.register(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"registered",Toast.LENGTH_SHORT).show();
                progressB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFail(String errorInfo) {
                Log.d("Login","register fail");
                Toast.makeText(MainActivity.this,errorInfo,Toast.LENGTH_SHORT).show();
                nameT.setText("");
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