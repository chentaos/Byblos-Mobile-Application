package account;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import branch.Branch;

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

        private ArrayList<User> userlist;
        private DatabaseReference mdb;
        private DatabaseReference mdbBranch;
        private String currentUserNamePointer;

        private final Class<E> c;

        public AccList(String category, Class<E> c) {
            this.c = c;
            userlist = new ArrayList<User>();
            mdb = FirebaseDatabase.getInstance().getReference().child("Users/" + category);
            currentUserNamePointer = null;
        }

        @Override
        public void getNextpage(ListenerCallBack callBack) {

            if (userlist.size() == 0 && currentUserNamePointer == null) {

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
                        if (userlist.size() == 0) {
                            callBack.onFail("no user data");
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("ww", "f");
                    }
                });

                return;
            }

            currentUserNamePointer = userlist.get(userlist.size() - 1).getUserName();
            // get the last element of the previous data and clear the list.
            Query qName = mdb.orderByKey().startAfter(currentUserNamePointer).limitToFirst(pageLength);
            qName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.hasChildren()) {
                        userlist.clear();
                    } else {
                        callBack.onFail("on the last page");
                        return;
                    }

                    for (DataSnapshot t : snapshot.getChildren()) {
                        //Log.d("ww", t.toString());
                        E temp = t.getValue(c); // getValue here cant not use generic, ****. Solution at https://stackoverflow.com/questions/48237786/best-practice-for-using-generics-with-firebase-snapshot-getvalue
                        userlist.add(temp);
                        Log.d("ww", temp.getUserName());

                    }
                    callBack.onSuccess();
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
            if (userlist.size() == 0 && currentUserNamePointer == null) {
                callBack.onFail("First page");
                return;
            }
            currentUserNamePointer = userlist.get(0).getUserName();

            Query qName = mdb.orderByKey().endBefore(currentUserNamePointer).limitToLast(pageLength);
            qName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.hasChildren()) {
                        userlist.clear();
                    } else {
                        callBack.onFail("already the first page.");
                        return;
                    }

                    for (DataSnapshot t : snapshot.getChildren()) {
                        //Log.d("ww", t.toString());
                        E temp = t.getValue(c); // getValue here cant not use generic, ****. Solution at https://stackoverflow.com/questions/48237786/best-practice-for-using-generics-with-firebase-snapshot-getvalue
                        userlist.add(temp);
                        Log.d("ww", temp.getUserName());

                    }
                    callBack.onSuccess();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("ww", "f");
                }
            });

        }

        @Override
        public ArrayList<User> getList() {
            return userlist;
        }

        @Override
        public void delete(User user) {
            mdbBranch = FirebaseDatabase.getInstance().getReference().child("Branch");

            if (user instanceof Employee) {
                mdbBranch.orderByChild("employee").equalTo(user.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            postSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            if (user instanceof Customer) {
                mdbBranch.child("requests")
                        .child("submittedForms").orderByChild("customerName")
                        .equalTo(user.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Branch b = postSnapshot.getValue(Branch.class);

                            mdbBranch.child(b.getName()).child("requests")
                                    .child("submittedForms").orderByChild("customerName")
                                    .equalTo(user.getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        mdbBranch.child(b.getName()).child("requests")
                                                .child("submittedForms").child(postSnapshot.getKey()).removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            mdb.child(user.getUserName()).removeValue();
            userlist.remove(user);

            if (userlist.size() == 0) {
                currentUserNamePointer = null;
            }
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

}