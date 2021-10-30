package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        TextView text = findViewById(R.id.adminWelcomeMsg);
        text.setText(getIntent().getStringExtra("welcomeMSG"));

    }

    public void openServiceOnClick(View view){
        Intent intent;
        intent = new Intent(AdminDashboard.this, ServiceDashboard.class);
        startActivity(intent);
    }
}