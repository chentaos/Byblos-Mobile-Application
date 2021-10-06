package account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.NoSuchElementException;

public class Employee extends User {

//    public void rejectRequest(Service s){
//
//    }
//    public void acceptRequest(Service s){
//
//    }

    //    @Override
//    public void updateServiceList() {
//
//    }
    public Employee() {
        super();
    }

    @Override
    public void login(String userName, String pw, ListenerCallBack callBack) throws IllegalArgumentException {
        //Query qUserName =

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User/Employee");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getChildrenCount();

                if (!snapshot.hasChild(userName)) {
                    callBack.onFail("wrong password or username");
                    return;
                }
                snapshot = snapshot.child(userName);
                String passwd = snapshot.child("passwd").getValue(String.class);

                if (passwd == null) {
                    throw new NoSuchElementException("found the username in database but no passwd or role elements");
                }

                if (passwd.equals(pw)) {
                    Log.d("Login", "Success L");
                    callBack.onSuccess();
                } else {
                    callBack.onFail("wrong password or role");
                }
                Log.d("Login", "still running? : yes" + pw + "Employee");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login", "fail");
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

    @Override
    public void register(String userName, String pw, ListenerCallBack callBack){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User/Employee");
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
                myRef.child(userName+"/userName").setValue(userName);

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
        return String.format("Welcome %s! You are logged in as Employee.",userName);
    }
}
