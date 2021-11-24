package account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Employee extends User {

    private String address;
    private String phoneNumber;
    //private DatabaseReference myEmRef = getMyRef().child("Users/Employee");
    /**
     *  Constructor for Employee user account.
     */

    // format 2320 = "23:20
    private ArrayList<String> workingTime = new ArrayList<>();
    private Deque<TimeInterval> dayTime = new LinkedList<>();

    private static class TimeInterval{
        public int start;
        public int end;
        public TimeInterval(int s, int e){
            start = s;
            end = e;
        }

        public String toString(){ //dumb code need optimize

            String startT = "";
            String endT = "";
//            if(start/100<10){
//                startT += "0";
//                if(start/100 == 0){
//                    startT+="0";
//                }
//            }
//            if(end/100<10){
//                endT += "0";
//                if(end/100 == 0){
//                    endT += "0";
//                }
//            }
//            startT += String.valueOf(start);T
//            endT += String.valueOf(end);
            for(int i=3;i>=0;i--){
                endT += String.valueOf(0 + (int)(end/Math.pow(10,i)));
                end = end % (int)Math.pow(10,i);
                startT += String.valueOf(0 + (int)(start/Math.pow(10,i)));
                start = start % (int)Math.pow(10,i);
            }
            //Log.d("ww",startT+endT);

            return startT+endT;
        }
    }

    //update to firebase
    private void timeUpdate(){
        getMyRef().child(getUserName()).child("workTime").setValue(workingTime);
    }

    //Initiate;retrieve value from firebase
    public void timeInitiate(ListenerCallBack callBack){
        //add default value.
        for(int i=0; i<7; i++){
            workingTime.add("0");
        }

        getMyRef().child(getUserName()).addValueEventListener(new ValueEventListener() { //g
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild("workTime")){
                    callBack.onFail("workTime no set");
                    return;
                }
                snapshot = snapshot.child("workTime");
                int i=0;
                for(DataSnapshot day: snapshot.getChildren()){
                    //if found, replace the default value.
                    workingTime.set(i++, day.getValue(String.class));
                    //Log.d("ww","go:"+day.getValue(String.class));
                }
                callBack.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Format:"0213031414111500"->[0213-0314],[1411-1500]
    private void toQueue(int day){
        dayTime.clear();

        if(workingTime.get(day-1).equals("0")){
            return;
        }

        String timeIntervals = workingTime.get(day-1);
        for(int i = 0; i<timeIntervals.length();i+=8){
            String ts = timeIntervals.substring(i,i+4);
            String te = timeIntervals.substring(i+4,i+8);
            Integer s = Integer.parseInt(ts);
            Integer e = Integer.parseInt(te);
            TimeInterval t = new TimeInterval(s, e);

            dayTime.addLast(t);
        }
    }

    //reverse
    private void toTimeString(int day){
        String workInterval_day = "";
        if(dayTime.isEmpty()){
            workingTime.set(day-1, "0");
            return;
        }
        for(TimeInterval t: (LinkedList<TimeInterval>)dayTime){
            workInterval_day += t.toString();
        }
        workingTime.set(day-1, workInterval_day);
    }

    //get one day working time.
    public LinkedList<TimeInterval> getDayTimeIntervals(int day){
        if(workingTime.get(day-1).equals("0")){
            return null;
        }
        toQueue(day);
        return (LinkedList<TimeInterval>) dayTime;
    }


    // add a time interval for one day.
    public void addTime(int day, int timeStart, int timeEnd){



        TimeInterval t = new TimeInterval(timeStart, timeEnd);
        if(workingTime.get(day-1).equals("0")){
            workingTime.set(day-1, t.toString());
            timeUpdate();
            return;
        }

        Deque<TimeInterval> temp = new LinkedList<>();
        toQueue(day);

        boolean added = false;
        while(!dayTime.isEmpty()){
            TimeInterval dayt = dayTime.removeFirst();
            // possible: contain, overlap, independent
            if(timeStart <= dayt.start){
                //contain
                if(timeEnd >= dayt.end){
                    continue;
                }
                //overlap
                else if(timeEnd >= dayt.start && timeEnd < dayt.end){
                    t.end = dayt.end;
                    temp.addLast(t);
                    added = true;
                    continue;
                }
                // independent
                if(added == false){
                    temp.addLast(t);
                    added = true;
                }
                temp.addLast(dayt);
            }
            //possible: overlap, be contained
            else{ // timeStart > dayt.start

                //be contained / overlap
                if(timeStart <= dayt.end){
                    t.start = Math.min(t.start, dayt.start);
                    t.end = Math.max(t.end, dayt.end);
                }
                // no contact
                else{
                    temp.add(dayt);
                }
            }
        }
        if(!added){
            temp.addLast(t);
        }
        dayTime = temp;
        toTimeString(day);
        timeUpdate();
    }

    public void deleteTime(int day, int index){
        toQueue(day);
        ((LinkedList)dayTime).remove(index);
        toTimeString(day);
        timeUpdate();
    }

    public Employee(){

    }

    public Employee(String userName, String password) {
        super(userName, password);
        setMyRef(getMyRef().child("Users/Employee"));
    }

    public Employee(String name,String password, String address, String phoneNumber){
        super(name,password);
        this.address = address;
        this.phoneNumber = phoneNumber;
        setMyRef(getMyRef().child("Users/Employee"));
    }


    @Override
    public String welcomeMSG() {
        return super.welcomeMSG()+"Employee." ;
    }

    public void setProfile(String address, String phoneNumber){
            this.phoneNumber = phoneNumber;
            this.address = address;
            getMyRef().child(getUserName()).child("PhoneNumber").setValue(phoneNumber);
            getMyRef().child(getUserName()).child("address").setValue(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public DatabaseReference getMyEmRef() {
//        return myEmRef;
//    }
}

