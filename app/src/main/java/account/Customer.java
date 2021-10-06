package account;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.NoSuchElementException;

public class Customer extends User {
//    @Override
//    public void updateServiceList() {
//
//    }
    public Customer(){
        this.role = "Customer";
    }

    @Override
    public boolean login(String userName, String pw, FirebaseDatabase instance) {
        DatabaseReference ref=instance.getReference("User/Customer");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getChildrenCount();

                if(!snapshot.hasChild(userName)){
                    exist=false;
                    return;
                }
                snapshot = snapshot.child(userName);
                String passwd = snapshot.child("passwd").getValue(String.class);
                String role1 = snapshot.child("role").getValue(String.class);

                if(passwd == null|| role1 == null){
                    throw new NoSuchElementException("found the username in database but no passwd or role elements");
                }

                if(passwd.equals(pw) && role1.equals(role)){
                    exist=true;
                    Log.d("Login","Success L");
                }else{
                    exist=false;
                }
                Log.d("Login","still running? : yes" + pw+role);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login","fail");
            }
        });

        return exist;
    }

    @Override
    public boolean register(String userName, String pw, FirebaseDatabase ins) {


        DatabaseReference myRef = ins.getReference("User/Customer");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getChildrenCount();

                if(snapshot.hasChild(userName)){
                    exist=true;
                    return;
                }
                //if non such username exist, then create a new child.
                exist=false;
                myRef.child(userName+"/passwd").setValue(pw);
                myRef.child(userName+"/userName").setValue(userName);

                Log.d("Login","Success R");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login","fail");
            }
        });

        return !exist;
    }
}

