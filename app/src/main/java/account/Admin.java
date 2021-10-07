package account;

public class Admin extends User {
    public Admin(){
        super("Admin");
    }

    public Admin(String userName,String password){
        super(userName,password);
        this.role="Admin";
    }

    @Override
    public void register(ListenerCallBack callBack){
        //display error message "admin User is limit to only one"
        callBack.onFail("Admin is not allowed to register");
    }
}