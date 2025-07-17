/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author laptop368
 */
public class AppointmentSchedule {

    private int id;
    private String confirmationStatus;
    private Doctor doctor;
    private Patient patient;
    private DoctorShiftSlot shiftSlot;

    private String appointment_date;
    private String appointment_hour;
    private String Symptom;

    public AppointmentSchedule() {
    }

    public AppointmentSchedule(int id, String confirmationStatus, Doctor doctor, Patient patient, DoctorShiftSlot shiftSlot) {
        this.id = id;
        this.confirmationStatus = confirmationStatus;
        this.doctor = doctor;
        this.patient = patient;
        this.shiftSlot = shiftSlot;
    }

    public AppointmentSchedule(int id, String confirmationStatus, Doctor doctor, Patient patient, DoctorShiftSlot shiftSlot, String appointment_date, String appointment_hour, String Symptom) {
        this.id = id;
        this.confirmationStatus = confirmationStatus;
        this.doctor = doctor;
        this.patient = patient;
        this.shiftSlot = shiftSlot;
        this.appointment_date = appointment_date;
        this.appointment_hour = appointment_hour;
        this.Symptom = Symptom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DoctorShiftSlot getShiftSlot() {
        return shiftSlot;
    }

    public void setShiftSlot(DoctorShiftSlot shiftSlot) {
        this.shiftSlot = shiftSlot;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_hour() {
        return appointment_hour;
    }

    public void setAppointment_hour(String appointment_hour) {
        this.appointment_hour = appointment_hour;
    }

    public String getSymptom() {
        return Symptom;
    }

    public void setSymptom(String Symptom) {
        this.Symptom = Symptom;
    }

//    @Override
//    public String toString() {
//        return "AppointmentSchedule{" + "id=" + id + ", confirmationStatus=" + confirmationStatus + ", doctor=" + doctor + ", patient=" + patient + ", shiftSlot=" + shiftSlot + '}';
//    }

    @Override
    public String toString() {
        return "AppointmentSchedule{" + "id=" + id + ", confirmationStatus=" + confirmationStatus + ", doctor=" + doctor + ", patient=" + patient + ", shiftSlot=" + shiftSlot + ", appointment_date=" + appointment_date + ", appointment_hour=" + appointment_hour + ", Symptom=" + Symptom + '}';
    }

}
