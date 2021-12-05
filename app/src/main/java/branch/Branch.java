package branch;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

import rate.Rate;
import service.Service;
import service.ServiceForm;
import service.ServiceRequest;

public class Branch {
    private DatabaseReference myRef;
    private String employee;
    private String service;
    private String name;
    private Rate rate;

    public List<ServiceForm> serviceForms;

    public Branch() {
    }

    public Branch(String employee, String service, String name, Rate rate) {
        myRef = FirebaseDatabase.getInstance().getReference().child("Branch").child(name);
        this.employee = employee;
        this.service = service;
        this.name = name;
        this.rate = rate;
    }

    //public Branch(String employee, String service, String name, Date startHour, Date endHour){
//    public Branch(String employee, String service, String name, int nbRate, int rate, int sumRate) {
//        myRef = FirebaseDatabase.getInstance().getReference().child("Branch").child(name);
//        this.employee = employee;
//        this.service = service;
//        this.name = name;
//        this.nbRate = nbRate;
//        this.rate = rate;
//        this.sumRate = sumRate;
//    }

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

    public void removeCustomer(String username) {
        if (myRef == null)
            myRef = FirebaseDatabase.getInstance().getReference().child("Branch").child(name);

        myRef.child("requests")
                .child("submittedForms").orderByChild("customerName")
                .equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void giveRate(int newRate, ServiceRequest sR){
        if (myRef == null)
            myRef = FirebaseDatabase.getInstance().getReference().child("Branch").child(name);

        if (rate == null)
            rate = new Rate(0,0,0);

        if (!sR.isRated()){
            rate.setSumRate(newRate);
            sR.setCurrentRate(newRate);
            sR.setRated(true);
            rate.setNbRate();
        } else {
            rate.setSumRate(sR.getCurrentRate() * - 1);
            sR.setCurrentRate(newRate);
            rate.setSumRate(newRate);
        }
        rate.setRate();

//        newRate = sumRate / nbRate;
//        myRef.child("rate").setValue(rate);
//        myRef.child("sumRate").setValue(sumRate);
//        myRef.child("nbRate").setValue(newRate);

        myRef.child("rate").setValue(rate);

        myRef.child("requests").child("submittedForms").child(sR.getKey())
                .setValue(new ServiceRequest(sR.getServiceForm(), sR.isPending(),
                        sR.isAccepted(), sR.getCustomerName(), sR.isRated(), sR.getCurrentRate()));
    }

    public void addComment(String comment){
        if (myRef == null)
            myRef = FirebaseDatabase.getInstance().getReference().child("Branch").child(name);

        String id = myRef.push().getKey();

        myRef.child("comments").child(id).setValue(comment);
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

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    //    public int getRate() {
//        return rate;
//    }

//    public int getNbRate() {
//        return nbRate;
//    }
//
//    public void setNbRate(int nbRate) {
//        this.nbRate = nbRate;
//    }
//
//    public void setRate(int rate) {
//        this.rate = rate;
//    }
//
//    public int getSumRate() {
//        return sumRate;
//    }
//
//    public void setSumRate(int sumRate) {
//        this.sumRate = sumRate;
//    }

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
                "myRef=" + myRef +
                ", employee='" + employee + '\'' +
                ", service='" + service + '\'' +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}
