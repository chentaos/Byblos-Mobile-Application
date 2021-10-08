package account;


import com.google.firebase.database.FirebaseDatabase;

public class Customer extends User {

    /**
     *  constructor for the customer User account.
     */
    public Customer(String userName, String password) {
        super(userName, password);
        setMyRef(getMyRef().child("Users/Customer"));
    }

    public Customer(String userName, String password, FirebaseDatabase firebaseDatabase) {
        super(userName, password, firebaseDatabase);
//        this.myRef= myRef.child("Users/Customer");
    }

    public String welcomeMSG() {
        return super.welcomeMSG()+"Customer.";
    }
}