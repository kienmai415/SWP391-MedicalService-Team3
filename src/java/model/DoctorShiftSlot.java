/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author laptop368
 */
public class DoctorShiftSlot {

    private int id;
    private int doctorId;
    private String slotStartTime;
    private String date;
    private boolean isBooked;

    public DoctorShiftSlot() {
    }

    public DoctorShiftSlot(int id, int doctorId, String slotStartTime, String date, boolean isBooked) {
        this.id = id;
        this.doctorId = doctorId;
        this.slotStartTime = slotStartTime;
        this.date = date;
        this.isBooked = isBooked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSlotStartTime() {
        return slotStartTime;
    }

    public void setSlotStartTime(String slotStartTime) {
        LocalTime time = LocalTime.parse(slotStartTime);
        String formatted = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.slotStartTime = formatted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
        this.date = formattedDate;
    }

    public boolean isIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    @Override
    public String toString() {
        return "DoctorShiftSlot{" + "id=" + id + ", doctorId=" + doctorId + ", slotStartTime=" + slotStartTime + ", date=" + date + ", isBooked=" + isBooked + '}';
    }

}
