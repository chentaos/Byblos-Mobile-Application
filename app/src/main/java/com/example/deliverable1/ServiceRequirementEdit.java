package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ServiceRequirementEdit extends AppCompatActivity {

    ListView requirement;
    ArrayAdapter<String> adapter;
    String[] requirements={"Name", "Date of birth", "Address", "Email", "License type",
            "Preferred car type", "Date and time to pick up and return", "Maximum number of kilometers",
            "Area to be used", "Moving start and end location", "Number of movers", "Expected number of boxes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requirement_edit);

        requirement=findViewById(R.id.requirement);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,requirements);
        requirement.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        return super.onOptionsItemSelected(item);
    }


    public void save(View view){
        finish();
    }
}