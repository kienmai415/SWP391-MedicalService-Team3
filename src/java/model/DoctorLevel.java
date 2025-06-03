/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author maiki
 */
public class DoctorLevel {
    

    private int id;
    private String levelName;
    private double examinationFee;

    public DoctorLevel() {
    }

    public DoctorLevel(int id, String levelName, double examinationFee) {
        this.id = id;
        this.levelName = levelName;
        this.examinationFee = examinationFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public double getExaminationFee() {
        return examinationFee;
    }

    public void setExaminationFee(double examinationFee) {
        this.examinationFee = examinationFee;
    }
    
}


