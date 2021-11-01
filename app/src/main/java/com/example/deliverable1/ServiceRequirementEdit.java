package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import service.Service;

public class ServiceRequirementEdit extends AppCompatActivity {

    ListView requirement;
    ArrayAdapter<String> adapter;
    String[] requirements={"Name", "Date of birth", "Address", "Email", "License type",
            "Preferred car type", "Date and time to pick up and return", "Maximum number of kilometers",
            "Area to be used", "Moving start and end location", "Number of movers", "Expected number of boxes"};

    boolean[] req=new boolean[12];

    EditText name,rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requirement_edit);

        requirement=findViewById(R.id.requirementList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,requirements);
        requirement.setAdapter(adapter);

        name=findViewById(R.id.serviceName);
        rate=findViewById(R.id.rate);
    }

    public void create(View view){
        if(valid()){
            for(int i=0;i<requirement.getCount();i++){
                req[i]=requirement.isItemChecked(i);
            }

            String type="";
            switch(view.getId()){
                case R.id.service1:
                    type="CarRental";
                    break;
                case R.id.service2:
                    type="TruckRental";
                    break;
                case R.id.service3:
                    type="MovingAssistant";
                    break;
            }

            Service s=new Service(name.getText().toString(),Double.valueOf(rate.getText().toString()),type,
                    req[0],req[1],req[2],req[3],req[4],req[5],req[6],req[7],req[8],req[9],req[10],req[11]);
            s.writeToDB();
            Log.i("serviceCreate",s.toString());
                    finish();
        }
    }

    private boolean valid(){
        if(name.getText().toString().isEmpty()||rate.getText().toString().isEmpty()||Double.valueOf(rate.getText().toString())==0){
            Toast.makeText(this,"name and rate must be filled",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}