package service;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import account.ListenerCallBack;

public class Service {
    private Boolean isActivated = false;
    private double hourlyRate=0;
    private String name;
    private DatabaseReference myRef;

    public Service(){

    }

     public Service(String name){
        myRef = FirebaseDatabase.getInstance().getReference().child("Services");
        this.name = name;
     }

    public void updateFromDB(ListenerCallBack callBack) {
//        callBack.onSuccess();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("Login", "see " + userName + snapshot);

//                if (!snapshot.hasChild(name)) {
//                    myRef.child(name).setValue(this);
//                }
                snapshot = snapshot.child(name);
                Number num = (Number) snapshot.child("rate").getValue();
                if (num != null) {
                    hourlyRate = num.doubleValue();
                }
                isActivated = hourlyRate == 0 ? false:true;
                callBack.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Service update from db", "fail");
            }
        });
    }

    public void writeToDB() {
        myRef.child(name).setValue(this);
        Log.d("writetodb","here");
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setRate(double rate){ hourlyRate=rate;}

    public double getRate() {
        return hourlyRate;
    }
}
