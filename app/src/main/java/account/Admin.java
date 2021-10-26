package account;


import com.example.deliverable1.AdminDashboard;
import com.example.deliverable1.UserMainMenu;

public class Admin extends User {

    public Admin() {
        setFirstName("Admin");
        setLastName("Admin");
    }

    /**
     * constructor for Admin user account.
     */
    public Admin(String userName, String password) {
        super(userName, password);
        setMyRef(getMyRef().child("Users/Admin"));
        setFirstName("Admin");
        setLastName("Admin");
    }


    public void register(String a, String b, ListenerCallBack callBack) {
        //display error message "admin User is limit to only one"
        callBack.onFail("Admin is not allowed to register");
    }


    public String welcomeMSG() {
        return super.welcomeMSG() + "Admin.";
    }

}