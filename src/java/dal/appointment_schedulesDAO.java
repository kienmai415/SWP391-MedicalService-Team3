/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import model.appointment_schedules;

/**
 *
 * @author MinhQuang
 */
public class appointment_schedulesDAO extends DBContext {

    public ArrayList<appointment_schedules> getAllSchedules() {
        ArrayList<appointment_schedules> listAp = new ArrayList<>();
        String sql = """
        SELECT * from appointment_schedules
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ApId = rs.getInt("id");
                Date ApDate = rs.getDate("date_appointment");
                String ApSTime = rs.getString("start_time");
                String ApEtime = rs.getString("end_time");
                int ApPatient = rs.getInt("patient_id");
                int ApDoctor = rs.getInt("doctor_id");
                Integer ApSlotId = rs.getInt("appointment_slot_id");
                String ApConfirm = rs.getString("confirmation_status");

                appointment_schedules app = new appointment_schedules(ApId, ApDate, ApSTime, ApEtime, ApPatient, ApDoctor, ApSlotId, ApConfirm);
                listAp.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAp;
    }

    public appointment_schedules getAppointmentSchedulesById(int id) {
        DBContext dbc = DBContext.getInstance();
        String sql = "SELECT * FROM appointment_schedules WHERE id = ?";
        try {
            PreparedStatement preparedStatement = dbc.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int ApId = rs.getInt("id");
                Date ApDate = rs.getDate("date_appointment");
                String ApSTime = rs.getString("start_time");
                String ApEtime = rs.getString("end_time");
                int ApPatient = rs.getInt("patient_id");
                int ApDoctor = rs.getInt("doctor_id");
                Integer ApSlotId = rs.getInt("appointment_slot_id");
                String ApConfirm = rs.getString("confirmation_status");

                return new appointment_schedules(ApId, ApDate, ApSTime, ApEtime, ApPatient, ApDoctor, ApSlotId, ApConfirm);

            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ArrayList<appointment_schedules> get(String sql) {
        ArrayList<appointment_schedules> listAp = new ArrayList<>();
        DBContext dbc = DBContext.getInstance();
        try {
            PreparedStatement preparedStatement = dbc.connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int ApId = rs.getInt("id");
                Date ApDate = rs.getDate("date_appointment");
                String ApSTime = rs.getString("start_time");
                String ApEtime = rs.getString("end_time");
                int ApPatient = rs.getInt("patient_id");
                int ApDoctor = rs.getInt("doctor_id");
                Integer ApSlotId = rs.getInt("appointment_slot_id");
                String ApConfirm = rs.getString("confirmation_status");

                appointment_schedules app = new appointment_schedules(ApId, ApDate, ApSTime, ApEtime, ApPatient, ApDoctor, ApSlotId, ApConfirm);
                listAp.add(app);
            }
        } catch (Exception e) {
            return null;
        }
        return listAp.isEmpty() ? null : listAp;
    }

    public appointment_schedules insertAppointmentSchedules(appointment_schedules APP) {
        DBContext dbc = DBContext.getInstance();
        int rs = 0;
        String sql = """
                        INSERT INTO [dbo].[appointment_schedules]
                                              ([date_appointment]
                                              ,[start_time]
                                              ,[end_time]
                                              ,[patient_id]
                                              ,[doctor_id]
                                              ,[appointment_slot_id]
                                              ,[confirmation_status])
                                        VALUES
                                              (?
                                              ,?
                                              ,?
                                              ,?
                                              ,?
                                              ,?
                                              ,?)
                     """;
        try {
            PreparedStatement statement = dbc.getConnection().prepareStatement(sql);

            statement.setDate(1, (java.sql.Date) APP.getDate_appointment());
            statement.setString(2, APP.getStart_time());
            statement.setString(3, APP.getEnd_time());
            statement.setInt(4, APP.getPatient_id());
            statement.setInt(5, APP.getDoctor_id());
            statement.setInt(6, APP.getAppointment_slot_id());
            statement.setString(7, APP.getConfirmation_status());

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

    public appointment_schedules deleteAppointmentSchedules(appointment_schedules APP) {
        DBContext dbc = DBContext.getInstance();
        int rs = 0;
        String sql = """
                       DELETE FROM [dbo].[appointment_schedules]
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

    public appointment_schedules updateAppointmentSchedules(appointment_schedules APP) {
        DBContext dbc = DBContext.getInstance();
        int rs = 0;
        String sql = """           
                       UPDATE [dbo].[appointment_schedules]
                          SET [date_appointment] = ?
                             ,[start_time] = ?
                             ,[end_time] = ?
                             ,[patient_id] = ?
                             ,[doctor_id] = ?
                             ,[appointment_slot_id] = ?
                             ,[confirmation_status] = ?
                        WHERE id = ?
                     """;
        try {
            PreparedStatement statement = dbc.getConnection().prepareStatement(sql);

            statement.setDate(1, (java.sql.Date) APP.getDate_appointment());
            statement.setString(2, APP.getStart_time());
            statement.setString(3, APP.getEnd_time());
            statement.setInt(4, APP.getPatient_id());
            statement.setInt(5, APP.getDoctor_id());
            statement.setInt(6, APP.getAppointment_slot_id());
            statement.setString(7, APP.getConfirmation_status());
            statement.setInt(8, APP.getId());
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
        appointment_schedulesDAO dao = new appointment_schedulesDAO();
        ArrayList<appointment_schedules> list = dao.getAllSchedules();
        // System.out.println( dao.insertAppointmentSchedules(new appointment_schedules( 0, java.sql.Date.valueOf("2025-05-30"), "08:00:00", "08:30:00", 3, 2, 1, "pending")));

//test insert
//        appointment_schedules inserted = dao.insertAppointmentSchedules(
//                new appointment_schedules(
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
//        appointment_schedules updated = dao.updateAppointmentSchedules(
//                new appointment_schedules(
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
//        appointment_schedules deleted = dao.deleteAppointmentSchedules(
//                new appointment_schedules(
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

        for (appointment_schedules app : list) {
            System.out.println(app);
        }
    }

}
