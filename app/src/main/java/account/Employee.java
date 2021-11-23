package account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Employee extends User {

    private String address;
    private String phoneNumber;
    private DatabaseReference myEmRef = getMyRef().child("Users/Employee");
    /**
     *  Constructor for Employee user account.
     */
    public Employee(){

    }

    public Employee(String userName, String password) {
        super(userName, password);
        setMyRef(getMyRef().child("Users/Employee"));
    }

    public Employee(String name,String password, String address, String phoneNumber){
        super(name,password);
        this.address = address;
        this.phoneNumber = phoneNumber;
        setMyRef(getMyRef().child("Users/Employee"));
    }


    @Override
    public String welcomeMSG() {
        return super.welcomeMSG()+"Employee." ;
    }

    public void setProfile(String address, String phoneNumber){
            this.phoneNumber = phoneNumber;
            this.address = address;
            getMyRef().child(getUserName()).child("PhoneNumber").setValue(phoneNumber);
            getMyRef().child(getUserName()).child("address").setValue(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DatabaseReference getMyEmRef() {
        return myEmRef;
    }
}

