package account;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

public class Employee extends User {

    /**
     *  Constructor for Employee user account.
     */
    public Employee(){

    }
    public Employee(String name,String password){
        super(name,password);
        setMyRef(getMyRef().child("Users/Employee"));
    }

    @Override
    public String welcomeMSG() {

        return super.welcomeMSG()+"Employee.";
    }

}
