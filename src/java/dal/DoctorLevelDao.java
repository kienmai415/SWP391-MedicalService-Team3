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
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String xSql = null;

    public DoctorLevelDao() {
        // Kế thừa connection từ DBContext
    }

    // Lấy tất cả trình độ bác sĩ
    public List<DoctorLevel> getAllDoctorLevels() {
        List<DoctorLevel> levels = new ArrayList<>();
        xSql = "SELECT id, name, examinationFee FROM doctorLevel";
        try {
            ps = connection.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DoctorLevel level = new DoctorLevel();
                level.setId(rs.getInt("id"));
                level.setName(rs.getString("name"));
                level.setExaminationFee(rs.getDouble("examinationFee"));
                levels.add(level);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all doctor levels: " + e.getMessage());
        } finally {
            closeResources();
        }
        return levels;
    }

    // Lấy trình độ bác sĩ theo ID
    public DoctorLevel getDoctorLevelsById(int id) {
        xSql = "SELECT id, name, examinationFee FROM doctorLevel WHERE id = ?";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                DoctorLevel level = new DoctorLevel();
                level.setId(rs.getInt("id"));
                level.setName(rs.getString("name"));
                level.setExaminationFee(rs.getDouble("examinationFee"));
                return level;
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching doctor level by id " + id + ": " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    // Thêm trình độ bác sĩ mới
    public boolean addDoctorLevels(DoctorLevel level) {
        xSql = "INSERT INTO doctorLevel (name, examinationFee) VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setString(1, level.getName());
            ps.setDouble(2, level.getExaminationFee());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while adding doctor level: " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    // Cập nhật trình độ bác sĩ
    public boolean updateDoctorLevels(DoctorLevel level) {
        xSql = "UPDATE doctorLevel SET name = ?, examinationFee = ? WHERE id = ?";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setString(1, level.getName());
            ps.setDouble(2, level.getExaminationFee());
            ps.setInt(3, level.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while updating doctor level with id " + level.getId() + ": " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    // Xóa trình độ bác sĩ
    public boolean deleteDoctorLevels(int id) {
        xSql = "DELETE FROM doctorLevel WHERE id = ?";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while deleting doctor level with id " + id + ": " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    // Đóng tài nguyên
    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        } catch (SQLException e) {
            System.err.println("Error while closing resources: " + e.getMessage());
        }
    }
}

