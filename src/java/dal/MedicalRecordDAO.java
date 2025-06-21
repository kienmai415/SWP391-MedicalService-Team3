/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.MedicalRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author BB-MT
 */
public class MedicalRecordDAO extends DBContext {

    private static final String CHECKUP_DETAILS = "Chưa có thông tin";

    public ArrayList<MedicalRecord> getMedicalRecordByPId(int pID) {
        ArrayList<MedicalRecord> mrp = new ArrayList<>();
        String sql = """
                     SELECT dbo.patient.id,patient.fullName,patient.address,patient.phoneNumber,patient.email,patient.gender,patient.dateOfBirth,
                     doctor.fullName AS doctor_name,weight,height,heartBeat,bloodPressure,detailMedical,diagnose,solution,medicineAndNotes,
                     time AS examination_time,examinationStatus,paymentStatus
                     
                     FROM dbo.medicalRecord
                     LEFT JOIN dbo.patient ON patient.id = medicalRecord.patientId
                     LEFT JOIN dbo.doctor ON doctor.id = medicalRecord.doctorId
                     LEFT JOIN dbo.medicalRecordDetail ON medicalRecordDetail.medicalRecordId = medicalRecord.id
                     WHERE patient.id = ? and examinationStatus = 'Done' """;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalRecord mr = new MedicalRecord(rs.getInt("id"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("doctor_name"),
                        rs.getString("examination_time"),
                        rs.getString("examinationStatus"),
                        rs.getString("paymentStatus"),
                        rs.getString("weight"),
                        rs.getString("height"),
                        rs.getString("heartBeat"),
                        rs.getString("bloodPressure"),
                        rs.getString("detailMedical"),
                        rs.getString("diagnose"),
                        rs.getString("solution"),
                        rs.getString("medicineAndNotes"));
                mrp.add(mr);

            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mrp;
    }

    public static void main(String[] args) {
        MedicalRecordDAO mrd = new MedicalRecordDAO();
        ArrayList<MedicalRecord> mr = mrd.getMedicalRecordByPId(1);
        if (mr != null) {
            for (MedicalRecord medicalRecord : mr) {
                System.out.println(medicalRecord.toString());
            }
        } else {
            System.out.println("Null");
        }
    }
}
