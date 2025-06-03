/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author maiki
 */
public class Doctor {
    private int id;
    private int accountId;
    private String imageURL;
    private String address;
    private LocalDateTime dob;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private int doctorLevelId; 
    private int specializationId;
    private Account account;
    public Doctor() {
    }

    public Doctor(int id, int accountId, String imageURL, String address, LocalDateTime dob, String fullName, String gender, String phoneNumber, int doctorLevelId, int specializationId, Account account) {
        this.id = id;
        this.accountId = accountId;
        this.imageURL = imageURL;
        this.address = address;
        this.dob = dob;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.doctorLevelId = doctorLevelId;
        this.specializationId = specializationId;
        this.account = account;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
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

    public int getDoctorLevelId() {
        return doctorLevelId;
    }

    public void setDoctorLevelId(int doctorLevelId) {
        this.doctorLevelId = doctorLevelId;
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }
    
    public Account getAccount() { 
        return account; 
    }
    
    public void setAccount(Account account) { 
        this.account = account; 
    }
}
