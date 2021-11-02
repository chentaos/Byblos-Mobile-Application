package service;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import account.ListenerCallBack;

public class Service {
    private DatabaseReference myRef;
    private double hourlyRate=0;
    private String name;
    private boolean customerName,DOB,address,email,licensetype,preferredCar,DnT,maxKl,area,moving,mover,box;

    public Service(){}

//    public Service(String name,double rate, boolean customerName, boolean DOB, boolean address, boolean email,
//                   boolean licensetype, boolean preferredCar, boolean DnT,
//                   boolean maxKl, boolean area, boolean moving, boolean mover, boolean box){
//        myRef = FirebaseDatabase.getInstance().getReference().child("Services");
//        this.customerName=customerName;
//        this.DOB=DOB;
//        this.address=address;
//        this.email=email;
//        this.licensetype=licensetype;
//        this.preferredCar=preferredCar;
//        this.DnT=DnT;
//        this.maxKl=maxKl;
//        this.area=area;
//        this.moving=moving;
//        this.mover=mover;
//        this.box=box;
//        this.name = name;
//        this.hourlyRate=rate;
//     }

    public Service(String name,double rate, boolean[] req){
        myRef = FirebaseDatabase.getInstance().getReference().child("Services");
        this.customerName=req[0];
        this.DOB=req[1];
        this.address=req[2];
        this.email=req[3];
        this.licensetype=req[4];
        this.preferredCar=req[5];
        this.DnT=req[6];
        this.maxKl=req[7];
        this.area=req[8];
        this.moving=req[9];
        this.mover=req[10];
        this.box=req[11];
        this.name = name;
        this.hourlyRate=rate;
    }

    public void updateFromDB(ListenerCallBack callBack) {
//        callBack.onSuccess();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("Login", "see " + userName + snapshot);

//                if (!snapshot.hasChild(name)) {
//                    myRef.child(name).setValue(this);
//                }
                snapshot = snapshot.child(name);
                Number num = (Number) snapshot.child("rate").getValue();
                if (num != null) {
                    hourlyRate = num.doubleValue();
                }
                callBack.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Service update from db", "fail");
            }
        });
    }

    public void writeToDB() {
        myRef.child(name).setValue(this);
    }

    public void setRate(double rate){ hourlyRate=rate;}
    public void setName(String name){this.name=name;}

    public double getRate() {
        return hourlyRate;
    }
    public String getName(){
        return name;
    }
    public boolean getCustomerName(){return  customerName;}
    public boolean getDOB(){return   DOB;}
    public boolean getAddress(){return  address;}
    public boolean getEmail(){return  email;}
    public boolean getLicensetype(){return  licensetype;}
    public boolean getPreferredCar(){return  preferredCar;}
    public boolean getDnT(){return  DnT;}
    public boolean getMaxKl(){return  maxKl;}
    public boolean getArea(){return  area;}
    public boolean getMoving(){return  moving;}
    public boolean getMover(){return  mover;}
    public boolean getBox(){return  box;}





    @Override
    public String toString() {
        return "Service{" +
                "hourlyRate=" + hourlyRate +
                ", name='" + name + '\'' +
                ", myRef=" + myRef +
                ", customerName=" + customerName +
                ", DOB=" + DOB +
                ", address=" + address +
                ", email=" + email +
                ", licensetype=" + licensetype +
                ", preferredCar=" + preferredCar +
                ", DnT=" + DnT +
                ", maxKl=" + maxKl +
                ", area=" + area +
                ", moving=" + moving +
                ", mover=" + mover +
                ", box=" + box +
                '}';
    }
}
