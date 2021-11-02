package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;
import account.*;

import android.app.AlertDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.util.ArrayList;

public class AdminAccountManager extends AppCompatActivity {
    ListView userlistVIew;
    Admin admin;
    UserList u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_manager);

        admin = new Admin();
        userlistVIew = findViewById(R.id.userList);
        RadioGroup rg = findViewById(R.id.serviceRadioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.eRadio:
                        u = admin.getEmployeeAccManager();
                        break;
                    case R.id.cRadio:
                        u = admin.getCustomerAccManager();
                }

                u.getNextpage(new ListenerCallBack() {
                    @Override
                    public void onSuccess() {
                        showList();
                    }

                    @Override
                    public void onFail(String errInfo) {

                    }
                });
            }

        });



        userlistVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<User> userList = u.getList();
                User user = userList.get(i);
                showDeleteDialog(user);
            }
        });
    }




    private void showDeleteDialog(final User user) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_admin_account_manager_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancelBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);

        dialogBuilder.setTitle(user.getUserName());
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    b.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u.delete(user);
                showList();
                b.dismiss();
            }
        });
    }

    public void showList(){
        ArrayList<User> list = u.getList();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(AdminAccountManager.this,android.R.layout.simple_expandable_list_item_1, list);
        userlistVIew.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Set a default button clicked
        RadioButton a = findViewById(R.id.eRadio);
        a.setChecked(true);


    }


    public void preOnClick(View view){
        u.getPrevPage(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                showList();
            }

            @Override
            public void onFail(String errInfo) {
                Toast.makeText(AdminAccountManager.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void nextOnClick(View view){
        u.getNextpage(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                showList();
            }

            @Override
            public void onFail(String errInfo) {
                Toast.makeText(AdminAccountManager.this, errInfo, Toast.LENGTH_SHORT).show();
            }
        });
    }


}