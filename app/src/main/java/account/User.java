package account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public abstract class User {

    public String firstName ;
    public String lastName ;
    public String userName;
    private String password;
    DatabaseReference myRef; //User has a database reference.

    // might need this to retrieve data.
    public User(){

    }

    public User(String name, String passwd) {
        myRef = FirebaseDatabase.getInstance().getReference();
        this.userName = name;
        this.password = passwd;
    }

    /**
     * login method, the reference path is already specified at construction method of each subclasses.
     * @param callBack
     */
    public void login(ListenerCallBack callBack) {
//        callBack.onSuccess();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("Login", "see " + userName + snapshot);

                if (!snapshot.hasChild(userName)) {
                    callBack.onFail("User doesn't exist");
                    return;
                }

                snapshot = snapshot.child(userName);
                String pw = snapshot.child("passwd").getValue(String.class);

                if (password.equals(pw)) {

                    firstName = snapshot.child("firstName").getValue(String.class);
                    lastName = snapshot.child("lastName").getValue(String.class);
                    callBack.onSuccess();  //if we find the matched result, call success callback.

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


    /**
     *
     * @param callBack
     */
    public void register(String firstName, String lastName, ListenerCallBack callBack){

        this.firstName = firstName;
        this.lastName = lastName;

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userName)) {
                    callBack.onFail("Account existed");
                    return;
                }
                myRef.child(userName).setValue(toMap());
                callBack.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login", "fail");
            }
        });
    }

    public String welcomeMSG(){
        return String.format("Welcome %s!\n You have successfully logged in as a ", firstName);
    }

    private Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("passwd", password);
        m.put("firstName", firstName);
        m.put("lastName", lastName);
        m.put("userName", userName);
        return m;
    }
}
