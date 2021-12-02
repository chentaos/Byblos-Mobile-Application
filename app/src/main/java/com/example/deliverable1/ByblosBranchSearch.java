package com.example.deliverable1;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import account.Employee;
import account.ListenerCallBack;
import branch.Branch;
import service.Service;
import service.ServiceForm;
import service.ServiceRequest;

public class ByblosBranchSearch extends AppCompatActivity {
    EditText editAddress;
//    EditText editAddress;
    int startTime;
    int endTime;
    String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Spinner spinDays;
    EditText editStartTime;
    EditText editEndTime;
    DatabaseReference dbServices = FirebaseDatabase.getInstance().getReference().child("Services");
    DatabaseReference dbBranch = FirebaseDatabase.getInstance().getReference().child("Branch");
    DatabaseReference dbEmployee = FirebaseDatabase.getInstance().getReference().child("Users").child("Employee");

    List<String> services;
    List<Service> servicesList;
    List<Branch> Branches;
    List<Employee> Employees;
    List<String> EmployeesUsername;
    Spinner spinServices;
    private static final DecimalFormat df = new DecimalFormat("00");
    ListView branchesList;

    String userName = "";

    EditText editFirstName;
    EditText editLastName;
    EditText editDateOfBirth;

    EditText editAddressForm;
    EditText editEmail;
    Spinner spinLicenses;

    Spinner spinCars;
    Spinner spinPickUpDate;
    Spinner spinReturnDate;

    EditText editNbKm;
    EditText editTruckArea;
    EditText editStartLocation;
    EditText editEndLocation;
    EditText editNbBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byblos_branch_search);
        startTime = -1;
        endTime = -1;
        spinDays = findViewById(R.id.spinDays);
        spinServices = findViewById(R.id.spinServices);
        editStartTime = findViewById(R.id.editStartTime);
        editEndTime = findViewById(R.id.editEndTime);
        editAddress = findViewById(R.id.inputAddress);

        servicesList = new ArrayList<>();
        services = new ArrayList<>();
        Branches = new ArrayList<>();
        Employees = new ArrayList<>();
        EmployeesUsername = new ArrayList<>();
        branchesList = findViewById(R.id.branchesList);

        ArrayAdapter arrayAdapterDays = new ArrayAdapter(this,android.R.layout.simple_spinner_item,days);
        arrayAdapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDays.setAdapter(arrayAdapterDays);
        spinDays.setSelection(0);

        editStartTime.setEnabled(false);
        editStartTime.setTextColor(Color.BLACK);
        editEndTime.setEnabled(false);
        editEndTime.setTextColor(Color.BLACK);

        dbServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                services.clear();
                servicesList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Service s= postSnapshot.getValue(Service.class);
                    services.add(s.getName());
                    servicesList.add(s);
                }
