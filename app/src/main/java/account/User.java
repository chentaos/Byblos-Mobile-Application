package account;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.example.deliverable1.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.widget.Toast;

import java.util.NoSuchElementException;

public abstract class User{
    public String userName;
    public String role; // flag of the database?
    //private Service[] serviceTypes;
    //private Account[] userList;
    protected boolean exist;
    //public String userInterface;
    // public abstract void updateServiceList(); dont need.Asynchronous.
    private boolean loginState = false; // not necessary now


    public abstract boolean login(String userName, String pw, FirebaseDatabase instance);

    public void login(String userName, String pw,ListenerCallBack callBack){
        //Query qUserName =

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

//        myRef.child(userName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    String passwd = task.getResult().child("passwd").getValue(String.class);
//                    String role1 = task.getResult().child("role").getValue(String.class);
//                    if(passwd == null|| role1 == null){
//                        return;
//                    }
//                    if(passwd.equals(pw) && role1.equals(role)){
//                        loginState = true;
//                        Log.d("Login","Success");
//
//                    }
//                    Log.d("Login",pw+role);
//                }
//            }
//        });
////

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getChildrenCount();

                if(!snapshot.hasChild(userName)){
                    callBack.onFail("wrong password or username");
                    return;
                }
                snapshot = snapshot.child(userName);
                String passwd = snapshot.child("passwd").getValue(String.class);
                String role1 = snapshot.child("role").getValue(String.class);

                if(passwd == null|| role1 == null){
                    throw new NoSuchElementException("found the username in database but no passwd or role elements");
                }

                if(passwd.equals(pw) && role1.equals(role)){
                    loginState = true;
                    Log.d("Login","Success L");
                    callBack.onSuccess();
                }else{
                    callBack.onFail("wrong password or role");
                }
                Log.d("Login","still running? : yes" + pw+role);
            }


        @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login","fail");
            }
        });



//        Q: is there a synchronous method for retrieving data from database
        //if(!loginState){
        ////            throw new IllegalArgumentException("wrong username or password");
        ////        }
        // this if statement always runs first.

        //another way to check if input matches the database, using Query.
//        Query qUserName = myRef.orderByKey().equalTo(userName);
//        //qUserName.orderByChild("role").equalTo(role); can't embed search?
//        ValueEventListener temp = qUserName.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot t:snapshot.getChildren()){
//                    String a = t.child("role").getValue(String.class);
//                    String b = t.child("passwd").getValue(String.class);
//                    Log.d("Login",t.getKey().toString() + "  "+ t.child("passwd").getValue().toString());
//                    if(t.child("role").getValue().toString().equals(role)){//checkrole
//                        if(t.child("passwd").getValue().toString().equals(pw)){//checkPw
//                            loginState = true;
//                            Log.d("Login","success");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("Login","fail");
//            }
//        });
//        if(!loginState){
//            throw new IllegalArgumentException("wrong username or password");
//        }
        //myRef.removeEventListener(temp);
    }

    public abstract boolean register(String userName, String pw, FirebaseDatabase ins);

    public void register(String userName, String pw, ListenerCallBack callBack){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getChildrenCount();

                if(snapshot.hasChild(userName)){
                    callBack.onFail("username already existed");
                    return;
                }
                //if non such username exist, then create a new child.
                myRef.child(userName+"/passwd").setValue(pw);
                myRef.child(userName+"/role").setValue(role);

                Log.d("Login","Success R");
                callBack.onSuccess();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login","fail");
            }
        });
    }

    public String welcomeMSG(){
        return String.format("Welcome %s! You are logged in as %s.",userName,role);
    }
}
