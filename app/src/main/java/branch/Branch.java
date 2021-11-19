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
    private List<Service> services;
    private Date startHour;
    private Date endHour;
    private String name;

    public Branch(String employee, List<Service> services, Date startHour, Date endHour){
        myRef = FirebaseDatabase.getInstance().getReference().child("Branches");
        this.employee = employee;
        this.services = services;
        this.startHour = startHour;
        this.endHour = endHour;
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

    public Date getEndHour() {
        return endHour;
    }

    public Date getStartHour() {
        return startHour;
    }

    public List<Service> getServices() {
        return services;
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

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Branches{" +
                ", name='" + name + '\'' +
                ", myRef=" + myRef +
                ", employee=" + employee +
                ", services=" + services +
                ", endHour=" + endHour +
                ", startHour=" + startHour +
                '}';
    }
}
