package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import account.Admin;
import account.Employee;
import account.ListenerCallBack;
import account.UserList;

public class AdminAccountManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_manager);

        Admin admin = new Admin();
        Log.d("ww","build s");
        UserList<Employee> u  = admin.getEmployeeAccManager();
        u.getNextpage(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                ArrayList userList = u.getList();

                ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(this,android.R.layout.simple_expandable_list_item_1,userList);
                ListView userlistVIew = findViewById(R.id.userList);
                userlistVIew.setAdapter(adapter);
            }

            @Override
            public void onFail(String errInfo) {

            }
        });





    }
    //TODO: srcollable/can go nextpage UI to show list.
    public void preOnClick(View view){

    }

    public void nextOnClick(View view){

    }
}