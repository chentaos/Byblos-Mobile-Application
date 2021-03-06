package com.example.deliverable1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceDashboard extends AppCompatActivity {

//    private Switch carRentalSwitch,truckRentalSwitch,movingAssistanceSwitch;
    private Button button1,button2,button3;
    private String activate="Activate Service";
    private String deactivate="Deactivate Service";

//    private Service carRentalService = new Service("carRental");
//    private Service truckRentalService = new Service("truckRental");
//    private Service movingAssistanceService = new Service("movingAssistance");

    private EditText rate1,rate2,rate3;
    private boolean start1=false;
    private boolean start2=false;
    private boolean start3=false;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_admin_service_dashboard);
//        carRentalSwitch = (Switch) findViewById(R.id.carRentalSwitch);
//        truckRentalSwitch = (Switch) findViewById(R.id.truckRentalSwitch);
//        movingAssistanceSwitch = (Switch) findViewById(R.id.movingAssistanceSwitch);
        button1=findViewById(R.id.service1);
        button2=findViewById(R.id.service2);
        button3=findViewById(R.id.service3);


        rate1=findViewById(R.id.hourRate1);
        rate2=findViewById(R.id.hourRate2);
        rate3=findViewById(R.id.hourRate3);

//        carRentalService.updateFromDB(new ListenerCallBack() {
//            @Override
//            public void onSuccess() {
//                rate1.setText(Double.toString(carRentalService.getRate()));
//                button1.setText(carRentalService.isActivated() ? deactivate:activate);
////                updateSwitchText(carRentalSwitch, carRentalService.isActivated());
////                if(Double.valueOf(rate1.getText().toString())==0)
////                    count++;
////                Log.i("countt",String.valueOf(count));
////                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onFail(String errInfo) {
//                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        truckRentalService.updateFromDB(new ListenerCallBack() {
//            @Override
//            public void onSuccess() {
//                rate2.setText(Double.toString(truckRentalService.getRate()));
//                button2.setText( truckRentalService.isActivated() ? deactivate:activate);
////                updateSwitchText(truckRentalSwitch, truckRentalService.isActivated());
////                if(Double.valueOf(rate1.getText().toString())==0)
////                    count++;
////                Log.i("countt",String.valueOf(count));
////                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onFail(String errInfo) {
//                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        movingAssistanceService.updateFromDB(new ListenerCallBack() {
//            @Override
//            public void onSuccess() {
//                rate3.setText(Double.toString(movingAssistanceService.getRate()));
//                button3.setText(movingAssistanceService.isActivated() ? deactivate:activate);
//                updateRateBox();
//            }
//            @Override
//            public void onFail(String errInfo) {
//                Toast.makeText(ServiceDashboard.this, errInfo, Toast.LENGTH_SHORT).show();
//            }
//        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(activateSuccess(rate1)){
//                    carRentalService.setActivated(!carRentalService.isActivated());
//                    carRentalService.setRate(carRentalService.isActivated() ==true ? Double.valueOf(rate1.getText().toString()):0);
//                    button1.setText(carRentalService.isActivated() ? deactivate:activate);
//                    updateRateBox();
//                    carRentalService.writeToDB();
//                    if(carRentalService.isActivated()){
//                        goToEdit();
//                    }
//                }
                goToEditPage();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(activateSuccess(rate2)){
//                    truckRentalService.setActivated(! truckRentalService.isActivated());
//                    truckRentalService.setRate( truckRentalService.isActivated() ==true ? Double.valueOf(rate2.getText().toString()):0);
//                    button2.setText( truckRentalService.isActivated() ? deactivate:activate);
//                    updateRateBox();
//                    truckRentalService.writeToDB();
//                    if( truckRentalService.isActivated()){
//                        goToEditPage();
//                    }
//                }
                goToEditPage();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(activateSuccess(rate3)){
//                    movingAssistanceService.setActivated(!movingAssistanceService.isActivated());
//                    movingAssistanceService.setRate(movingAssistanceService.isActivated() ==true ? Double.valueOf(rate3.getText().toString()):0);
//                    button3.setText(movingAssistanceService.isActivated() ? deactivate:activate);
//                    updateRateBox();
//                    movingAssistanceService.writeToDB();
//                    if(movingAssistanceService.isActivated()){
//                        goToEdit();
//                    }
//                }
                goToEditPage();
            }
        });

    }
    private void goToEditPage(){
        Intent i=new Intent(ServiceDashboard.this,ServiceRequirementEdit.class);
        startActivity(i);
    }

}
