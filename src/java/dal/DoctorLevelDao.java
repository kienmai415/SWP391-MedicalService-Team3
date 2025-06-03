/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author maiki
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DoctorLevel;

public class DoctorLevelDao extends DBContext{
    public List<DoctorLevel> getAll() {
        List<DoctorLevel> list = new ArrayList<>();
        String sql = "SELECT id, level_name, examination_fee FROM doctor_levels";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DoctorLevel d = new DoctorLevel();
                d.setId(rs.getInt("id"));
                d.setLevelName(rs.getString("level_name"));
                d.setExaminationFee(rs.getDouble("examination_fee"));
                list.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

