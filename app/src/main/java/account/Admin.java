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
        this.role = "Admin";
    }

    private User[] userList;

    @Override
    public boolean login(String userName, String pw, FirebaseDatabase instance) {
       DatabaseReference ref=instance.getReference("User/Admin");

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
    public void register(String userName, String pw, ListenerCallBack callBack){
        //display error message "admin User is limit to only one"
        callBack.onFail("Admin is not allowed to register");
    }

    private User[] getUserList() {
        return new User[0];
    }


    public boolean deleteAcc(User acc){ //cant delete User only use userName because it might have same userName in another type of User.
        return false;
    }

    @Override
    public boolean register(String userName, String pw, FirebaseDatabase ins) {

        return false;
    }
}