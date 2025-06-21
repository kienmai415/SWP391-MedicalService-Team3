/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.List;
import model.AppoinmentScheduleDTO;
import model.AppointmentSchedule;
import model.Doctor;
import model.DoctorLevel;
import model.DoctorShiftSlot;
import model.Patient;
import model.Specialization;
import ultil.Validation;

/**
 *
 * @author laptop368
 */
public class AppointmentScheduleDAO extends DBContext {

    Validation v = new Validation();

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

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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

    public List<AppointmentSchedule> getListAppointmentById(int patientId) {
        List<AppointmentSchedule> list = new ArrayList<>();
        String sql = """
                     SELECT a.id AS appointment_id,
                            a.confirmationStatus,
                     
                            p.id AS patient_id, p.fullName AS patient_name, p.dateOfBirth AS patient_DOB,
                            p.gender AS patient_gender, p.phoneNumber AS patient_phone,
                            p.email AS patient_email, p.address AS patient_address,
                     
                            d.id AS doctor_id, d.fullName AS doctor_name,
                            d.email AS doctor_email, d.phoneNumber AS doctor_phone,
                     
                            dl.id AS level_id, dl.name AS level_name, dl.examinationFee,
                            s.id AS spec_id, s.name AS spec_name, s.description AS spec_description,
                     
                            sl.id AS slot_id, sl.slotStartTime, sl.date, sl.isBooked
                     
                     FROM appointmentSchedule a
                     JOIN patient p ON a.patientId = p.id
                     JOIN doctor d ON a.doctorId = d.id
                     JOIN doctorLevel dl ON d.doctorLevelId = dl.id
                     JOIN specialization s ON d.specializationId = s.id
                     JOIN doctorShiftSlot sl ON a.doctorShiftId = sl.id where p.id = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

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

    public List<AppointmentSchedule> getListAppointmentByDoctorId(int id) {
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
                + "JOIN doctorShiftSlot sl ON a.doctorShiftId = sl.id where d.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

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

    //Quy
    public int addAppointment(int id, String dateApp, String timeApp, String decription) {
        String sql = """
                      INSERT INTO dbo.appointmentSchedule
                       (
                           patientId,
                           appointment_date,
                           appointment_hour,
                           Symptom
                       )
                       VALUES
                       (?,?,?,?)""";
        int isUpdated = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, dateApp);
            ps.setString(3, timeApp);
            ps.setNString(4, decription);
            isUpdated = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isUpdated == 0) {
            return -1;
        } else {
            return isUpdated;
        }
    }

    public List<AppoinmentScheduleDTO> getListAppointmentDTOByPId(int id) {
        List<AppoinmentScheduleDTO> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         p.fullName AS patientName,
                         d.fullName AS doctorName,
                         d.phoneNumber AS doctorPhone,
                         dl.name AS doctorLevel,
                         s.name AS specialization,
                         a.appointment_date,
                         a.appointment_hour,
                         
                         -- Trạng thái hiển thị
                         CASE 
                             WHEN mr.examinationStatus = 'Done' THEN N'Đã khám'
                             WHEN a.confirmationStatus = 'Pending' THEN N'Đang chờ xử lý'
                             WHEN a.confirmationStatus = 'Accept' THEN N'Đã xác nhận'
                             WHEN a.confirmationStatus = 'Cancel' THEN N'Đã hủy'
                             ELSE N'Không đến khám'
                         END AS status
                     
                     FROM 
                         dbo.appointmentSchedule a
                     JOIN 
                         dbo.patient p ON a.patientId = p.id
                     JOIN 
                         dbo.doctor d ON a.doctorId = d.id
                     JOIN 
                         dbo.doctorLevel dl ON d.doctorLevelId = dl.id
                     JOIN 
                         dbo.doctorShiftSlot ds ON a.doctorShiftId = ds.id
                     	JOIN dbo.specialization s ON s.id = d.specializationId
                     
                    
                     LEFT JOIN 
                         medicalRecord mr 
                         ON mr.patientId = a.patientId 
                         AND mr.doctorId = a.doctorId
                         AND FORMAT(mr.time, 'yyyy-MM-dd') = a.appointment_date
                     
                     WHERE 
                         p.id = ? 
                     ORDER BY 
                         a.appointment_date DESC, a.appointment_hour;""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppoinmentScheduleDTO appP = new AppoinmentScheduleDTO();
                appP.setNameP(rs.getString(1));
                appP.setNameD(rs.getString(2));
                appP.setPhoneD(rs.getString(3));
                appP.setLevelD(rs.getString(4));
                appP.setSpecD(rs.getString(5));
                appP.setAppointDate(rs.getString(6));
                appP.setAppointTime(rs.getString(7));
                appP.setStatus(rs.getString(8));

                list.add(appP);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public static void main(String[] args) {
        AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
        List<AppoinmentScheduleDTO> apd = dao.getListAppointmentDTOByPId(1);
        for (AppoinmentScheduleDTO appoinmentScheduleDTO : apd) {
            System.out.println(appoinmentScheduleDTO);
        }
    }
}
