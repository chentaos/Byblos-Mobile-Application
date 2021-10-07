package com.example.deliverable1;

import account.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPageActivity extends AppCompatActivity {

    EditText firsrName;
    EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        firsrName= (EditText) findViewById(R.id.firstName);
        lastName= (EditText) findViewById(R.id.lastName);
    }

    public void continueRegister(View view){
        String firstName=firsrName.getText().toString().trim();
        String lastName=firsrName.getText().toString().trim();
        String msg;
        User user;

        if(firstName.isEmpty()){
            Toast.makeText(getApplication(),"Fisrt name can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastName.isEmpty()){
            Toast.makeText(getApplicationContext(),"Last name can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i=getIntent();
        user=(User)i.getSerializableExtra("User");
        user.register(firstName,lastName);
        Toast.makeText(getApplicationContext(),"Register sucess",Toast.LENGTH_SHORT).show();
        loadUserMainPage(user);
    }

    private void loadUserMainPage(User user) {
        Intent intent = new Intent(RegisterPageActivity.this, UserMainMenu.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

}