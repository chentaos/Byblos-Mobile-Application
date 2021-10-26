package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        TextView text = findViewById(R.id.adminWelcomeMsg);
        text.setText(getIntent().getStringExtra("welcomeMSG"));
    }
}