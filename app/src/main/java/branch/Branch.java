package branch;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

import service.Service;

public class Branch {
    private DatabaseReference myRef;
    private String employee;
    private String service;
//    private Date startHour;
//    private Date endHour;
    private String name;

    public Branch(){}

    //public Branch(String employee, String service, String name, Date startHour, Date endHour){
    public Branch(String employee, String service, String name, Date startHour, Date endHour){
        myRef = FirebaseDatabase.getInstance().getReference().child("Branch");
        this.employee = employee;
        this.service = service;
        this.name = name;
//        this.startHour = startHour;
//        this.endHour = endHour;
    }

    public Boolean[] writeToDB() {
        boolean addedValue = false;
        final Boolean[] addedElement = {false};
        myRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(name)) {
                    myRef.child(name).setValue(this);
                    addedElement[0] = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return addedElement;
    }

    public String getName() {
        return name;
    }

//    public Date getEndHour() {
//        return endHour;
//    }
//
//    public Date getStartHour() {
//        return startHour;
//    }

    public String getService() {
        return service;
    }

    public String getEmployee() {
        return employee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

//    public void setEndHour(Date endHour) {
//        this.endHour = endHour;
//    }
//
//    public void setStartHour(Date startHour) {
//        this.startHour = startHour;
//    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Branch{" +
                ", name='" + name + '\'' +
                ", myRef=" + myRef +
                ", employee=" + employee +
                ", service=" + service +
//                ", endHour=" + endHour +
//                ", startHour=" + startHour +
                '}';
    }
}
