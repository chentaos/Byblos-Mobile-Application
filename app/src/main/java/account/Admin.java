package account;


import com.google.firebase.database.FirebaseDatabase;

public class Admin extends User {

    public Admin(){

    }
    /**
     *  constructor for Admin user account.
     */
    public Admin(String userName, String password) {
        super(userName, password);
        setMyRef(getMyRef().child("Users/Admin"));
    }


    public void register(String a,String b,ListenerCallBack callBack) {
        //display error message "admin User is limit to only one"
        callBack.onFail("Admin is not allowed to register");
    }


    public String welcomeMSG() {
        return super.welcomeMSG()+"Admin.";
    }

}