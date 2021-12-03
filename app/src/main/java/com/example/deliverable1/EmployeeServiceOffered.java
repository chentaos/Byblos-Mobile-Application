package com.example.deliverable1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import branch.Branch;
import service.Service;
import service.ServiceForm;
import service.ServiceRequest;

public class EmployeeServiceOffered extends AppCompatActivity {
    String userName;
    DatabaseReference dbBranch = FirebaseDatabase.getInstance().getReference().child("Branch");
    List<Branch> branchList;
    List<ServiceRequest> serviceRequest;
    ListView lstPendingReq;
    List<String> services;
    List<Service> servicesList;

    // TextView
    TextView editFirstName;
    TextView editLastName;
    TextView editDateOfBirth;
    TextView editAddressForm;
    TextView editEmail;
    TextView txtLicense;
    TextView txtCar;
    TextView txtPickUpDate;
    TextView txtReturnDate;
    TextView editNbKm;
    TextView editTruckArea;
    TextView editStartLocation;
    TextView editEndLocation;
    TextView editNbBox;
    DatabaseReference dbServices = FirebaseDatabase.getInstance().getReference().child("Services");
    TextView txtInfoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_request);
        txtInfoService = findViewById(R.id.txtInfoService);
        txtInfoService.setText("Offered services");
        lstPendingReq =  findViewById(R.id.lstPendingReq);
        branchList = new ArrayList<>();
        serviceRequest = new ArrayList<>();
        servicesList = new ArrayList<>();
        services = new ArrayList<>();
        userName = getIntent().getStringExtra("userName");
        Toast.makeText(getApplicationContext(), "Long press on request to see details ", Toast.LENGTH_SHORT).show();

        lstPendingReq.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceRequest sR = serviceRequest.get(position);
                Service s = servicesList.get(services.indexOf(sR.getService()));
                showUpdateDeleteDialog(s, userName, sR.getParentId(), sR);
                return false;
            }
        });

        refrechListView();

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void refrechListView(){
        dbBranch.orderByChild("employee").equalTo(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchList.clear();
                serviceRequest.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch b = postSnapshot.getValue(Branch.class);
                    branchList.add(b);
                }

                for (Branch b : branchList) {
                    dbBranch.child(b.getName()).child("requests").child("submittedForms").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                ServiceRequest sR = postSnapshot.getValue(ServiceRequest.class);
                                if (!sR.isPending() && sR.isAccepted()){
                                    sR.setCustomerName(userName);
                                    sR.setKey(postSnapshot.getKey());
                                    sR.setParentId(b.getName());
                                    sR.setService(b.getService());
                                    serviceRequest.add(sR);
                                }
                            }
                            ServiceRequestItem p = new ServiceRequestItem(EmployeeServiceOffered.this, serviceRequest);
                            lstPendingReq.setAdapter(p);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    branchList.add(b);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUpdateDeleteDialog(final Service s, final String employeeId, final String branchId, ServiceRequest sR){
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_form_info_dialog, null);
        dialogBuilder.setView(dialogView);


        setFormEdit(dialogView, s);
        handleTextView(dialogView, s);
        setText(sR.getServiceForm());

        final Button btnAccept = dialogView.findViewById(R.id.acceptBtn);
        final Button btnReject = dialogView.findViewById(R.id.rejectBtn);
        final Button buttonCancel = dialogView.findViewById(R.id.cancelBtn);

        final androidx.appcompat.app.AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCancel.setOnClickListener(view -> b.dismiss());
        btnAccept.setVisibility(View.GONE);

        btnReject.setOnClickListener(view -> {
            String key = sR.getKey();
            String parentId = sR.getParentId();
            sR.setAccepted(true);
            sR.setPending(false);
            sR.setKey(null);
            sR.setParentId(null);
            sR.setService(null);
            dbBranch.child(parentId).child("requests")
                    .child("submittedForms").child(key).setValue(sR);
        });
    }

    private void setFormEdit(View dialogView, Service s) {
        editFirstName = dialogView.findViewById(R.id.editFirstName);
        editLastName = dialogView.findViewById(R.id.editLastName);
        editDateOfBirth = dialogView.findViewById(R.id.editDateOfBirth);

        editAddressForm = dialogView.findViewById(R.id.editAddress);
        editEmail = dialogView.findViewById(R.id.editEmail);
        txtLicense = dialogView.findViewById(R.id.txtLicense);

        txtCar = dialogView.findViewById(R.id.txtCar);
        txtPickUpDate = dialogView.findViewById(R.id.txtPickUpDate);
        txtReturnDate = dialogView.findViewById(R.id.txtReturnDate);

        editNbKm = dialogView.findViewById(R.id.editNbKm);
        editTruckArea = dialogView.findViewById(R.id.editTruckArea);
        editStartLocation = dialogView.findViewById(R.id.editStartLocation);
        editEndLocation = dialogView.findViewById(R.id.editEndLocation);
        editNbBox = dialogView.findViewById(R.id.editNbBox);

        editFirstName.setVisibility(s.getCustomerName()?View.VISIBLE:View.GONE);
        editLastName.setVisibility(s.getCustomerName()?View.VISIBLE:View.GONE);
        editDateOfBirth.setVisibility(s.getDob()?View.VISIBLE:View.GONE);
        editAddressForm.setVisibility(s.getAddress()?View.VISIBLE:View.GONE);
        editEmail.setVisibility(s.getEmail()?View.VISIBLE:View.GONE);
        txtLicense.setVisibility(s.getLicensetype()?View.VISIBLE:View.GONE);
        txtCar.setVisibility(s.getPreferredCar()?View.VISIBLE:View.GONE);
        txtPickUpDate.setVisibility(s.getDnT()?View.VISIBLE:View.GONE);
        txtReturnDate.setVisibility(s.getDnT()?View.VISIBLE:View.GONE);
        editNbKm.setVisibility(s.getMaxKl()?View.VISIBLE:View.GONE);
        editTruckArea.setVisibility(s.getArea()?View.VISIBLE:View.GONE);
        editStartLocation.setVisibility(s.getMoving()?View.VISIBLE:View.GONE);
        editEndLocation.setVisibility(s.getMoving()?View.VISIBLE:View.GONE);
        editNbBox.setVisibility(s.getBox()?View.VISIBLE:View.GONE);
    }

    private void setText(ServiceForm serviceForm){
        editFirstName.setText(serviceForm.getFirstName());
        editLastName.setText(serviceForm.getLastName());
        editDateOfBirth.setText(serviceForm.getDateOfBirth());
        editAddressForm.setText(serviceForm.getAddress());
        editEmail.setText(serviceForm.getEmail());
        txtLicense.setText(serviceForm.getLicense());
        txtCar.setText(serviceForm.getCar());
        txtPickUpDate.setText(serviceForm.getPickUpDate());
        txtReturnDate.setText(serviceForm.getReturnDate());
        editTruckArea.setText(serviceForm.getTruckArea());
        editNbKm.setText(String.valueOf(serviceForm.getNbKm()));
        editStartLocation.setText(serviceForm.getStartLocation());
        editEndLocation.setText(serviceForm.getEndLocation());
        editNbBox.setText(String.valueOf(serviceForm.getNbBox()));
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

}