//                services.add("None");
                setSpinnerService();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbEmployee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Employee e = postSnapshot.getValue(Employee.class);
                    if (e != null) {
                        e.setMyRef(dbEmployee);
                        e.timeInitiate(new ListenerCallBack() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFail(String errInfo) {

                            }
                        });
                        EmployeesUsername.add(e.getUserName());
                        Employees.add(e);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        branchesList.setOnItemLongClickListener((parent, view, position, id) -> {
            Branch branch = Branches.get(position);
            showUpdateDeleteDialog(branch.getService(), branch.getEmployee(), branch.getName());
            return false;
        });

        loadBranches();
        userName = getIntent().getStringExtra("userName");
    }

    private void showUpdateDeleteDialog(final String serviceId, final String employeeId, final String branchId){
        Toast.makeText(getApplicationContext(), "If you select an existing request it will be erased ", Toast.LENGTH_SHORT).show();

        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_required_info_dialog, null);
        dialogBuilder.setView(dialogView);


        int serviceIndex = services.indexOf(serviceId);

        Service s = servicesList.get(serviceIndex);
        handleTextView(dialogView, s);
        setFormEdit(dialogView, s);

        setSpinners(s);

        int employeeIndex = EmployeesUsername.indexOf(employeeId);

        Employee e = Employees.get(employeeIndex);

        if (s.getDnT()) defineDates(e);

        final Button btnProfileSubmitBtn = dialogView.findViewById(R.id.profileSbmitBtn);
        final Button buttonCancel = dialogView.findViewById(R.id.profileCancelBtn);

        final androidx.appcompat.app.AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCancel.setOnClickListener(view -> b.dismiss());

        btnProfileSubmitBtn.setOnClickListener(view -> {
            submitForm(branchId);
            b.dismiss();
        });

    }

    private void setSpinners(Service s) {
        ArrayAdapter arrayAdapter;
        String[] licenseType = { "G1", "G2", "G"};
        String[] carType = { "compact", "intermediate", "SUV"};
        if (s.getLicensetype()){
            arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,licenseType);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinLicenses.setAdapter(arrayAdapter);
            spinLicenses.setSelection(0);
        }

        if (s.getPreferredCar()){
            arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,carType);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCars.setAdapter(arrayAdapter);
            spinCars.setSelection(0);
        }
    }

    private void submitForm(String branchId) {
        int nbKm = 0;
        int nbBox = 0;
        String license = "";
        String car = "";
        String pickUpDate = "";
        String returnDate = "";

        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String dateOfBirth = editDateOfBirth.getText().toString();
        String address = editAddress.getText().toString();
        String email = editEmail.getText().toString();

        if (spinLicenses.getSelectedItem() != null)
            license = spinLicenses.getSelectedItem().toString();

        if (spinCars.getSelectedItem() != null)
            car = spinCars.getSelectedItem().toString();

        if (spinPickUpDate.getSelectedItem() != null)
            pickUpDate = spinPickUpDate.getSelectedItem().toString();

        if (spinReturnDate.getSelectedItem() != null)
            returnDate = spinReturnDate.getSelectedItem().toString();

        if (editNbKm.getText().toString().length() != 0)
            nbKm = Integer.parseInt(editNbKm.getText().toString());

        if (editNbBox.getText().toString().length() !=0){
            nbBox = Integer.parseInt(editNbBox.getText().toString());
        }

        String truckArea = editTruckArea.getText().toString();

        String startLocation = editStartLocation.getText().toString();
        String endLocation = editEndLocation.getText().toString();

        ServiceForm serviceForm = new ServiceForm(firstName, lastName, dateOfBirth,
                address,email, license, car, pickUpDate,
                returnDate, nbKm, truckArea, startLocation, endLocation, nbBox,false);

        String key = FirebaseDatabase.getInstance().getReference().push().getKey();

        ServiceRequest serviceRequest = new ServiceRequest(serviceForm, true, false, userName);

        dbBranch.child(branchId).child("requests").child("submittedForms").child(key).setValue(serviceRequest);
    }

    private void defineDates(Employee e) {
        ArrayAdapter arrayAdapter;
        List<String> possibleDates = new ArrayList<>();
        List<String> possibleReturnDates = new ArrayList<>();

        for (int i = 0; i < days.length; i++) {
            LinkedList<Employee.TimeInterval> dayTimeIntervals = e.getDayTimeIntervals(i + 1);
            if (dayTimeIntervals != null)
                for (Employee.TimeInterval timeInterval: dayTimeIntervals) {
                    if (timeInterval.start != 0   && timeInterval.end != 0){
                        StringBuilder startTime = new StringBuilder();
                        startTime.append(timeInterval.toString().substring(0, timeInterval.toString().length() / 2));
                        startTime.insert(startTime.length() / 2, ":");

                        StringBuilder returnTime = new StringBuilder();
                        returnTime.append(timeInterval.toString().substring(timeInterval.toString().length() / 2));
                        returnTime.insert(returnTime.length() / 2, ":");

                        possibleDates.add(days[i] + " between " + startTime.toString() + " - " + returnTime.toString());
                        possibleReturnDates.add("Next " + possibleDates.get(possibleDates.size() -1));
                    }
                }
        }

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,possibleDates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPickUpDate.setAdapter(arrayAdapter);
        spinPickUpDate.setSelection(0);

        arrayAdapter = new ArrayAdapter(ByblosBranchSearch.this, android.R.layout.simple_spinner_item,possibleReturnDates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinReturnDate.setAdapter(arrayAdapter);
        spinReturnDate.setSelection(0);
    }

    private void setFormEdit(View dialogView, Service s) {
        editFirstName = dialogView.findViewById(R.id.editFirstName);
        editLastName = dialogView.findViewById(R.id.editLastName);
        editDateOfBirth = dialogView.findViewById(R.id.editDateOfBirth);

        editAddressForm = dialogView.findViewById(R.id.editAddress);
        editEmail = dialogView.findViewById(R.id.editEmail);
        spinLicenses = dialogView.findViewById(R.id.spinLicenses);

        spinCars = dialogView.findViewById(R.id.spinCars);
        spinPickUpDate = dialogView.findViewById(R.id.spinPickUpDate);
        spinReturnDate = dialogView.findViewById(R.id.spinReturnDate);

        editNbKm = dialogView.findViewById(R.id.editNbKm);
        editTruckArea = dialogView.findViewById(R.id.editTruckArea);
        editStartLocation = dialogView.findViewById(R.id.editStartLocation);
        editEndLocation = dialogView.findViewById(R.id.editEndLocation);
        editNbBox = dialogView.findViewById(R.id.editNbBox);

        editFirstName.setVisibility(s.getCustomerName()?View.VISIBLE:View.GONE);
        editLastName.setVisibility(s.getCustomerName()?View.VISIBLE:View.GONE);
        editDateOfBirth.setVisibility(s.getDob()?View.VISIBLE:View.GONE);
        editAddress.setVisibility(s.getAddress()?View.VISIBLE:View.GONE);
        editEmail.setVisibility(s.getEmail()?View.VISIBLE:View.GONE);
        spinLicenses.setVisibility(s.getLicensetype()?View.VISIBLE:View.GONE);
        spinCars.setVisibility(s.getPreferredCar()?View.VISIBLE:View.GONE);
        spinPickUpDate.setVisibility(s.getDnT()?View.VISIBLE:View.GONE);
        spinReturnDate.setVisibility(s.getDnT()?View.VISIBLE:View.GONE);
        editNbKm.setVisibility(s.getMaxKl()?View.VISIBLE:View.GONE);
        editTruckArea.setVisibility(s.getArea()?View.VISIBLE:View.GONE);
        editStartLocation.setVisibility(s.getMoving()?View.VISIBLE:View.GONE);
        editEndLocation.setVisibility(s.getMoving()?View.VISIBLE:View.GONE);
        editNbBox.setVisibility(s.getBox()?View.VISIBLE:View.GONE);
    }

    private void handleTextView(final View dialogView, Service s){
        final TextView txtFirstName = dialogView.findViewById(R.id.txtFirstName);
        final TextView txtLastName = dialogView.findViewById(R.id.txtLastName);
        final TextView txtDOB = dialogView.findViewById(R.id.txtDOB);

        final TextView txtAddress = dialogView.findViewById(R.id.txtAddress);
        final TextView txtEmail = dialogView.findViewById(R.id.txtEmail);
        final TextView txtLicenseType = dialogView.findViewById(R.id.txtLicenseType);

        final TextView txtCarType = dialogView.findViewById(R.id.txtCarType);
        final TextView txtPoD = dialogView.findViewById(R.id.txtPoD);
        final TextView txtRPOD = dialogView.findViewById(R.id.txtRPOD);

        final TextView txtMaxKm = dialogView.findViewById(R.id.txtMaxKm);
        final TextView txtTruckArea = dialogView.findViewById(R.id.txtTruckArea);
        final TextView txtMovingStart = dialogView.findViewById(R.id.txtMovingStart);
        final TextView txtMovingEnd = dialogView.findViewById(R.id.txtMovingEnd);
        final TextView txtNbBox = dialogView.findViewById(R.id.txtNbBox);

        txtFirstName.setVisibility(s.getCustomerName()?View.VISIBLE:View.GONE);
        txtLastName.setVisibility(s.getCustomerName()?View.VISIBLE:View.GONE);
        txtDOB.setVisibility(s.getDob()?View.VISIBLE:View.GONE);
        txtAddress.setVisibility(s.getAddress()?View.VISIBLE:View.GONE);
        txtEmail.setVisibility(s.getEmail()?View.VISIBLE:View.GONE);
        txtLicenseType.setVisibility(s.getLicensetype()?View.VISIBLE:View.GONE);
        txtCarType.setVisibility(s.getPreferredCar()?View.VISIBLE:View.GONE);
        txtPoD.setVisibility(s.getDnT()?View.VISIBLE:View.GONE);
        txtRPOD.setVisibility(s.getDnT()?View.VISIBLE:View.GONE);
        txtMaxKm.setVisibility(s.getMaxKl()?View.VISIBLE:View.GONE);
        txtTruckArea.setVisibility(s.getArea()?View.VISIBLE:View.GONE);
        txtMovingStart.setVisibility(s.getMoving()?View.VISIBLE:View.GONE);
        txtMovingEnd.setVisibility(s.getMoving()?View.VISIBLE:View.GONE);
        txtNbBox.setVisibility(s.getBox()?View.VISIBLE:View.GONE);
    }

    public void setSpinnerService(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, services);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinServices.setAdapter(arrayAdapter);
        spinServices.setSelection(0);
    }

    public void startingTimeOnClick(View view){
        Toast.makeText(getApplicationContext(), "Please enter the starting time", Toast.LENGTH_SHORT).show();
        new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Log.d("ww",hourOfDay+"and "+ minute);
                startTime = hourOfDay*100+minute;
                editEndTime.setText("");
                endTime = 0;
                editStartTime.setText(new StringBuilder().append(df.format(hourOfDay)).append(":").append(df.format(minute)).toString());
            }
        },0,0,true).show();
    }

    public void endTimeOnClick(View view){
        Toast.makeText(getApplicationContext(), "Please enter the ending time", Toast.LENGTH_SHORT).show();
        new TimePickerDialog(ByblosBranchSearch.this, AlertDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Log.d("ww","timeShow: "+ startTime + (hourOfDay*100+minute));
                if(startTime>= (hourOfDay*100+minute)){
                    Toast.makeText(getApplicationContext(), "StartTime need to be earlier than endTime", Toast.LENGTH_SHORT).show();
                    return;
                }
                endTime = hourOfDay*100+minute;
                editEndTime.setText(df.format(hourOfDay) + ":" + df.format(minute));
//                e.addTime(day, startTime,hourOfDay*100+minute);
            }
        },0,0,true).show();
    }

    public void loadBranches(){
        dbBranch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Branches.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch b= postSnapshot.getValue(Branch.class);
                    Branches.add(b);
                }

                BranchSearchItem p = new BranchSearchItem(ByblosBranchSearch.this, Branches, services);
                branchesList.setAdapter(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void searchOnClick(View view){
        String serviceSelected = spinServices.getSelectedItem().toString();
        Branches.clear();
        BranchSearchItem p = new BranchSearchItem(ByblosBranchSearch.this, Branches, services);
        branchesList.setAdapter(p);

        dbBranch.orderByChild("service").equalTo(serviceSelected).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Branches.clear();

                String dayNb = String.valueOf(spinDays.getSelectedItemPosition());

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch b= postSnapshot.getValue(Branch.class);
                    int employeeIndex = EmployeesUsername.indexOf(b.getEmployee());

                    Employee e = Employees.get(employeeIndex);
                    int dayPos = spinDays.getSelectedItemPosition();
                    LinkedList<Employee.TimeInterval> dayTimeIntervals = e.getDayTimeIntervals(dayPos + 1);

                    for (Employee.TimeInterval timeInterval: dayTimeIntervals) {
                        if (startTime >= timeInterval.start   && endTime <= timeInterval.end){
                            Branches.add(b);
                        }
                    }
                    dayTimeIntervals.clear();
                    BranchSearchItem p = new BranchSearchItem(ByblosBranchSearch.this, Branches, services);
                    branchesList.setAdapter(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clearOnClick(View view){
        editStartTime.setText("");
        editEndTime.setText("");
        spinDays.setSelection(0);
        spinServices.setSelection(0);
        editAddress.setText("");
        startTime = 0;
        endTime = 0;
        loadBranches();
    }

}
