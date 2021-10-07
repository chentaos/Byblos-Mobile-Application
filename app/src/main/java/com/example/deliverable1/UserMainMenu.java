package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserMainMenu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);

        TextView eT = findViewById(R.id.welcomeText);
        eT.setText(getIntent().getStringExtra("welcomeMSG"));
    }
}