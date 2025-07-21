package dal;

import model.Doctor;
import model.Specialization;
import model.DoctorLevel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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

                p = new Doctor(
                        rs.getInt("id"),
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
        return p;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = """
            SELECT d.id, d.fullName, d.email, d.phoneNumber,
                   s.id AS spec_id, s.name AS spec_name,
                   dl.id AS level_id, dl.name AS level_name, dl.examinationFee
            FROM doctor d
            JOIN specialization s ON d.specializationId = s.id
            JOIN doctorLevel dl ON d.doctorLevelId = dl.id
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("id"));
                d.setFullName(rs.getString("fullName"));
                d.setEmail(rs.getString("email"));
                d.setPhoneNumber(rs.getString("phoneNumber"));

                Specialization spec = new Specialization();
                spec.setId(rs.getInt("spec_id"));
                spec.setName(rs.getString("spec_name"));

                DoctorLevel level = new DoctorLevel();
                level.setId(rs.getInt("level_id"));
                level.setName(rs.getString("level_name"));
                level.setExaminationFee(rs.getDouble("examinationFee"));

                d.setSpecialization(spec);
                d.setDoctorLevel(level);

                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Doctor getDoctorNameById(int id) {
        String sql = "SELECT fullName FROM doctor WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Doctor d = new Doctor();
                d.setFullName(rs.getString("fullName")); // ✅ Chỉ cần tên
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static void main(String[] args) {
        DoctorDAO dd = new DoctorDAO();
        Doctor d = dd.getDoctorByEmailPass("doctor1@hospital.com", "hashed_password6");
        System.out.println(d);

        // Test getAllDoctors
        List<Doctor> doctors = dd.getAllDoctors();
        for (Doctor doc : doctors) {
            System.out.println(doc.getFullName() + " - " + doc.getEmail());
        }
    }
}
