package service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import account.ListenerCallBack;

public class Service {
    Boolean isActivated = false;
    String name;
    private DatabaseReference myRef;

    public Service(){

    }

     public Service(String name, Boolean isActivated){
        myRef = FirebaseDatabase.getInstance().getReference().child("Services");
        this.name = name;
        this.isActivated = isActivated;
     }

    public void updateFromDB(ListenerCallBack callBack) {
//        callBack.onSuccess();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("Login", "see " + userName + snapshot);

                if (!snapshot.hasChild(name)) {
                    myRef.child(name).setValue(new Service(name, false));
                }

                isActivated = snapshot.child(name).child("activated").getValue(Boolean.class);
                callBack.onSuccess();

//                if (password.equals(pw)) {
//
//                    firstName = snapshot.child("firstName").getValue(String.class);
//                    lastName = snapshot.child("lastName").getValue(String.class);
//                    callBack.onSuccess();  //if we find the matched result, call success callback.
//
//                } else {
//                    callBack.onFail("wrong password or role");
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Service update from db", "fail");
            }
        });
    }

    public void writeToDB() {
        myRef.child(name).setValue(this);
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public boolean isActivated() {
        return isActivated;
    }
}
