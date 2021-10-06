package com.example.deliverable1;

import account.*;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

//class User1{
//    public String passwd;
//    public String role;
//    public String extraElement;
//
//    public User1(){
//
//    }
//    public User1(double passwd, String role,String extraElement){
//        this.passwd = String.valueOf(passwd);
//        this.extraElement = extraElement;
//        this.role = role;
//    }
//} getValue(User1.class)will not fill the elements that the element names dont match database.

public class MainActivity extends AppCompatActivity {

    private User user;
    private FirebaseDatabase dbInstance;
    private DatabaseReference adminRef;
    private DatabaseReference employeeRef;
    private DatabaseReference customerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Users");




        //handle radioButtons, add a listener and change User whenever the user type changes.
        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                //Toast.makeText(getApplicationContext(),rb.getText(),Toast.LENGTH_SHORT).show();
                switch (i){
                    case R.id.radioAdmin:
                        user = new Admin();
                        break;
                    case R.id.radioEmployee:
                        user = new Employee();
                        break;
                    case R.id.radioCustomer:
                        user = new Customer();
                        break;
                    default:
                        //?
                }
            }
        });


        //myRef.setValue("Hello, World!");

//        Query q = myRef.orderByChild("role").equalTo("Customer");
//        //q = q.orderByValue().equalTo("ababab");
//        q.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot t: snapshot.getChildren()){
//                    User1 u = t.getValue(User1.class);
//                    Log.d("MainActivity","test:"+ t.getKey().toString() + t.getValue().toString() + u.passwd);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("MainActivity","fail",error.toException());
//            }
//        });


//        ValueEventListener a1 = myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.child("admin").child("passwd").getValue(String.class);
//                Log.d("MainActivity", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("MainActivity","fail",error.toException());
//            }
//        });

        //myRef.removeEventListener(a1); not synchronized
        //myRef.setValue("admin");
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbInstance=FirebaseDatabase.getInstance();
//        adminRef=dbInstance.getReference("User/Admin");
//        employeeRef=dbInstance.getReference("User/Employee");
//        customerRef=dbInstance.getReference("User/Customer");
    }

    public void loginOnClick(View view) {
        String userName = ((EditText)findViewById(R.id.userName)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();

        //or use setError to specify where is missing.
        if(user == null || userName.isEmpty() || password.isEmpty()){
           Toast.makeText(getApplicationContext(),"information not filled",Toast.LENGTH_SHORT).show();
            return;
        }

        // user login
//        user.login(userName, password, new ListenerCallBack() {
//            @Override
//            public void onSuccess() {
//                user.userName = userName;
//                loadUserMainPage();
//            }
//
//            @Override
//            public void onFail(String errorInfo) {
//                Log.d("Login","login fail");
//                Toast.makeText(MainActivity.this,errorInfo,Toast.LENGTH_SHORT).show();
//            }
//        });

        if(user.login(userName,password,dbInstance)){
            Log.d("Login","login success");
            loadUserMainPage();
        }else{
            Log.d("Login","login fail2");
            Toast.makeText(MainActivity.this,"failed2",Toast.LENGTH_SHORT).show();
        }

    }

    // start a new page.
    public void loadUserMainPage(){
        Intent intent = new Intent(MainActivity.this, UserMainMenu.class);
        intent.putExtra("welcomeMSG",user.welcomeMSG());
        startActivity(intent);
    }


    public void registerOnClick(View view) {
        EditText nameT = (EditText)findViewById(R.id.userName);
        String userName = nameT.getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();

        //or use setError to specify where is missing.
        if(user == null || userName.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(),"information not filled",Toast.LENGTH_SHORT).show();
            return;
        }

        // user login
//        user.register(userName, password, new ListenerCallBack() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MainActivity.this,"registered",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFail(String errorInfo) {
//                Log.d("Login","register fail");
//                Toast.makeText(MainActivity.this,errorInfo,Toast.LENGTH_SHORT).show();
//                nameT.setText("");
//            }
//        });
        if(!user.register(userName,password,dbInstance)){
            Log.d("Login","register fail");
                Toast.makeText(MainActivity.this,"register failed",Toast.LENGTH_SHORT).show();
                nameT.setText("");
                //password to "" too
        }else{
            loadUserMainPage();
            Toast.makeText(MainActivity.this,"registered",Toast.LENGTH_SHORT).show();
        }

    }
}