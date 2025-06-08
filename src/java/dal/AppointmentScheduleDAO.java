/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        	app.confirmationStatus ,
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
        String sql = "select * from appointmentSchedule WHERE id = ?";
        try {
            PreparedStatement preparedStatement = dbc.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
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

                return new AppointmentSchedule(apId, apConfirm, apDoctor, apPatient, apSlot);

            }
        } catch (Exception e) {
            return null;
        }
        return null;
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
