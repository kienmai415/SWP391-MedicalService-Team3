/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Doctor;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;

/**
 *
 * @author BB-MT
 */
public class DoctorDAO extends DBContext {

    public Doctor getDoctorByEmailPass(String email, String pass) {
        String sql = "SELECT * FROM dbo.doctor WHERE email = ? AND password = ?";
        Doctor p = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date sqlDate = rs.getDate("dateOfBirth");
                LocalDate dob = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                p = new Doctor(rs.getInt("id"), 
                        rs.getString("imageURL"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("fullName"),
                        dob,
                        rs.getString("gender"),
                        rs.getString("fullName"),
                        rs.getString("phoneNumber"),
                        rs.getBoolean("status")
                );
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (p);
    }
    public static void main(String[] args) {
        DoctorDAO dd = new DoctorDAO();
        Doctor d = dd.getDoctorByEmailPass("doctor1@hospital.com", "hashed_password6");
        System.out.println(d);
    }
}
