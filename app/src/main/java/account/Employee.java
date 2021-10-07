package account;

import android.util.Log;

public class Employee extends User {

    public Employee(String name,String password){
        super(name,password);
        this.dbPath ="Employee";
    }

    @Override
    public String welcomeMSG() {

        return super.welcomeMSG()+"Employee.";
    }
}
