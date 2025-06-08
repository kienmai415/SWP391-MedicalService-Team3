 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Patient;
import java.sql.PreparedStatement;

/**
 *
 * @author BB-MT
 */
public class PatientDAO extends DBContext {

    public Patient getPatientByEmailPass(String email, String pass) {
        String sql = "SELECT * FROM dbo.patient WHERE email = ? AND password = ?";
        Patient p = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                p = new Patient(rs.getInt("id"), rs.getString("imageURL"),
                        rs.getString("address"),
                        rs.getDate("dateOfBirth").toLocalDate(),
                        rs.getString("fullName"), rs.getString("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("identityNumber"),
                        rs.getString("insuranceNumber"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("status") == 1,
                        rs.getString("role")
                );
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (p);
    }

    public Patient getPatientByEmail(String email) {
        String sql = "SELECT * FROM dbo.patient WHERE email = ?";
        Patient p = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                p = new Patient(rs.getInt("id"), rs.getString("imageURL"),
                        rs.getString("address"),
                        rs.getDate("dateOfBirth").toLocalDate(),
                        rs.getString("fullName"), rs.getString("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("identityNumber"),
                        rs.getString("insuranceNumber"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("status") == 1,
                        rs.getString("role")
                );
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (p);
    }

    public int addPatient(String name, String email, String pass, String phone, int status) {
        String sql = """
                     INSERT INTO dbo.patient
                     (
                         full_name,
                         phone_number,
                         email,
                         pass,
                         status,
                     )
                     VALUES(?,?,?,?,?)""";
        int isUpdated = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setNString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setInt(5, status);
            isUpdated = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return isUpdated;
    }

    public static void main(String[] args) {
        PatientDAO pd = new PatientDAO();
        String name = "Nguyen Van A1";
        String email = "vana1@gmail.com1";
        String pass = "123456";
        String phone = "0123456789";
        int status = 1;

        // Gọi hàm thêm
        if (pd.getPatientByEmail("patient1@email.com") == null) {
            int x = pd.addPatient(name, email, pass, phone, status);
        } else {
            System.out.println("no");
        }

        System.out.println(pd.getPatientByEmail("patient1@email.com"));
    }
}
