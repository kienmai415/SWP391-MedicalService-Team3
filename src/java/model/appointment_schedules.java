/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author MinhQuang
 */
public class appointment_schedules {

    private int id;
    private Date date_appointment;
    private String start_time;         // tương ứng với TIME
    private String end_time;           // tương ứng với TIME
    private int patient_id;
    private int doctor_id;
    private Integer appointment_slot_id;      // có thể NULL => dùng wrapper class
    private String confirmation_status;       // mặc định là "Pending"

    public appointment_schedules() {
    }

    public appointment_schedules(int id, Date date_appointment, String start_time, String end_time, int patient_id, int doctor_id, Integer appointment_slot_id, String confirmation_status) {
        this.id = id;
        this.date_appointment = date_appointment;
        this.start_time = start_time;
        this.end_time = end_time;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.appointment_slot_id = appointment_slot_id;
        this.confirmation_status = confirmation_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_appointment() {
        return date_appointment;
    }

    public void setDate_appointment(Date date_appointment) {
        this.date_appointment = date_appointment;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Integer getAppointment_slot_id() {
        return appointment_slot_id;
    }

    public void setAppointment_slot_id(Integer appointment_slot_id) {
        this.appointment_slot_id = appointment_slot_id;
    }

    public String getConfirmation_status() {
        return confirmation_status;
    }

    public void setConfirmation_status(String confirmation_status) {
        this.confirmation_status = confirmation_status;
    }

    @Override
    public String toString() {
        return "appointment_schedules{" + "id=" + id + ", date_appointment=" + date_appointment + ", start_time=" + start_time + ", end_time=" + end_time + ", patient_id=" + patient_id + ", doctor_id=" + doctor_id + ", appointment_slot_id=" + appointment_slot_id + ", confirmation_status=" + confirmation_status + '}';
    }
    
    
}
