package account;



public class Customer extends User {
    //    @Override
//    public void updateServiceList() {
// set a listener for that.
//    }

    public Customer(String userName,String password){
        super(userName,password);
        this.role="Customer";
    }

    public Customer(){
        super("Customer");
    }

}