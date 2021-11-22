package account;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

public class Employee extends User {

    private String address;
    private long phoneNumber;
    /**
     *  Constructor for Employee user account.
     */

    public Employee(String userName, String password) {
        super(userName, password);
        setMyRef(getMyRef().child("Users/Customer"));
    }

    public Employee(String name,String password, String address, long phoneNumber){
        super(name,password);
        this.address = address;
        this.phoneNumber = phoneNumber;
        setMyRef(getMyRef().child("Users/Employee"));
    }

    @Override
    public String welcomeMSG() {
        return super.welcomeMSG()+"Employee.";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

