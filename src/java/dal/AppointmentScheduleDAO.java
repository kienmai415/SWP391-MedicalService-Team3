/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.AppointmentSchedule;
import model.Doctor;
import model.DoctorLevel;
import model.DoctorShiftSlot;
import model.Patient;
import model.Specialization;

/**
 *
 * @author laptop368
 */
public class AppointmentScheduleDAO extends DBContext {

    public List<AppointmentSchedule> getListAppointment() {
        List<AppointmentSchedule> list = new ArrayList<>();
        String sql = "SELECT a.id AS appointment_id,\n"
                + "       a.confirmationStatus,\n"
                + "\n"
                + "       p.id AS patient_id, p.fullName AS patient_name, p.dateOfBirth AS patient_DOB,\n"
                + "       p.gender AS patient_gender, p.phoneNumber AS patient_phone,\n"
                + "       p.email AS patient_email, p.address AS patient_address,\n"
                + "\n"
                + "       d.id AS doctor_id, d.fullName AS doctor_name,\n"
                + "       d.email AS doctor_email, d.phoneNumber AS doctor_phone,\n"
                + "\n"
                + "       dl.id AS level_id, dl.name AS level_name, dl.examinationFee,\n"
                + "       s.id AS spec_id, s.name AS spec_name, s.description AS spec_description,\n"
                + "\n"
                + "       sl.id AS slot_id, sl.slotStartTime, sl.date, sl.isBooked\n"
                + "\n"
                + "FROM appointmentSchedule a\n"
                + "JOIN patient p ON a.patientId = p.id\n"
                + "JOIN doctor d ON a.doctorId = d.id\n"
                + "JOIN doctorLevel dl ON d.doctorLevelId = dl.id\n"
                + "JOIN specialization s ON d.specializationId = s.id\n"
                + "JOIN doctorShiftSlot sl ON a.doctorShiftId = sl.id";

        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Specialization spec = new Specialization();
                spec.setId(rs.getInt("spec_id"));
                spec.setName(rs.getString("spec_name"));
                spec.setDescription(rs.getString("spec_description"));

// DoctorLevel
                DoctorLevel level = new DoctorLevel();
                level.setId(rs.getInt("level_id"));
                level.setName(rs.getString("level_name"));
                level.setExaminationFee(rs.getDouble("examinationFee"));
                // Patient
                Patient patient = new Patient();
                patient.setId(rs.getInt("patient_id"));
                patient.setFullName(rs.getString("patient_name"));
                patient.setDob(rs.getDate("patient_DOB").toLocalDate());
                patient.setGender(rs.getString("patient_gender"));
                patient.setPhoneNumber(rs.getString("patient_phone"));
                patient.setEmail(rs.getString("patient_email"));
                patient.setAddress(rs.getString("patient_address"));

                // Doctor
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("doctor_id"));
                doctor.setFullName(rs.getString("doctor_name"));
                doctor.setEmail(rs.getString("doctor_email"));
                doctor.setPhoneNumber(rs.getString("doctor_phone"));
                doctor.setDoctorLevel(level);
                doctor.setSpecialization(spec);
                // ShiftSlot
                DoctorShiftSlot slot = new DoctorShiftSlot();
                slot.setId(rs.getInt("slot_id"));
                slot.setSlotStartTime(rs.getString("slotStartTime"));
                slot.setDate(rs.getDate("date").toLocalDate());
                slot.setIsBooked(rs.getBoolean("isBooked"));

                // AppointmentSchedule
                AppointmentSchedule app = new AppointmentSchedule();
                app.setId(rs.getInt("appointment_id"));
                app.setConfirmationStatus(rs.getString("confirmationStatus"));
                app.setPatient(patient);
                app.setDoctor(doctor);
                app.setShiftSlot(slot);

                list.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
        List<AppointmentSchedule> list = dao.getListAppointment();
        for (AppointmentSchedule co : list) {
            System.out.println(co);
        }
    }
}
