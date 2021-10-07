package account;

public class Employee extends User {

    public Employee(String name,String password){
        super(name,password);
        this.role="Employee";
    }
}
