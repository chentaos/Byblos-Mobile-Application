package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserMainMenu extends AppCompatActivity {
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);

        TextView eT = findViewById(R.id.welcomeText);
        eT.setText(getIntent().getStringExtra("welcomeMSG"));
        userName = getIntent().getStringExtra("userName");

    }

    public void availableServiceOnClick(View view){
        Intent intent = new Intent(UserMainMenu.this, ByblosBranchSearch.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}