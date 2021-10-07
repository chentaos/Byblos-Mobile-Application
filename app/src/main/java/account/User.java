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

public abstract class User{

    protected String userName;
    protected String role;
    private String passwd;
    /*TODO:
           - When User click register, start a second page.Let User input more information, then
           return the information and fill the information in the user instance.
           - Map the keys and values and then upload to database.
           - change the welcome MSG; change userName to the first name of the user.
           -


     */
    public String firstName = "unset";
    public String lastName = "unset";



    public DatabaseReference myRef; //User has a database reference.

    public User(){
        // for firebase to auto map when retrieving data.
    }

    public User(String name, String passwd){
        this.userName=name;
        this.passwd=passwd;
    }

    public User(String role){
        this.role = role;

    }

    public void login(ListenerCallBack callBack) {
        myRef = FirebaseDatabase.getInstance().getReference("Users/" + role);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Login", "see "+ userName + snapshot);

                if (!snapshot.hasChild(userName)) {
                    callBack.onFail("wrong password or username");
                    return;
                }

                snapshot = snapshot.child(userName);
                String pw = snapshot.child("passwd").getValue(String.class);

                if (pw == null) {
                    throw new NoSuchElementException("found the username in database but no passwd"); //seems not possible.
                }

                if (passwd.equals(pw)) {

                    Log.d("Login", "Success L");
                    //TODO: when finish the register page, remove the comment below.
                   // firstName = snapshot.child("firstName").getValue(String.class);
                    //lastName = snapshot.child("lastName").getValue(String.class);

                    callBack.onSuccess();

                    myRef.removeEventListener(this); //delete this listener. dont know if necessary,(need test);

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
    public void register(ListenerCallBack callBack){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getChildrenCount();

                if(snapshot.hasChild(userName)){
                    callBack.onFail("username already existed");
                    return;
                }

                //if non such username exist, then create a new child.
                //TODO: use toMap() to assign instead of this.
                myRef.child(userName).setValue(toMap());

                Log.d("Login","Success R");
                callBack.onSuccess();
                myRef.removeEventListener(this); //delete this listener. dont know if necessary,(need test);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login","fail");
            }
        });
    }


    public String welcomeMSG(){
        return String.format("Welcome %s! You are logged in as %s.",firstName,role);
    }

    /**
     * set methods
     *
     */
    public void setUserName(String name){
        userName=name;
    }
    public void setfirstName(String name){
        firstName = name;
    }
    public void setLastName(String name){
        lastName = name;
    }
    public void setPasswd(String pw) {
        passwd = pw;
    }

    /**
     * use to register.
     *
     */
    private Map<String,Object> toMap(){
        Map<String, Object> m = new HashMap<>();
        m.put("passwd",passwd);
        m.put("firstName",firstName);
        m.put("lastName",lastName);
        m.put("userName",userName);
        //enough for now.

        return m;
    }
}
