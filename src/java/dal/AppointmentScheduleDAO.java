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

    public int addAppointment(int patientId, int doctorId, String dateApp, String timeApp, String description) {
        String sql = """
        INSERT INTO dbo.appointmentSchedule
            (patientId, doctorId, appointment_date, appointment_hour, Symptom)
        VALUES (?, ?, ?, ?, ?)
    """;
        int isUpdated = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, dateApp);
            ps.setString(4, timeApp);
            ps.setNString(5, description);
            isUpdated = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (isUpdated == 0) ? -1 : isUpdated;
    }

    public List<AppoinmentScheduleDTO> getListAppointmentDTOByPId(int id) {
        List<AppoinmentScheduleDTO> list = new ArrayList<>();
        String sql = """
        SELECT   
            a.id,
            p.fullName AS patientName,
            d.fullName AS doctorName,
            d.phoneNumber AS doctorPhone,
            dl.name AS doctorLevel,
            s.name AS specialization,
            a.appointment_date,
            a.appointment_hour,
            a.Symptom,
            a.attendanceStatus,
            CASE 
                WHEN a.confirmationStatus = 'Cancel' THEN N'Đã hủy'
                WHEN a.confirmationStatus = 'Pending' THEN N'Đang chờ xử lý'
                WHEN a.confirmationStatus = 'Done' AND a.attendanceStatus = 'Completed' THEN N'Đã hoàn thành'
                WHEN a.confirmationStatus = 'Done' AND a.attendanceStatus = 'No-show' THEN N'Không đến khám'
                WHEN a.confirmationStatus = 'Done' THEN N'Đã xác nhận'
                ELSE N'Không xác định'
            END AS status
        FROM 
            appointmentSchedule a
        JOIN patient p ON a.patientId = p.id
        JOIN doctor d ON a.doctorId = d.id
        JOIN doctorLevel dl ON d.doctorLevelId = dl.id
        JOIN specialization s ON d.specializationId = s.id
        WHERE p.id = ?
        ORDER BY a.id ASC;
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppoinmentScheduleDTO dto = new AppoinmentScheduleDTO(
                        rs.getInt("id"),
                        rs.getString("patientName"),
                        rs.getString("doctorName"),
                        rs.getString("doctorPhone"),
                        rs.getString("doctorLevel"),
                        rs.getString("specialization"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_hour"),
                        rs.getString("status"),
                        rs.getString("Symptom")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //xu ly doctor
    public List<AppoinmentScheduleDTO> getListAppointmentDTOByDoctorId(int doctorId) {
        List<AppoinmentScheduleDTO> list = new ArrayList<>();
        String sql = """
        SELECT 
            a.id,
            p.fullName AS patientName,
            d.fullName AS doctorName,
            d.phoneNumber AS doctorPhone,
            dl.name AS doctorLevel,
            s.name AS specialization,
            a.appointment_date,
            a.appointment_hour,
            a.Symptom,
            a.attendanceStatus
        FROM appointmentSchedule a
        JOIN patient p ON a.patientId = p.id
        JOIN doctor d ON a.doctorId = d.id
        JOIN doctorLevel dl ON d.doctorLevelId = dl.id
        JOIN specialization s ON d.specializationId = s.id
        WHERE d.id = ? AND a.confirmationStatus = 'Done'
        ORDER BY a.id ASC;
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String attendanceStatus = rs.getString("attendanceStatus");
                String status;

                if ("Completed".equalsIgnoreCase(attendanceStatus)) {
                    status = "Đã hoàn thành";
                } else if ("No-show".equalsIgnoreCase(attendanceStatus)) {
                    status = "Không đến khám";
                } else {
                    status = "Đã xác nhận";
                }

                AppoinmentScheduleDTO dto = new AppoinmentScheduleDTO(
                        rs.getInt("id"),
                        rs.getString("patientName"),
                        rs.getString("doctorName"),
                        rs.getString("doctorPhone"),
                        rs.getString("doctorLevel"),
                        rs.getString("specialization"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_hour"),
                        status,
                        rs.getString("Symptom")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public AppointmentSchedule getAppointmentScheduleById(int appointmentId) {
        String sql = """
        SELECT a.id AS appointment_id, a.confirmationStatus, a.Symptom,
               a.appointment_date, a.appointment_hour,

               p.id AS patient_id, p.fullName AS patient_name, p.dateOfBirth AS patient_dob,
               p.gender AS patient_gender, p.phoneNumber AS patient_phone,
               p.email AS patient_email, p.address AS patient_address,

               d.id AS doctor_id, d.fullName AS doctor_name, d.gender AS doctor_gender,
               d.email AS doctor_email, d.phoneNumber AS doctor_phone,
               d.address AS doctor_address,

               dl.id AS level_id, dl.name AS level_name, dl.examinationFee,
               s.id AS spec_id, s.name AS spec_name, s.description AS spec_description,

               sl.id AS slot_id, sl.slotStartTime, sl.date, sl.isBooked
        FROM appointmentSchedule a
        JOIN patient p ON a.patientId = p.id
        JOIN doctor d ON a.doctorId = d.id
        JOIN doctorLevel dl ON d.doctorLevelId = dl.id
        JOIN specialization s ON d.specializationId = s.id
        JOIN doctorShiftSlot sl ON a.doctorShiftId = sl.id
        WHERE a.id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Populate patient
                Patient patient = new Patient();
                patient.setId(rs.getInt("patient_id"));
                patient.setFullName(rs.getString("patient_name"));
                patient.setDob(rs.getDate("patient_dob").toLocalDate());
                patient.setGender(rs.getString("patient_gender"));
                patient.setPhoneNumber(rs.getString("patient_phone"));
                patient.setEmail(rs.getString("patient_email"));
                patient.setAddress(rs.getString("patient_address"));

                // Populate doctor
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("doctor_id"));
                doctor.setFullName(rs.getString("doctor_name"));
                doctor.setGender(rs.getString("doctor_gender"));
                doctor.setPhoneNumber(rs.getString("doctor_phone"));
                doctor.setEmail(rs.getString("doctor_email"));
                doctor.setAddress(rs.getString("doctor_address"));

                DoctorLevel level = new DoctorLevel();
                level.setId(rs.getInt("level_id"));
                level.setName(rs.getString("level_name"));
                level.setExaminationFee(rs.getDouble("examinationFee"));
                doctor.setDoctorLevel(level);

                Specialization spec = new Specialization();
                spec.setId(rs.getInt("spec_id"));
                spec.setName(rs.getString("spec_name"));
                spec.setDescription(rs.getString("spec_description"));
                doctor.setSpecialization(spec);

                // Populate shift slot
                DoctorShiftSlot slot = new DoctorShiftSlot();
                slot.setId(rs.getInt("slot_id"));
                slot.setSlotStartTime(rs.getString("slotStartTime"));
                slot.setDate(rs.getDate("date").toLocalDate());
                slot.setIsBooked(rs.getBoolean("isBooked"));

                // Return full appointment
                return new AppointmentSchedule(
                        rs.getInt("appointment_id"),
                        rs.getString("confirmationStatus"),
                        doctor,
                        patient,
                        slot,
                        rs.getString("appointment_date"),
                        rs.getString("appointment_hour"),
                        rs.getString("Symptom")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void markMedicalRecordAsDone(int appointmentId) {
        String sql = """
        UPDATE mr
        SET examinationStatus = 'Done'
        FROM medicalRecord mr
        JOIN appointmentSchedule a ON 
            mr.patientId = a.patientId 
            AND mr.doctorId = a.doctorId 
            AND CAST(mr.time AS DATE) = a.appointment_date
        WHERE a.id = ? AND a.confirmationStatus = 'Done'
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAttendanceStatus(int appointmentId, String attendanceStatus) {
        String sql = "UPDATE appointmentSchedule SET attendanceStatus = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, attendanceStatus);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
        List<AppoinmentScheduleDTO> apd = dao.getListAppointmentDTOByPId(1);
        for (AppoinmentScheduleDTO appoinmentScheduleDTO : apd) {
            System.out.println(appoinmentScheduleDTO);
        }
    }
}
