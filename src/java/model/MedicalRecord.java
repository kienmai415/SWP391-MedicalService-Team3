/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author BB-MT
 */
public class MedicalRecord {

    private int id;
    private String namePatient; //p
    private String addressP;
    private String phoneP;
    private String emailP;
    private String genderP;
    private String dob;     //p
    private String nameDoctor;  //d
    private String examinationTime; // thoi gian kham thuc medical record
    private String examinationStatus;   //trang thai da kham medical record
    private String paymentStatus; //trang thai thanh toan medical record
    private String weight; //md detail
    private String height;
    private String heartBeat;
    private String bloodPressure;
    private String detailMedical;
    private String diagnose;
    private String solution;
    private String medicineAndNotes;

    public MedicalRecord() {
    }

    public MedicalRecord(int id, String namePatient, String addressP, String phoneP, String emailP, String genderP, String dob, String nameDoctor, String examinationTime, String examinationStatus, String paymentStatus, String weight, String height, String heartBeat, String bloodPressure, String detailMedical, String diagnose, String solution, String medicineAndNotes) {
        this.id = id;
        this.namePatient = namePatient;
        this.addressP = addressP;
        this.phoneP = phoneP;
        this.emailP = emailP;
        this.genderP = genderP;
        this.dob = dob;
        this.nameDoctor = nameDoctor;
        this.examinationTime = examinationTime;
        this.examinationStatus = examinationStatus;
        this.paymentStatus = paymentStatus;
        this.weight = weight;
        this.height = height;
        this.heartBeat = heartBeat;
        this.bloodPressure = bloodPressure;
        this.detailMedical = detailMedical;
        this.diagnose = diagnose;
        this.solution = solution;
        this.medicineAndNotes = medicineAndNotes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public String getAddressP() {
        return addressP;
    }

    public void setAddressP(String addressP) {
        this.addressP = addressP;
    }

    public String getPhoneP() {
        return phoneP;
    }

    public void setPhoneP(String phoneP) {
        this.phoneP = phoneP;
    }

    public String getEmailP() {
        return emailP;
    }

    public void setEmailP(String emailP) {
        this.emailP = emailP;
    }

    public String getGenderP() {
        return genderP;
    }

    public void setGenderP(String genderP) {
        this.genderP = genderP;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }

    public String getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(String examinationTime) {
        this.examinationTime = examinationTime;
    }

    public String getExaminationStatus() {
        return examinationStatus;
    }

    public void setExaminationStatus(String examinationStatus) {
        this.examinationStatus = examinationStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(String heartBeat) {
        this.heartBeat = heartBeat;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getDetailMedical() {
        return detailMedical;
    }

    public void setDetailMedical(String detailMedical) {
        this.detailMedical = detailMedical;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getMedicineAndNotes() {
        return medicineAndNotes;
    }

    public void setMedicineAndNotes(String medicineAndNotes) {
        this.medicineAndNotes = medicineAndNotes;
    }

    @Override
    public String toString() {
        return "MedicalRecord {\n"
                + "  id = " + id + "\n"
                + "  namePatient = '" + namePatient + "'\n"
                + "  addressP = '" + addressP + "'\n"
                + "  phoneP = '" + phoneP + "'\n"
                + "  emailP = '" + emailP + "'\n"
                + "  genderP = '" + genderP + "'\n"
                + "  dob = '" + dob + "'\n"
                + "  nameDoctor = '" + nameDoctor + "'\n"
                + "  examinationTime = '" + examinationTime + "'\n"
                + "  examinationStatus = '" + examinationStatus + "'\n"
                + "  paymentStatus = '" + paymentStatus + "'\n"
                + "  weight = '" + weight + "'\n"
                + "  height = '" + height + "'\n"
                + "  heartBeat = '" + heartBeat + "'\n"
                + "  bloodPressure = '" + bloodPressure + "'\n"
                + "  detailMedical = '" + detailMedical + "'\n"
                + "  diagnose = '" + diagnose + "'\n"
                + "  solution = '" + solution + "'\n"
                + "  medicineAndNotes = '" + medicineAndNotes + "'\n"
                + '}';
    }

}
