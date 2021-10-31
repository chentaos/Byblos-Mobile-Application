package com.example.deliverable1;

import androidx.appcompat.app.AppCompatActivity;
import account.*;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import account.Admin;
import account.Employee;
import account.ListenerCallBack;
import account.UserList;

public class AdminAccountManager extends AppCompatActivity {
    ListView userlistVIew;
    Admin admin = new Admin();
    UserList u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_manager);
        userlistVIew = findViewById(R.id.userList);
        RadioGroup rg = findViewById(R.id.accRadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.eRadio:
                        u = admin.getEmployeeAccManager();
                        break;
                    case R.id.cRadio:
                        u = admin.getCustomerAccManager();
                        break;
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

        userlistVIew.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<User> userList = u.getList();
                User user = userList.get(i);
                showDeleteDialog(user.getUserName());
                return true;
            }
        });

        userlistVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),"Long press to delete account",Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void showDeleteDialog(final String userName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_admin_account_manager_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancelBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);

        dialogBuilder.setTitle("fuc");
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
                u.delete(userName);
                b.dismiss();
            }
        });
    }

    public void showList(){
        ArrayList<User> list = u.getList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminAccountManager.this,android.R.layout.simple_expandable_list_item_1);
        for(User u1 : list){
            adapter.add(u1.getUserName());
        }
        userlistVIew.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Set a default button clicked
        RadioButton a = (RadioButton) findViewById(R.id.eRadio);
        a.setChecked(true);


    }


    //TODO: srcollable/can go nextpage UI to show list.
    public void preOnClick(View view){
        u.getPrevPage(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                showList();
            }

            @Override
            public void onFail(String errInfo) {

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

            }
        });
    }


}