/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author laptop368
 */
public class Doctor {
    private int id;
    private String imageURL;
    private String email;
    private String username;
    private String password;
    private String role;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String address;
    private String phoneNumber;
    private boolean status;
    private Specialization specialization;   
    private DoctorLevel doctorLevel; 

    public Doctor() {
    }

    public Doctor(int id, String imageURL, String email, String username, String password, String role, String fullName, LocalDate dob, String gender, String address, String phoneNumber, boolean status, Specialization specialization, DoctorLevel doctorLevel) {
        this.id = id;
        this.imageURL = imageURL;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.specialization = specialization;
        this.doctorLevel = doctorLevel;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public DoctorLevel getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(DoctorLevel doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    @Override
    public String toString() {
        return "Doctor{" + "id=" + id + ", imageURL=" + imageURL + ", email=" + email + ", username=" + username + ", password=" + password + ", role=" + role + ", fullName=" + fullName + ", dob=" + dob + ", gender=" + gender + ", address=" + address + ", phoneNumber=" + phoneNumber + ", status=" + status + ", specialization=" + specialization + ", doctorLevel=" + doctorLevel + '}';
    }

    
    
    
}
