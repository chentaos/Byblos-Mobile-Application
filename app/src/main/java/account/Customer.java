package account;


public class Customer extends User {

    public Customer(String userName, String password) {
        super(userName, password);
        this.dbPath = "Customer";
    }


    public String welcomeMSG() {
        return super.welcomeMSG()+"Customer.";
    }
}