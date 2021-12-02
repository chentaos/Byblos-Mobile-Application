package service;

import android.widget.EditText;
import android.widget.Spinner;

import com.example.deliverable1.R;

public class ServiceForm {
    String firstName;
    String lastName;
    String dateOfBirth;
    String address;
    String email;
    String license;
    String car;
    String pickUpDate;
    String returnDate;
    int nbKm;
    String truckArea;
    String startLocation;
    String endLocation;
    int nbBox;
    boolean isAccepted;

    public ServiceForm(String firstName, String lastName, String dateOfBirth, String address,
                       String email, String license, String car, String pickUpDate,
                       String returnDate, int nbKm, String truckArea, String startLocation,
                       String endLocation, int nbBox, boolean isAccepted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.license = license;
        this.car = car;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.nbKm = nbKm;
        this.truckArea = truckArea;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.nbBox = nbBox;
        this.isAccepted = isAccepted;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNbKm() {
        return nbKm;
    }

    public void setNbKm(int nbKm) {
        this.nbKm = nbKm;
    }

    public String getTruckArea() {
        return truckArea;
    }

    public void setTruckArea(String truckArea) {
        this.truckArea = truckArea;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public int getNbBox() {
        return nbBox;
    }

    public void setNbBox(int nbBox) {
        this.nbBox = nbBox;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    @Override
    public String toString() {
        return "ServiceForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", license='" + license + '\'' +
                ", car='" + car + '\'' +
                ", pickUpDate='" + pickUpDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", nbKm=" + nbKm +
                ", truckArea='" + truckArea + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", nbBox=" + nbBox +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
