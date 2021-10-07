package account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class User implements Serializable {

    private String firstName ;
    private String lastName ;
    private String userName;
    private String password;
    protected DatabaseReference myRef; //User has a database reference.
    
    public User(String name, String passwd) {
        this.userName = name;
        this.password = passwd;
    }

    public void login( ListenerCallBack callBack) {
//        callBack.onSuccess();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Login", "see " + userName + snapshot);

                if (!snapshot.hasChild(userName)) {
                    callBack.onFail("User doesn't exist");
                    return;
                }

                snapshot = snapshot.child(userName);
                String pw = snapshot.child("password").getValue(String.class);

                if (password.equals(pw)) {
                    Log.d("Login", "Login success");
                    firstName = snapshot.child("firstName").getValue(String.class);
                    lastName = snapshot.child("lastName").getValue(String.class);
                    callBack.onSuccess();
                } else {
                    callBack.onFail("wrong password or role");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login", "fail");
            }
        });
    }


    //need more information
    public void checkAccountExist( ListenerCallBack callBack) {
//        callBack.onSuccess();


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userName)) {
                    callBack.onFail("Account existed");
                    return;
                }

                callBack.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login", "fail");
            }
        });
    }

    public void register(String firstName,String lastName){
        this.lastName=lastName;
        this.firstName=firstName;
        myRef.child(userName).setValue(toMap());
        
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                myRef.child(userName).setValue(toMap());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("Login", "fail");
//            }
//        });
    }

    public String welcomeMSG(){
        return String.format("Welcome %s!\n You have successfully logged in as a ", firstName);
    }

    private Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("password", password);
        m.put("firstName", firstName);
        m.put("lastName", lastName);
        m.put("userName", userName);
        return m;
    }
}
