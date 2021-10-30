package com.example.deliverable1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import account.ListenerCallBack;
import service.Service;

public class ServiceDashboard extends AppCompatActivity {

    private Service carRentalService = new Service("carRental", false);
    private Service truckRentalService = new Service("truckRental", false);
    private Service movingAssistanceService = new Service("movingAssistance", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_admin_service_dashboard);
        Switch carRentalSwitch = (Switch) findViewById(R.id.carRentalSwitch);
        Switch truckRentalSwitch = (Switch) findViewById(R.id.truckRentalSwitch);
        Switch movingAssistanceSwitch = (Switch) findViewById(R.id.movingAssistanceSwitch);


        carRentalService.updateFromDB(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                carRentalSwitch.setChecked(carRentalService.isActivated());
                updateSwitchText(carRentalSwitch, carRentalService.isActivated());
            }
            @Override
            public void onFail(String errInfo) {
                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });

        truckRentalService.updateFromDB(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                truckRentalSwitch.setChecked(truckRentalService.isActivated());
                updateSwitchText(truckRentalSwitch, truckRentalService.isActivated());
            }
            @Override
            public void onFail(String errInfo) {
                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });

        movingAssistanceService.updateFromDB(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                movingAssistanceSwitch.setChecked(movingAssistanceService.isActivated());
                updateSwitchText(movingAssistanceSwitch, movingAssistanceService.isActivated());
            }
            @Override
            public void onFail(String errInfo) {
                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });


        carRentalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              updateSwitchText(carRentalSwitch, isChecked);
              carRentalService.setActivated(isChecked);
              carRentalService.writeToDB();
              goToEdit();
            }
        });


        truckRentalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSwitchText(truckRentalSwitch, isChecked);
                truckRentalService.setActivated(isChecked);
                truckRentalService.writeToDB();
            }
        });

        movingAssistanceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSwitchText(movingAssistanceSwitch, isChecked);
                movingAssistanceService.setActivated(isChecked);
                movingAssistanceService.writeToDB();
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

    private void goToEdit(){
        Intent i=new Intent(ServiceDashboard.this,ServiceRequirementEdit.class);
        startActivity(i);
    }
}
