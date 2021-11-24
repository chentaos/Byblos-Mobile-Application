package com.example.deliverable1;

import account.*;

import android.content.Intent;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private User user;
    private ProgressBar progressB;

    private enum AccountType {admin, customer, employee}
    private AccountType type;
    private int count=0;
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
                switch (i) {
                    case R.id.radioAdmin:
                        type = AccountType.admin;
                        break;
                    case R.id.radioEmployee:
                        type = AccountType.employee;
                        break;
                    case R.id.radioCustomer:
                        type = AccountType.customer;
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Set a default button clicked
        if(count==0){
            RadioButton a = (RadioButton) findViewById(R.id.radioAdmin);
            a.setChecked(true);
            count++;
        }
    }

    private boolean usernameValidate(String name){
        String matchPattern = "^[A-Za-z0-9_-]+$";
        return name.matches(matchPattern);
    }

    public void loginOnClick(View view) {
        EditText name = findViewById(R.id.userName);
        String userName = name.getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
        Log.d("ww",userName+password);

        //or use setError to specify where is missing.
        if (userName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "User name not filled", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "password not filled", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!usernameValidate(userName)){
            Toast.makeText(getApplicationContext(), "userName can only include A-Z,a-z,0-9,_,-", Toast.LENGTH_SHORT).show();
            name.setText("");
            return;
        }

        progressB.setVisibility(View.VISIBLE);
        specifyAccount(userName, password);

        user.login( new ListenerCallBack() {
            @Override
            public void onSuccess() {
                progressB.setVisibility(View.INVISIBLE);
                loadUserMainPage();
            }

            @Override
            public void onFail(String errInfo) {
                progressB.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, errInfo, Toast.LENGTH_SHORT).show();
                name.setText("");
            }
        });
    }

    /**
     * when User click register, start a new page to collect more data.
     * call login after register successfully.
     * @param view
     */
    public void registerOnClick(View view) {

        EditText nameT = findViewById(R.id.userName);
        EditText passT = findViewById(R.id.password);
        String userName = nameT.getText().toString().trim();
        String password = passT.getText().toString().trim();

        if (userName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "User name not filled", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "password not filled", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!usernameValidate(userName)){
            Toast.makeText(getApplicationContext(), "userName can only include A-Z,a-z,0-9,_,-", Toast.LENGTH_SHORT).show();
            nameT.setText("");
            return;
        }

        progressB.setVisibility(View.VISIBLE);
        specifyAccount(userName, password);

        //for user to input more information.
        loadRegisterPage();

    }

    /**
     *  this is a callback of the new register page.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            String lastName =  data.getStringExtra("lastName");
            String firstName = data.getStringExtra("firstName");


            //if register successfully load the main page.
            user.register(firstName, lastName,new ListenerCallBack(){

                @Override
                public void onSuccess() {
                    Log.d("Login","register success");
                    progressB.setVisibility(View.INVISIBLE);
                    loadUserMainPage();
                }

                @Override
                public void onFail(String errInfo) {
                    Log.d("Login","register fail");
                    Toast.makeText(MainActivity.this, errInfo,Toast.LENGTH_SHORT).show();
                    EditText nameT = findViewById(R.id.userName);
                    nameT.setText("");
                    progressB.setVisibility(View.INVISIBLE);

                }
            });
        }

    }


    // start a new page.
    private void loadUserMainPage() {
        Intent intent;

        if(type==AccountType.admin){
            intent = new Intent(MainActivity.this, AdminDashboard.class);
        }

        else if(type==AccountType.employee){
            intent = new Intent(MainActivity.this, EmployeeMainMenu.class);
        }
        else {
           intent = new Intent(MainActivity.this, UserMainMenu.class);
        }

        intent.putExtra("welcomeMSG", user.welcomeMSG());
        intent.putExtra("username", user.getUserName());
        startActivity(intent);
    }

    private void loadRegisterPage(){
        Intent intent=new Intent(MainActivity.this,RegisterPageActivity.class);
        startActivityForResult(intent,0); //deprecated. new use can be found at https://developer.android.com/training/basics/intents/result#java

    }
    /**
     *  this method specifies the type of account.
     */
    private void specifyAccount(String userName, String password) {
        switch (type) {
            case admin:
                this.user = new Admin(userName, password);
                break;
            case customer:
                this.user = new Customer(userName, password);
                break;
            case employee:
                this.user = new Employee(userName, password);
        }
    }
}