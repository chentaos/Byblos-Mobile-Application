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
import java.util.NoSuchElementException;

public abstract class User {

    public String firstName = "unset";
    public String lastName = "unset";
    protected String userName;

    private String passwd;
    protected String role;

    public DatabaseReference myRef; //User has a database reference.
    /*TODO:
           - When User click register, start a second page.Let User input more information, then
           return the information and fill the information in the user instance.
           - Map the keys and values and then upload to database.
           - change the welcome MSG; change userName to the first name of the user.
           -
     */

    public User(String name, String passwd) {
        this.userName = name;
        this.passwd = passwd;
    }

    public void login(FirebaseDatabase ins, ListenerCallBack callBack) {
        myRef = ins.getReference("User/" + role);

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

                if (passwd.equals(pw)) {
                    Log.d("Login", "Login success");
                    //TODO: when finish the register page, remove the comment below.
                    // firstName = snapshot.child("firstName").getValue(String.class);
                    //lastName = snapshot.child("lastName").getValue(String.class);

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
    public void register(FirebaseDatabase ins, ListenerCallBack callBack) {

        DatabaseReference myRef = ins.getReference("User/" + role);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getChildrenCount();

                if (snapshot.hasChild(userName)) {
                    callBack.onFail("Account existed");
                    return;
                }

                //if non such username exist, then create a new child.
                //TODO: use toMap() to assign instead of this.
                myRef.child(userName).setValue(toMap());
                Log.d("Login", "Success R");
                callBack.onSuccess();
                myRef.removeEventListener(this); //delete this listener. dont know if necessary,(need test);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login", "fail");
            }
        });
    }

    public String welcomeMSG() {
        return String.format("Welcome %s! You are logged in as %s.", firstName, role);
    }

    /**
     * set methods
     */
    public void setUserName(String name) {
        userName = name;
    }

    public void setfirstName(String name) {
        firstName = name;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public void setPasswd(String pw) {
        passwd = pw;
    }


    private Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("password", passwd);
        m.put("firstName", firstName);
        m.put("lastName", lastName);
        m.put("userName", userName);
        return m;
    }
}
