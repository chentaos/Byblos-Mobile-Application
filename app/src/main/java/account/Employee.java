package account;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

public class Employee extends User {

    public Employee(String name,String password){
        super(name,password);
        this.myRef= myRef.child("Users/Employee");
    }

    @Override
    public String welcomeMSG() {

        return super.welcomeMSG()+"Employee.";
    }
}
