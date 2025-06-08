/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Quy
 */
public class Patient {

    private int id;
    private String imageURL;
    private String address;
    private LocalDate dob;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private String identityNumber;
    private String insuranceNumber;
    private String email;
    private String pass;
    private boolean status;
    private String role;

    public Patient() {
    }

    public Patient(int id, String imageURL, String address, LocalDate dob, String fullName, String gender, String phoneNumber, String identityNumber, String insuranceNumber, String email, String pass, boolean status, String rold) {
        this.id = id;
        this.imageURL = imageURL;
        this.address = address;
        this.dob = dob;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.identityNumber = identityNumber;
        this.insuranceNumber = insuranceNumber;
        this.email = email;
        this.pass = pass;
        this.status = status;
        this.role = rold;
    }

    

    public String getRold() {
        return role;
    }

    public void setRold_id(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Patient{"
                + "id=" + id
                + ", imageURL='" + imageURL + '\''
                + ", address='" + address + '\''
                + ", dob=" + dob
                + ", fullName='" + fullName + '\''
                + ", gender='" + gender + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", identityNumber='" + identityNumber + '\''
                + ", insuranceNumber='" + insuranceNumber + '\''
                + ", email='" + email + '\''
                + ", pass='" + pass + '\''
                + ", status=" + status
                + '}';
    }

}
