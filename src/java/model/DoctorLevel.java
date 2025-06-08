/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author laptop368
 */
public class DoctorLevel {
    private int id;
    private String name;
    private double examinationFee;

    public DoctorLevel() {
    }

    public DoctorLevel(int id, String name, double examinationFee) {
        this.id = id;
        this.name = name;
        this.examinationFee = examinationFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExaminationFee() {
        return examinationFee;
    }

    public void setExaminationFee(double examinationFee) {
        this.examinationFee = examinationFee;
    }

    @Override
    public String toString() {
        return "DoctorLevel{" + "id=" + id + ", name=" + name + ", examinationFee=" + examinationFee + '}';
    }
    
}
