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

public class CustomerServiceRequest extends AppCompatActivity {
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
        txtInfoService.setText("Requested services");

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

                if(sR.isPending())
                    showUpdateDeleteDialog(s, userName, sR.getParentId(), sR);
                else
                    showRateDialog(s, userName, sR.getParentId(), sR);
                return false;
            }
        });

        refreshListView();

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

    private void refreshListView(){
        branchList.clear();
        serviceRequest.clear();
        CustomerRequestItem p = new CustomerRequestItem(CustomerServiceRequest.this, serviceRequest);
        lstPendingReq.setAdapter(p);

        dbBranch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchList.clear();
                serviceRequest.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch b = postSnapshot.getValue(Branch.class);
                    branchList.add(b);
                }

                for (Branch b : branchList) {
                    dbBranch.child(b.getName()).child("requests").child("submittedForms").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                ServiceRequest sR = postSnapshot.getValue(ServiceRequest.class);

                                if (sR != null && sR.getCustomerName().equals(userName)) {
                                    sR.setCustomerName(userName);
                                    sR.setKey(postSnapshot.getKey());
                                    sR.setParentId(b.getName());
                                    sR.setService(b.getService());
                                    if (!serviceRequest.contains(sR))
                                        serviceRequest.add(sR);
                                }

                            }
                            CustomerRequestItem p = new CustomerRequestItem(CustomerServiceRequest.this, serviceRequest);
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
        final Button btnDelete= dialogView.findViewById(R.id.btnDelete);
        final Button buttonCancel = dialogView.findViewById(R.id.cancelBtn);
        btnAccept.setVisibility(View.GONE);
        btnReject.setVisibility(View.GONE);
        btnDelete.setVisibility(View.VISIBLE);
        final androidx.appcompat.app.AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCancel.setOnClickListener(view -> b.dismiss());

        btnDelete.setOnClickListener(view -> {
            dbBranch.child(sR.getParentId()).child("requests")
                    .child("submittedForms").child(sR.getKey()).removeValue();
            b.dismiss();
        });
    }

    private void showRateDialog(final Service s, final String employeeId, final String branchId, ServiceRequest sR){
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_rate_form_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button cancelButton = dialogView.findViewById(R.id.button_rate_cancel);
        final Button rate1 = dialogView.findViewById(R.id.button_rate_1);
        final Button rate2 = dialogView.findViewById(R.id.button_rate_2);
        final Button rate3 = dialogView.findViewById(R.id.button_rate_3);
        final Button rate4 = dialogView.findViewById(R.id.button_rate_4);
        final Button rate5 = dialogView.findViewById(R.id.button_rate_5);
        final Button sm = dialogView.findViewById(R.id.button_rate_submit);
        final TextView txtComment = dialogView.findViewById(R.id.editComment);
        final int[] rate = {0};

        TextView t =dialogView.findViewById(R.id.rated);
        t.setText("default rate: 3");

        final androidx.appcompat.app.AlertDialog b = dialogBuilder.create();
        b.show();

        cancelButton.setOnClickListener(view -> b.dismiss());

        rate1.setOnClickListener(v -> {
            rate[0] = 1;
            t.setText("You rated: 1");
        });

        rate2.setOnClickListener(v -> {
            rate[0] = 2;
            t.setText("You rated: 2");
        });

        rate3.setOnClickListener(v -> {
            rate[0] = 3;
            t.setText("You rated: 3");
        });

        rate4.setOnClickListener(v -> {
            rate[0] = 4;
            t.setText("You rated: 4");
        });

        rate5.setOnClickListener(v -> {
            rate[0] = 5;
            t.setText("You rated: 5");
        });

        sm.setOnClickListener(v -> {
            for(Branch b1 : branchList){
                if (rate[0] == 0){
                    Toast.makeText(getApplicationContext(), "Please select rate ", Toast.LENGTH_SHORT).show();
                } else {
                    if (b1.getName().equals(branchId)) {
                        b1.giveRate(rate[0], sR);
                        if (!txtComment.getText().toString().isEmpty())
                            b1.addComment(userName + " said: " + txtComment.getText().toString());
                    }

                }
            }
            b.dismiss();
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
