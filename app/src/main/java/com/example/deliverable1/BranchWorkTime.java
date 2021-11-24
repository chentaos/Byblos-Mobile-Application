package com.example.deliverable1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import account.Employee;
import account.ListenerCallBack;


public class BranchWorkTime extends AppCompatActivity {

    String userName = "aaaa";
    Employee e = new Employee(userName,null);
    TimeSlotAdapter[] ta = new TimeSlotAdapter[7];
    int[] listViewDayID = {R.id.R1,R.id.R2,R.id.R3,R.id.R4,R.id.R5,R.id.R6,R.id.R7};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_work_time);


//        ListView ml = (ListView)findViewById(R.id.R1);
//        List<String> tl = new LinkedList<String>();
//
//        tl.add("00:3420:24");
//        tl.add("12:0013:00");
//
//        ta[0] = new TimeSlotAdapter(BranchWorkTime.this, tl);
//        ml.setAdapter(ta[0]);

        Toast.makeText(getApplicationContext(), "press add button to add working time, long press time interval to delete", Toast.LENGTH_LONG).show();
        e.timeInitiate(new ListenerCallBack() {
            @Override
            public void onSuccess() {
                update();
            }

            @Override
            public void onFail(String errInfo) {
                Log.d("ww",errInfo);
            }
        });

    }


    private void update(){
        for(int i=1;i<=7;i++){
            List tl = e.getDayTimeIntervals(i);
            List<String> timeStrings = new LinkedList<>();

            if(tl==null){
                continue;
            }

            for(Object o: tl){
                String s = o.toString(); // 00121111
                s = s.substring(0,2) + ":" + s.substring(2,4)+ s.substring(4,6)+":"+s.substring(6);
                timeStrings.add(s);
                Log.d("ww","time:"+s);
            }
            if(timeStrings.size()!=0){
                ta[i-1] = new TimeSlotAdapter(BranchWorkTime.this,timeStrings);

                ListView lv = (ListView) findViewById(listViewDayID[i-1]);
                lv.setAdapter(ta[i-1]);
                final int d = i;
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int j, long l) {
                        showUpdateDeleteDialog(d, j, ta[d-1]);
                        return false;
                    }
                });
            }
        }
    }
    private void showUpdateDeleteDialog(int day, int timeIndex, TimeSlotAdapter ta) {

        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_admin_account_manager_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancelBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteBtn);

        dialogBuilder.setTitle("comfirm to delete?");
        final androidx.appcompat.app.AlertDialog b = dialogBuilder.create();
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

                e.deleteTime(day, timeIndex);
                if(ta.getCount() == 1){
                    ta.clear();
                }
                b.dismiss();
            }
        });
    }

    public void addButtonOnClick(View view){

        int day;
        switch (view.getId()){
            case R.id.day1Add:
                day = 1;
                break;
            case R.id.day2Add:
                day = 2;
                break;
            case R.id.day3Add:
                day = 3;
                break;
            case R.id.day4Add:
                day = 4;
                break;
            case R.id.day5Add:
                day = 5;
                break;
            case R.id.day6Add:
                day = 6;
                break;
            case R.id.day7Add:
                day = 7;
                break;
            default:
                day = 1;
        }

        Toast.makeText(getApplicationContext(), "Please enter the starting time", Toast.LENGTH_SHORT).show();
        new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Log.d("ww",hourOfDay+"and "+ minute);
                int startTime = hourOfDay*100+minute;
                Toast.makeText(getApplicationContext(), "Please enter the ending time", Toast.LENGTH_SHORT).show();
                new TimePickerDialog(BranchWorkTime.this, AlertDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Log.d("ww","timeShow: "+ startTime + (hourOfDay*100+minute));
                        if(startTime>= (hourOfDay*100+minute)){
                            Toast.makeText(getApplicationContext(), "StartTime need to be earlier than endTime", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        e.addTime(day, startTime,hourOfDay*100+minute);
                    }
                },0,0,true).show();
            }
        },0,0,true).show();

    }
}