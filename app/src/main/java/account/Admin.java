package account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.NoSuchElementException;

public class Admin extends User {

    public Admin() {
        super();
    }

//    private User[] userList;

    @Override
    public void login(String userName, String pw, ListenerCallBack callBack) throws IllegalArgumentException {
        //Query qUserName =

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User/Admin");


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
                Log.d("Login", "still running? : yes" + pw + "Admin");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Login", "fail");
            }
        });
    }

    @Override
    public void register(String userName, String pw, ListenerCallBack callBack){
        //display error message "admin User is limit to only one"
        callBack.onFail("Admin is not allowed to register");
    }

//    private User[] getUserList() {
//        return new User[0];
//    }
//
//
//    public boolean deleteAcc(User acc){ //cant delete User only use userName because it might have same userName in another type of User.
//        return false;
//    }

    public String welcomeMSG(){
        return String.format("Welcome %s! You are logged in as Admin.",userName);
    }
}