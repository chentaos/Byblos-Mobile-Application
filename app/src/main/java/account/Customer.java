package account;


import com.google.firebase.database.FirebaseDatabase;

public class Customer extends User {

    public Customer(String userName, String password) {
        super(userName, password);
        this.myRef= myRef.child("Users/Customer");
    }


    public String welcomeMSG() {
        return super.welcomeMSG()+"Customer.";
    }
}