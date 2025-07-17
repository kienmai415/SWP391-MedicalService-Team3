/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.AppointmentSchedule;
import model.Doctor;
import model.DoctorShiftSlot;
import model.Patient;

/**
 *
 * @author MinhQuang
 */
public class ReceptionDAO extends DBContext {

    public ArrayList<AppointmentSchedule> getAllSchedules() {
        ArrayList<AppointmentSchedule> listAp = new ArrayList<>();
        String sql = """
        select 
            app.id ,
            ds.date ,
            ds.slotStartTime ,
            pa.fullName as patientName , 
            dt.fullName as doctorName,
            app.confirmationStatus,
            app.appointment_date,
            app.appointment_hour,
            app.symptom
        from appointmentSchedule app
        JOIN doctorShiftSlot ds on app.doctorShiftId = ds.id
        JOIN patient pa ON app.patientId = pa.id
        JOIN doctor dt on app.doctorId = dt.id
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int apId = rs.getInt("id");
                String apConfirm = rs.getString("confirmationStatus");
                String doctorName = rs.getString("doctorName");
                String patientName = rs.getString("patientName");

                Doctor apDoctor = new Doctor();
                apDoctor.setFullName(doctorName);
                Patient apPatient = new Patient();
                apPatient.setFullName(patientName);

                DoctorShiftSlot apSlot = new DoctorShiftSlot();
                apSlot.setSlotStartTime(rs.getString("slotStartTime"));
                apSlot.setDate(rs.getDate("date").toLocalDate());

                AppointmentSchedule app = new AppointmentSchedule(
                        apId,
                        apConfirm,
                        apDoctor,
                        apPatient,
                        apSlot,
                        rs.getString("appointment_date"),
                        rs.getString("appointment_hour"),
                        rs.getString("symptom")
                );

                listAp.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAp;
    }

    public AppointmentSchedule getAppointmentSchedulesById(int id) {
        DBContext dbc = DBContext.getInstance();
        String sql = """
            SELECT app.id, app.appointment_date, app.appointment_hour, app.confirmationStatus,
                   pa.fullName AS patientName, pa.email AS patientEmail, pa.phoneNumber AS patientPhone,
                   pa.gender AS patientGender, pa.address AS patientAddress,
                   dt.fullName AS doctorName, dt.email AS doctorEmail, dt.phoneNumber AS doctorPhone,
                   dt.gender AS doctorGender, dt.address AS doctorAddress
            FROM appointmentSchedule app
            JOIN patient pa ON app.patientId = pa.id
            JOIN doctor dt ON app.doctorId = dt.id
            WHERE app.id = ?
        """;
        try (PreparedStatement ps = dbc.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AppointmentSchedule ap = new AppointmentSchedule();
                ap.setId(rs.getInt("id"));
                ap.setConfirmationStatus(rs.getString("confirmationStatus"));

                DoctorShiftSlot shift = new DoctorShiftSlot();
                shift.setDate(LocalDate.parse(rs.getString("appointment_date")));
                shift.setSlotStartTime(rs.getString("appointment_hour"));
                ap.setShiftSlot(shift);

                shift.setDate(LocalDate.parse(rs.getString("appointment_date")));
                shift.setSlotStartTime(rs.getString("appointment_hour"));
                ap.setShiftSlot(shift);

                Patient patient = new Patient();
                patient.setFullName(rs.getString("patientName"));
                patient.setEmail(rs.getString("patientEmail"));
                patient.setPhoneNumber(rs.getString("patientPhone"));
                patient.setGender(rs.getString("patientGender"));
                patient.setAddress(rs.getString("patientAddress"));
                ap.setPatient(patient);

                Doctor doctor = new Doctor();
                doctor.setFullName(rs.getString("doctorName"));
                doctor.setEmail(rs.getString("doctorEmail"));
                doctor.setPhoneNumber(rs.getString("doctorPhone"));
                doctor.setGender(rs.getString("doctorGender"));
                doctor.setAddress(rs.getString("doctorAddress"));
                ap.setDoctor(doctor);

                return ap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AppointmentSchedule> getAppointmentSchedulesByName(String name) {
        List<AppointmentSchedule> resultList = new ArrayList<>();
        DBContext dbc = DBContext.getInstance();
        String sql = """
        select 
            app.id ,
            ds.date ,
            ds.slotStartTime ,
            pa.fullName as patientName , 
            dt.fullName as doctorName,
            app.confirmationStatus 
        from appointmentSchedule app
        JOIN doctorShiftSlot ds on app.doctorShiftId = ds.id
        JOIN patient pa ON app.patientId = pa.id
        JOIN doctor dt on app.doctorId = dt.id
        where dt.fullName like ? or pa.fullName like ?
    """;

        try {
            PreparedStatement preparedStatement = dbc.connection.prepareStatement(sql);
            String Aname = "%" + name + "%";
            preparedStatement.setString(1, Aname);
            preparedStatement.setString(2, Aname);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int apId = rs.getInt("id");
                String apConfirm = rs.getString("confirmationStatus");

                Doctor apDoctor = new Doctor();
                apDoctor.setFullName(rs.getString("doctorName"));

                Patient apPatient = new Patient();
                apPatient.setFullName(rs.getString("patientName"));

                DoctorShiftSlot apSlot = new DoctorShiftSlot();
                apSlot.setSlotStartTime(rs.getString("slotStartTime"));
                apSlot.setDate(rs.getDate("date").toLocalDate());

                AppointmentSchedule ap = new AppointmentSchedule(apId, apConfirm, apDoctor, apPatient, apSlot);
                resultList.add(ap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

//    public ArrayList<AppointmentSchedule> get(String sql) {
//        ArrayList<AppointmentSchedule> listAp = new ArrayList<>();
//        DBContext dbc = DBContext.getInstance();
//        try {
//            PreparedStatement preparedStatement = dbc.connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                int apId = rs.getInt("id");
//                String apConfirm = rs.getString("confirmationStatus"); // đảm bảo đúng tên cột trong DB
//
//                int doctorId = rs.getInt("doctorId");
//                Doctor apDoctor = new Doctor();
//                apDoctor.setId(doctorId);
//
//                int patientId = rs.getInt("patientId");
//                Patient apPatient = new Patient();
//                apPatient.setId(patientId);
//
//                int slotId = rs.getInt("doctorShiftId");
//                DoctorShiftSlot apSlot = new DoctorShiftSlot();
//                apSlot.setId(slotId);
//
//                AppointmentSchedule app = new AppointmentSchedule(apId, apConfirm, apDoctor, apPatient, apSlot);
//                listAp.add(app);
//            }
//        } catch (Exception e) {
//            return null;
//        }
//        return listAp.isEmpty() ? null : listAp;
//    }
//phân trang
    public List<AppointmentSchedule> getAppointmentsWithPaging(int pageIndex, int pageSize) {
        List<AppointmentSchedule> list = new ArrayList<>();
        String sql = """
        SELECT app.id, app.appointment_date, app.appointment_hour,
               pa.fullName AS patientName,
               dt.fullName AS doctorName,
               app.confirmationStatus
        FROM appointmentSchedule app
        JOIN patient pa ON app.patientId = pa.id
        JOIN doctor dt ON app.doctorId = dt.id
        ORDER BY app.id ASC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppointmentSchedule ap = new AppointmentSchedule();
                ap.setId(rs.getInt("id"));
                ap.setConfirmationStatus(rs.getString("confirmationStatus"));

                // Ngày giờ lấy từ cột trực tiếp
                DoctorShiftSlot shift = new DoctorShiftSlot();
                shift.setDate(LocalDate.parse(rs.getString("appointment_date")));
                shift.setSlotStartTime(rs.getString("appointment_hour"));
                ap.setShiftSlot(shift);

                Doctor doctor = new Doctor();
                doctor.setFullName(rs.getString("doctorName"));
                ap.setDoctor(doctor);

                Patient patient = new Patient();
                patient.setFullName(rs.getString("patientName"));
                ap.setPatient(patient);

                list.add(ap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countTotalAppointments() {
        String sql = "SELECT COUNT(*) FROM appointmentSchedule";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<AppointmentSchedule> getHistoryAppointmentsWithPaging(int pageIndex, int pageSize) {
        List<AppointmentSchedule> list = new ArrayList<>();
        String sql = """
        SELECT app.id, app.confirmationStatus,
               pa.fullName AS patientName,
               dt.fullName AS doctorName,
               shift.date AS shiftDate,
               shift.slotStartTime
        FROM appointmentSchedule app
        JOIN patient pa ON app.patientId = pa.id
        JOIN doctor dt ON app.doctorId = dt.id
        JOIN doctorShiftSlot shift ON app.doctorShiftId = shift.id
        WHERE app.confirmationStatus IN (N'Done', N'Cancel')
        ORDER BY app.id ASC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppointmentSchedule ap = new AppointmentSchedule();
                ap.setId(rs.getInt("id"));
                ap.setConfirmationStatus(rs.getString("confirmationStatus"));

                Doctor doctor = new Doctor();
                doctor.setFullName(rs.getString("doctorName"));
                ap.setDoctor(doctor);

                Patient patient = new Patient();
                patient.setFullName(rs.getString("patientName"));
                ap.setPatient(patient);

                DoctorShiftSlot shift = new DoctorShiftSlot();
                shift.setDate(rs.getDate("shiftDate").toLocalDate());
                shift.setSlotStartTime(rs.getString("slotStartTime")); // or toLocalTime() if needed
                ap.setShiftSlot(shift);

                list.add(ap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countTotalHistoryAppointments() {
        String sql = """
        SELECT COUNT(*) FROM appointmentSchedule
        WHERE confirmationStatus IN (N'Done', N'Cancel')
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT id, fullName FROM doctor";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("id"));
                d.setFullName(rs.getString("fullName"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT id, fullName FROM patient";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("id"));
                p.setFullName(rs.getString("fullName"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public AppointmentSchedule getAppointmentScheduleByIdRaw(int id) {
        DBContext dbc = DBContext.getInstance();
        String sql = """
        SELECT app.id, app.appointment_date, app.appointment_hour, app.confirmationStatus,
               app.patientId, app.doctorId,
               pa.fullName AS patientName, dt.fullName AS doctorName
        FROM appointmentSchedule app
        JOIN patient pa ON app.patientId = pa.id
        JOIN doctor dt ON app.doctorId = dt.id
        WHERE app.id = ?
    """;

        try (PreparedStatement ps = dbc.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AppointmentSchedule ap = new AppointmentSchedule();
                ap.setId(rs.getInt("id"));
                ap.setConfirmationStatus(rs.getString("confirmationStatus"));

                ap.setAppointment_date(rs.getString("appointment_date"));
                ap.setAppointment_hour(rs.getString("appointment_hour"));

                Patient p = new Patient();
                p.setId(rs.getInt("patientId"));
                p.setFullName(rs.getString("patientName"));
                ap.setPatient(p);

                Doctor d = new Doctor();
                d.setId(rs.getInt("doctorId"));
                d.setFullName(rs.getString("doctorName"));
                ap.setDoctor(d);

                return ap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<DoctorShiftSlot> getSlotsByAppointmentDate(String appointmentDate) {
        List<DoctorShiftSlot> list = new ArrayList<>();
        String sql = "SELECT id, doctor_id, appointment_date, appointment_hour FROM AppointmentSchedule WHERE appointment_date = ? ORDER BY appointment_hour";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appointmentDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DoctorShiftSlot slot = new DoctorShiftSlot();
                slot.setId(rs.getInt("id"));
                slot.setDoctorId(rs.getInt("doctor_id"));
                slot.setDate(LocalDate.parse(rs.getString("appointment_date")));
                slot.setSlotStartTime(rs.getString("appointment_hour"));
                slot.setIsBooked(false);
                list.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<DoctorShiftSlot> getSlotsByAppointmentHour(String appointmentHour) {
        List<DoctorShiftSlot> list = new ArrayList<>();
        String sql = "SELECT id, doctor_id, appointment_date, appointment_hour FROM AppointmentSchedule WHERE appointment_hour = ? ORDER BY appointment_date";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appointmentHour);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DoctorShiftSlot slot = new DoctorShiftSlot();
                slot.setId(rs.getInt("id"));
                slot.setDoctorId(rs.getInt("doctor_id"));
                slot.setDate(LocalDate.parse(rs.getString("appointment_date")));
                slot.setSlotStartTime(rs.getString("appointment_hour"));
                slot.setIsBooked(false);
                list.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> getAllAppointmentDates() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT appointment_date FROM AppointmentSchedule ORDER BY appointment_date";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String date = rs.getString("appointment_date");
                LocalDate parsedDate = LocalDate.parse(date);
                list.add(parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> getAllAppointmentHours() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT appointment_hour FROM AppointmentSchedule ORDER BY appointment_hour";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String hour = rs.getString("appointment_hour");
                list.add(LocalTime.parse(hour).format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<AppointmentSchedule> searchHistoryAppointmentsByName(String name) {
        List<AppointmentSchedule> resultList = new ArrayList<>();
        String sql = """
        SELECT app.id, app.confirmationStatus,
               pa.fullName AS patientName,
               dt.fullName AS doctorName,
               shift.date AS shiftDate,
               shift.slotStartTime
        FROM appointmentSchedule app
        JOIN patient pa ON app.patientId = pa.id
        JOIN doctor dt ON app.doctorId = dt.id
        JOIN doctorShiftSlot shift ON app.doctorShiftId = shift.id
        WHERE (dt.fullName LIKE ? OR pa.fullName LIKE ?)
          AND app.confirmationStatus IN (N'Done', N'Cancel')
        ORDER BY app.id ASC
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String kw = "%" + name.trim() + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppointmentSchedule ap = new AppointmentSchedule();
                ap.setId(rs.getInt("id"));
                ap.setConfirmationStatus(rs.getString("confirmationStatus"));

                Doctor doctor = new Doctor();
                doctor.setFullName(rs.getString("doctorName"));
                ap.setDoctor(doctor);

                Patient patient = new Patient();
                patient.setFullName(rs.getString("patientName"));
                ap.setPatient(patient);

                DoctorShiftSlot shift = new DoctorShiftSlot();
                shift.setDate(rs.getDate("shiftDate").toLocalDate());
                shift.setSlotStartTime(rs.getString("slotStartTime"));
                ap.setShiftSlot(shift);

                resultList.add(ap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public boolean updateAppointment(AppointmentSchedule ap) {
        String sql = """
        UPDATE appointmentSchedule
        SET doctorId = ?, appointment_date = ?, appointment_hour = ?, confirmationStatus = ?
        WHERE id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, ap.getDoctor().getId());
            ps.setString(2, ap.getAppointment_date());
            ps.setString(3, ap.getAppointment_hour());
            ps.setString(4, ap.getConfirmationStatus());
            ps.setInt(5, ap.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        ReceptionDAO dao = new ReceptionDAO();
        ArrayList<AppointmentSchedule> list = dao.getAllSchedules();
        for (AppointmentSchedule app : list) {
            System.out.println(app);
        }
    }
}
