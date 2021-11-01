package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        intent = new Intent(AdminDashboard.this, ServiceRequirementEdit.class);
        startActivity(intent);
    }

    public void openServiceManageOnClick(View view){
        Intent intent;
        intent = new Intent(AdminDashboard.this, ServiceManage.class);
        startActivity(intent);
    }

    public void accountManageOnclick(View view) {
        startActivity(new Intent(AdminDashboard.this, AdminAccountManager.class));
    }
}