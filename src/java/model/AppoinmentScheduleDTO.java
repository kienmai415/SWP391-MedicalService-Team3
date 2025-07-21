/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author BB-MT
 */
public class AppoinmentScheduleDTO {
    private int id;
    private String nameP;
    private String nameD;
    private String phoneD;
    private String levelD;
    private String specD;
    private String appointDate;
    private String appointTime;
    private String status;

    private String symptom;
    

    public AppoinmentScheduleDTO() {
    }

//    public AppoinmentScheduleDTO(String nameP, String nameD, String phoneD, String levelD, String specD, String appointDate, String appointTime, String status) {
//        this.nameP = nameP;
//        this.nameD = nameD;
//        this.phoneD = phoneD;
//        this.levelD = levelD;
//        this.specD = specD;
//        this.appointDate = appointDate;
//        this.appointTime = appointTime;
//        this.status = status;
//    }

    //có triệu chứng
//    public AppoinmentScheduleDTO(String nameP, String nameD, String phoneD, String levelD, String specD,
//            String appointDate, String appointTime, String status, String symptom) {
//        this.nameP = nameP;
//        this.nameD = nameD;
//        this.phoneD = phoneD;
//        this.levelD = levelD;
//        this.specD = specD;
//        this.appointDate = appointDate;
//        this.appointTime = appointTime;
//        this.status = status;
//        this.symptom = symptom;
//    }
    //có id
    public AppoinmentScheduleDTO(int id, String nameP, String nameD, String phoneD, String levelD, String specD, String appointDate, String appointTime, String status, String symptom) {
        this.id = id;
        this.nameP = nameP;
        this.nameD = nameD;
        this.phoneD = phoneD;
        this.levelD = levelD;
        this.specD = specD;
        this.appointDate = appointDate;
        this.appointTime = appointTime;
        this.status = status;
        this.symptom = symptom;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

      
    
    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getNameD() {
        return nameD;
    }

    public void setNameD(String nameD) {
        this.nameD = nameD;
    }

    public String getPhoneD() {
        return phoneD;
    }

    public void setPhoneD(String phoneD) {
        this.phoneD = phoneD;
    }

    public String getLevelD() {
        return levelD;
    }

    public void setLevelD(String levelD) {
        this.levelD = levelD;
    }

    public String getSpecD() {
        return specD;
    }

    public void setSpecD(String specD) {
        this.specD = specD;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        if (appointDate != null && !appointDate.isEmpty()) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = inputFormat.parse(appointDate);
                this.appointDate = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                this.appointDate = appointDate; // fallback nếu parse lỗi
            }
        } else {
            this.appointDate = null;
        }
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AppointmentInfo{"
                + "nameP='" + nameP + '\''
                + ", nameD='" + nameD + '\''
                + ", phoneD='" + phoneD + '\''
                + ", levelD='" + levelD + '\''
                + ", specD='" + specD + '\''
                + ", appointDate='" + appointDate + '\''
                + ", appointTime='" + appointTime + '\''
                + ", status='" + status + '\''
                + '}';
    }
}
