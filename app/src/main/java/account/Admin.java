package account;


import com.google.firebase.database.FirebaseDatabase;

public class Admin extends User {


    public Admin(String userName, String password) {
        super(userName, password);
        this.myRef= FirebaseDatabase.getInstance().getReference("User/Admin");
    }


    public void register(ListenerCallBack callBack) {
        //display error message "admin User is limit to only one"
        callBack.onFail("Admin is not allowed to register");
    }


    public String welcomeMSG() {
        return super.welcomeMSG()+"Admin.";
    }

//    private User[] getUserList() {
//        return new User[0];
//    }
//    public boolean deleteAcc(User acc){ //cant delete User only use userName because it might have same userName in another type of User.
//        return false;
//    }
}