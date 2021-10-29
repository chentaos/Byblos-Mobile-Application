package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
                u.getNextpage(new ListenerCallBack() {
                    @Override
                    public void onSuccess() {
                        u.getPrevPage(new ListenerCallBack() {
                            @Override
                            public void onSuccess() {
                                // Matryoshka coding.
                                u.delete("123");
                            }

                            @Override
                            public void onFail(String errInfo) {

                            }
                        });

                    }

                    @Override
                    public void onFail(String errInfo) {

                    }
                });
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