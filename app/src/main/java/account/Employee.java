package account;


public class Employee extends User {

    public Employee(){
        super("Employee");
    }

    public Employee(String userName,String password){
        super(userName,password);
        this.role="Employee";
    }
}
