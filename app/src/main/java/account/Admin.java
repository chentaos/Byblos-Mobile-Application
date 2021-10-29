package account;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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


    //D2
    private class AccList<E extends User> implements UserList<E> {

        private ArrayList<E> userlist;
        private DatabaseReference mdb;
        private String currentUserNamePointer;

        private final Class<E> c;

        public AccList(String category, Class<E> c) {
            this.c = c;
            userlist = new ArrayList<E>();
            mdb = FirebaseDatabase.getInstance().getReference().child("Users/" + category);
            currentUserNamePointer = null;
        }

        @Override
        public void getNextpage(ListenerCallBack callBack) {
            Log.d("ww", "getnextpage");
            if (userlist.size() == 0) {

                Query qName = mdb.orderByKey().limitToFirst(pageLength);

                qName.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot t : snapshot.getChildren()) {
                            //Log.d("ww", t.toString());
                            E temp = t.getValue(c); // getValue here cant not use generic, ****. Solution at https://stackoverflow.com/questions/48237786/best-practice-for-using-generics-with-firebase-snapshot-getvalue
                            userlist.add(temp);
                            Log.d("ww", temp.getUserName());
                        }
                        callBack.onSuccess();
                        //getMyRef().removeEventListener(this); // If I dont remove the listener, it will be call again after database changes.//just use singlevaluelistener.
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("ww", "f");
                    }
                });

                return;
            }

            Log.d("ww", "getnextpage2");
            // get the last element of the previous data and clear the list.
            currentUserNamePointer = userlist.get(pageLength - 1).getUserName();
            userlist.clear();
            Query qName = mdb.orderByKey().startAfter(currentUserNamePointer).limitToFirst(10);
            qName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot t : snapshot.getChildren()) {
                        //Log.d("ww", t.toString());
                        E temp = t.getValue(c); // getValue here cant not use generic, ****. Solution at https://stackoverflow.com/questions/48237786/best-practice-for-using-generics-with-firebase-snapshot-getvalue
                        userlist.add(temp);
                        Log.d("ww", temp.getUserName());

                    }

                    if (userlist.size() == 0) {
                        callBack.onFail("on the last page");
                    } else {
                        Log.d("ww", String.valueOf(userlist.size()));
                        callBack.onSuccess();
                    }
                    //getMyRef().removeEventListener(this); // If I dont remove the listener, it will be call again after database changes.//just use singlevaluelistener.
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ww", "f");
                }
            });

        }

        @Override
        public void getPrevPage(ListenerCallBack callBack) {
            //get the first element of the psaklsgjslkdajgdm data and clear the list.
            currentUserNamePointer = userlist.get(0).getUserName();
            userlist.clear();

            Log.d("ww", "getpreviouspage");
            Query qName = mdb.orderByKey().endBefore(currentUserNamePointer).limitToLast(10);
            qName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot t : snapshot.getChildren()) {
                        //Log.d("ww", t.toString());
                        E temp = t.getValue(c); // getValue here cant not use generic, ****. Solution at https://stackoverflow.com/questions/48237786/best-practice-for-using-generics-with-firebase-snapshot-getvalue
                        userlist.add(temp);
                        Log.d("ww", temp.getUserName());

                    }

                    if (userlist.size() == 0) {
                        callBack.onFail("on the first page");
                    } else {
                        Log.d("ww", String.valueOf(userlist.size()));
                        callBack.onSuccess();
                    }
                    //getMyRef().removeEventListener(this); // If I dont remove the listener, it will be call again after database changes.//just use singlevaluelistener.
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ww", "f");
                }
            });

        }

        @Override
        public ArrayList<E> getList() {
            return userlist;
        }

        @Override
        public void delete(String userName) {
            mdb.child(userName).removeValue();
            //also delete the array list.
        }


    }



    // maybe unsuitable using the word iterator, but it works like a iterator.
    public AccList<Employee> getEmployeeAccManager() {
        return new AccList<Employee>("Employee", Employee.class);
    }

    public AccList<Customer> getCustomerAccManager() {
        return new AccList<Customer>("Customer", Customer.class);
    }

    //I will implement that inside the acclist, easier to refresh.
//    public void deleteEmployeeAcc(Employee e,ListenerCallBack callBack){
//        // assume acc exist(selected from the list), else will need more coding work.
//        FirebaseDatabase db = FirebaseDatabase.getInstance().getReference().child("")
//    }
//
//    public void deleteCustomerAcc(Customer c,ListenerCallBack callBack){
//
//    }


}