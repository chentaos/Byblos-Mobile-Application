package com.example.deliverable1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import account.ListenerCallBack;
import service.Service;

public class ServiceDashboard extends AppCompatActivity {

    private Switch carRentalSwitch,truckRentalSwitch,movingAssistanceSwitch;

    private Service carRentalService = new Service("carRental");
    private Service truckRentalService = new Service("truckRental");
    private Service movingAssistanceService = new Service("movingAssistance");

    private EditText rate1,rate2,rate3;
    private boolean start1=false;
    private boolean start2=false;
    private boolean start3=false;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_admin_service_dashboard);
        carRentalSwitch = (Switch) findViewById(R.id.carRentalSwitch);
        truckRentalSwitch = (Switch) findViewById(R.id.truckRentalSwitch);
        movingAssistanceSwitch = (Switch) findViewById(R.id.movingAssistanceSwitch);

        rate1=findViewById(R.id.hourRate1);
        rate2=findViewById(R.id.hourRate2);
        rate3=findViewById(R.id.hourRate3);

        carRentalService.updateFromDB(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                rate1.setText(Double.toString(carRentalService.getRate()));
                carRentalSwitch.setChecked(carRentalService.isActivated());
                updateSwitchText(carRentalSwitch, carRentalService.isActivated());
                if(Double.valueOf(rate1.getText().toString())==0)
                    count++;
                Log.i("countt",String.valueOf(count));
                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFail(String errInfo) {
                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });

        truckRentalService.updateFromDB(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                rate2.setText(Double.toString(truckRentalService.getRate()));
                truckRentalSwitch.setChecked(truckRentalService.isActivated());
                updateSwitchText(truckRentalSwitch, truckRentalService.isActivated());
                if(Double.valueOf(rate1.getText().toString())==0)
                    count++;
                Log.i("countt",String.valueOf(count));
                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFail(String errInfo) {
                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });

        movingAssistanceService.updateFromDB(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                rate3.setText(Double.toString(movingAssistanceService.getRate()));
                movingAssistanceSwitch.setChecked(movingAssistanceService.isActivated());
                updateSwitchText(movingAssistanceSwitch, movingAssistanceService.isActivated());
                Log.i("countt",String.valueOf(count));
                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFail(String errInfo) {
                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });

        updateRateBox();

        carRentalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(toggleSuccess(carRentalSwitch,rate1,isChecked)){
                    carRentalService.setActivated(isChecked);
                    carRentalService.setRate(isChecked ==true ? Double.valueOf(rate1.getText().toString()):0);
                    if(isChecked)
                    count++;
                    if(Double.valueOf(rate3.getText().toString())==0)
                        count++;
                    Log.i("countt",String.valueOf(count));
                    updateSwitchText(carRentalSwitch, isChecked);
                    updateRateBox();
                    carRentalService.writeToDB();
                    if(isChecked && count>3){
                        goToEdit();
                    }
                }
            }
        });


        truckRentalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (toggleSuccess(truckRentalSwitch, rate2, isChecked)) {
                    updateSwitchText(truckRentalSwitch, isChecked);
                    truckRentalService.setActivated(isChecked);
                    truckRentalService.setRate(isChecked ==true ? Double.valueOf(rate2.getText().toString()):0);
                    if(isChecked)
                        count++;
                    if(Double.valueOf(rate3.getText().toString())==0)
                        count++;
                    Log.i("countt",String.valueOf(count));
                    updateSwitchText(truckRentalSwitch, isChecked);
                    updateRateBox();
                    truckRentalService.writeToDB();
                    if(isChecked && count>=3){
                        truckRentalService.setRate(Double.valueOf(rate2.getText().toString()));
                        goToEdit();
                    }
                }
            }
        });

        movingAssistanceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (toggleSuccess(movingAssistanceSwitch, rate3, isChecked)) {
                    updateSwitchText(movingAssistanceSwitch, isChecked);
                    movingAssistanceService.setActivated(isChecked);
                    movingAssistanceService.setRate(isChecked ==true ? Double.valueOf(rate3.getText().toString()):0);
                    if(isChecked)
                        count++;
                    if(Double.valueOf(rate3.getText().toString())==0)
                        count++;
                    Log.i("countt",String.valueOf(count));
                    updateSwitchText(movingAssistanceSwitch, isChecked);
                    updateRateBox();
                    movingAssistanceService.writeToDB();
                    if(isChecked && count>3){
                        goToEdit();
                    }
                }
            }
        });


    }

    private void updateSwitchText(Switch s, boolean isChecked) {
        if(isChecked) {
            s.setText(R.string.deactivate_service);
        } else {
            s.setText(R.string.activate_service);
        }
    }

    private boolean toggleSuccess(Switch s, EditText rate, boolean isChecked){
        if(isChecked) {
            if(Double.valueOf(rate.getText().toString())==0) {
                Toast.makeText(getApplicationContext(), "Can't activate without hour rate", Toast.LENGTH_SHORT).show();
                s.setChecked(false);
                return false;
            }
        }
        return true;
    }

    private void goToEdit(){
        Intent i=new Intent(ServiceDashboard.this,ServiceRequirementEdit.class);
        startActivity(i);
    }

    private void updateRateBox(){
        if(carRentalSwitch.isChecked()){
            rate1.setEnabled(false);
        }else{
            rate1.setEnabled(true);
        }

        if(truckRentalSwitch.isChecked()){
            rate2.setEnabled(false);
        }else{
            rate2.setEnabled(true);
        }

        if(movingAssistanceSwitch.isChecked()){
            rate3.setEnabled(false);
        }else{
            rate3.setEnabled(true);
        }
    }

    private void count(){
        if(Double.valueOf(rate1.getText().toString())==0)
            count++;
        if(Double.valueOf(rate2.getText().toString())==0)
            count++;
        if(Double.valueOf(rate3.getText().toString())==0)
            count++;
    }
}
