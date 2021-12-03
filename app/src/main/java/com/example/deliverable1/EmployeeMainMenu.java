package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import account.Employee;
import branch.Branch;
import service.ServiceRequest;

public class EmployeeMainMenu extends AppCompatActivity {
    String userName;
    DatabaseReference dbBranch = FirebaseDatabase.getInstance().getReference().child("Branch");
    List<Branch> branchList;
    boolean isPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivty_employee_main_menu);
        userName = getIntent().getStringExtra("userName");
        Employee em = new Employee(userName,"");
        TextView eT = findViewById(R.id.welcomeText);
        branchList = new ArrayList<>();

        em.getMyRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String address = snapshot.child(userName).child("address").getValue(String.class);
                String phoneNumber = snapshot.child(userName).child("PhoneNumber").getValue(String.class);
                String welcomeMsg = getIntent().getStringExtra("welcomeMSG");
                String result = welcomeMsg + "\n your address is " + address + "\nyour phone number is "+ phoneNumber;
                eT.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getNbPending();
    }

    private void getNbPending(){
        dbBranch.orderByChild("employee").equalTo(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchList.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch b = postSnapshot.getValue(Branch.class);
                    branchList.add(b);
                }

                for (Branch b : branchList) {
                    dbBranch.child(b.getName()).child("requests").child("submittedForms").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                ServiceRequest sR = postSnapshot.getValue(ServiceRequest.class);
                                if (sR.isPending()){
                                    showPendingMessage(sR.getCustomerName());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showPendingMessage(String customer){
        Toast.makeText(getApplicationContext(), "hello " + userName + " you have one pending request from " + customer, Toast.LENGTH_SHORT).show();
    }

    public void openSettingOnClick(View view){
        Intent intent;
        intent = new Intent(EmployeeMainMenu.this, EmployeeSetting.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));

        startActivity(intent);
    }

    public void pendingServiceOnClick(View view){
        Intent intent = new Intent(EmployeeMainMenu.this, EmployeeServiceRequest.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    public void offeredServiceOnClick(View view){
        Intent intent = new Intent(EmployeeMainMenu.this, EmployeeServiceOffered.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}