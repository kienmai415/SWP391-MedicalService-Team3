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
public class AppointmentScheduleDAO extends DBContext {

    public ArrayList<AppointmentSchedule> getAllSchedules() {
        ArrayList<AppointmentSchedule> listAp = new ArrayList<>();
        String sql = """
        	select 
        	app.id ,
        	ds.date ,
        	ds.slotStartTime ,
        	pa.fullName as patientName , 
        	dt.fullName as  doctorName,
        	app.confirmationStatus  
                     
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
                String apConfirm = rs.getString("confirmationStatus"); // đảm bảo đúng tên cột trong DB

//                int doctorId = rs.getInt("doctorId");
                String doctorName = rs.getString("doctorName");
                Doctor apDoctor = new Doctor();
                apDoctor.setFullName(doctorName);
//                apDoctor.setId(doctorId);

//                int patientId = rs.getInt("patientId");
                String patientName = rs.getString("patientName");
                Patient apPatient = new Patient();
                apPatient.setFullName(patientName);
//                apPatient.setId(patientId);
//                int slotId = rs.getInt("doctorShiftId");

                DoctorShiftSlot apSlot = new DoctorShiftSlot();
//                apSlot.setId(slotId);
                String slotStartTime = rs.getString("slotStartTime");
                apSlot.setSlotStartTime(slotStartTime);
                LocalDate date = rs.getDate("date").toLocalDate();
                apSlot.setDate(date);
// Tạo đối tượng Appointment (hoặc AppointmentSchedule nếu đúng tên)
                AppointmentSchedule app = new AppointmentSchedule(apId, apConfirm, apDoctor, apPatient, apSlot);

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
                   SELECT app.id, ds.date, ds.slotStartTime, app.confirmationStatus,
                                    pa.fullName AS patientName, pa.email AS patientEmail, pa.phoneNumber AS patientPhone,
                                    pa.gender AS patientGender, pa.address AS patientAddress,
                                    dt.fullName AS doctorName, dt.email AS doctorEmail, dt.phoneNumber AS doctorPhone,
                                    dt.gender AS doctorGender, dt.address AS doctorAddress
                             FROM appointmentSchedule app
                             JOIN doctorShiftSlot ds ON app.doctorShiftId = ds.id
                             JOIN patient pa ON app.patientId = pa.id
                             JOIN doctor dt ON app.doctorId = dt.id
                             WHERE app.id = ?
                     """;
        try {
            PreparedStatement preparedStatement = dbc.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {

                AppointmentSchedule ap = new AppointmentSchedule();
                ap.setId(rs.getInt("id"));
                ap.setConfirmationStatus(rs.getString("confirmationStatus"));

                DoctorShiftSlot shift = new DoctorShiftSlot();
                shift.setDate(rs.getDate("date").toLocalDate());

                LocalTime time = rs.getTime("slotStartTime").toLocalTime();
                String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
                shift.setSlotStartTime(formattedTime);
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
            return null;
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

    public ArrayList<AppointmentSchedule> get(String sql) {
        ArrayList<AppointmentSchedule> listAp = new ArrayList<>();
        DBContext dbc = DBContext.getInstance();
        try {
            PreparedStatement preparedStatement = dbc.connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int apId = rs.getInt("id");
                String apConfirm = rs.getString("confirmationStatus"); // đảm bảo đúng tên cột trong DB

                int doctorId = rs.getInt("doctorId");
                Doctor apDoctor = new Doctor();
                apDoctor.setId(doctorId);

                int patientId = rs.getInt("patientId");
                Patient apPatient = new Patient();
                apPatient.setId(patientId);

                int slotId = rs.getInt("doctorShiftId");
                DoctorShiftSlot apSlot = new DoctorShiftSlot();
                apSlot.setId(slotId);

                AppointmentSchedule app = new AppointmentSchedule(apId, apConfirm, apDoctor, apPatient, apSlot);
                listAp.add(app);
            }
        } catch (Exception e) {
            return null;
        }
        return listAp.isEmpty() ? null : listAp;
    }

    public AppointmentSchedule addAppointmentSchedules(AppointmentSchedule APP) {
        DBContext dbc = DBContext.getInstance();
        int rs = 0;
        String sql = """
                        INSERT INTO [dbo].[appointmentSchedule]
                                                         ([patientId]
                                                         ,[doctorId]
                                                         ,[doctorShiftId]
                                                         ,[confirmationStatus])
                                                   VALUES
                                                         (?
                                                         ,?
                                                         ,?
                                                         ,?)
                     """;
        try {
            PreparedStatement statement = dbc.getConnection().prepareStatement(sql);

            statement.setInt(1, APP.getPatient().getId());
            statement.setInt(2, APP.getDoctor().getId());
            statement.setInt(3, APP.getShiftSlot().getId());
            statement.setString(4, APP.getConfirmationStatus());

            rs = statement.executeUpdate();
        } catch (Exception e) {
            return null;
        }
        if (rs == 0) {
            return null;
        } else {
            return APP;
        }
    }

    public AppointmentSchedule deleteAppointmentSchedules(AppointmentSchedule APP) {
        DBContext dbc = DBContext.getInstance();
        int rs = 0;
        String sql = """
                       DELETE FROM [dbo].[appointmentSchedule]
                             WHERE id = ?
                     """;
        try {
            PreparedStatement statement = dbc.getConnection().prepareStatement(sql);

            statement.setInt(1, APP.getId());

            rs = statement.executeUpdate();
        } catch (Exception e) {
            return null;
        }
        if (rs == 0) {
            return null;
        } else {
            return APP;
        }
    }

    public AppointmentSchedule updateAppointmentSchedules(AppointmentSchedule APP) {
        DBContext dbc = DBContext.getInstance();
        int rs = 0;
        String sql = """           
                       UPDATE [dbo].[appointmentSchedule]
                                SET [patientId] = ?
                                   ,[doctorId] = ?
                                   ,[doctorShiftId] = ?
                                   ,[confirmationStatus] = ?
                        WHERE id = ?
                     """;
        try {
            PreparedStatement statement = dbc.getConnection().prepareStatement(sql);
            statement.setInt(1, APP.getPatient().getId());
            statement.setInt(2, APP.getDoctor().getId());
            statement.setInt(3, APP.getShiftSlot().getId());
            statement.setString(4, APP.getConfirmationStatus());
            statement.setInt(5, APP.getId());

            rs = statement.executeUpdate();
        } catch (Exception e) {
            return null;
        }
        if (rs == 0) {
            return null;
        } else {
            return APP;
        }
    }

    public static void main(String[] args) {
        AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
        ArrayList<AppointmentSchedule> list = dao.getAllSchedules();
        // System.out.println( dao.insertAppointmentSchedules(new AppointmentSchedule( 0, java.sql.Date.valueOf("2025-05-30"), "08:00:00", "08:30:00", 3, 2, 1, "pending")));

//test insert
//        AppointmentSchedule inserted = dao.addAppointmentSchedules(
//                new AppointmentSchedule(
//                        2,
//                        java.sql.Date.valueOf("2025-05-30"),
//                        "08:00:00",
//                        "08:30:00",
//                        1,
//                        1,
//                        1,
//                        "Pending" 
//                )
//        );
//
//        if (inserted != null) {
//            System.out.println("Insert thành công: " + inserted);
//        } else {
//            System.out.println("Insert thất bại!");
//        }
//test update
//        AppointmentSchedule updated = dao.updateAppointmentSchedules(
//                new AppointmentSchedule(
//                        12, // ID của bản ghi cần update
//                        java.sql.Date.valueOf("2025-06-01"), // đổi ngày hẹn
//                        "09:00:00", // đổi giờ bắt đầu
//                        "09:30:00", // đổi giờ kết thúc
//                        1, // giữ nguyên patient_id
//                        1, // giữ nguyên doctor_id
//                        1, // giữ nguyên slot
//                        "Accepted" // đổi trạng thái
//                )
//        );
//
//        if (updated != null) {
//            System.out.println("✅ Update thành công: " + updated);
//        } else {
//            System.out.println("❌ Update thất bại!");
//        }
//test delete
//        AppointmentSchedule deleted = dao.deleteAppointmentSchedules(
//                new AppointmentSchedule(
//                        12, // ID cần xóa
//                        null, null, null,
//                        0, 0, 0,
//                        null
//                )
//        );
//
//        if (deleted != null) {
//            System.out.println("🗑️ Delete thành công: " + deleted);
//        } else {
//            System.out.println("❌ Delete thất bại!");
//        }
        for (AppointmentSchedule app : list) {
            System.out.println(app);
        }
    }

}
