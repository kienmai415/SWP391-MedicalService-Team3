/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Specialization;
/**
 *
 * @author maiki
 */
public class SpecializationDao extends DBContext{
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String xSql = null;

    public SpecializationDao() {
        // Kế thừa connection từ DBContext
    }

    // Lấy tất cả chuyên khoa
    public List<Specialization> getAllSpecializations() {
        List<Specialization> specs = new ArrayList<>();
        xSql = "SELECT id, name, description FROM specialization";
        try {
            ps = connection.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Specialization spec = new Specialization();
                spec.setId(rs.getInt("id"));
                spec.setName(rs.getString("name"));
                spec.setDescription(rs.getString("description"));
                specs.add(spec);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all specializations: " + e.getMessage());
        } finally {
            closeResources();
        }
        return specs;
    }

    // Lấy chuyên khoa theo ID
    public Specialization getSpecializationsById(int id) {
        xSql = "SELECT id, name, description FROM specialization WHERE id = ?";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Specialization spec = new Specialization();
                spec.setId(rs.getInt("id"));
                spec.setName(rs.getString("name"));
                spec.setDescription(rs.getString("description"));
                return spec;
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching specialization by id " + id + ": " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    // Thêm chuyên khoa mới
    public boolean addSpecializations(Specialization spec) {
        xSql = "INSERT INTO specialization (name, description) VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setString(1, spec.getName());
            ps.setString(2, spec.getDescription());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while adding specialization: " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    // Cập nhật chuyên khoa
    public boolean updateSpecializations(Specialization spec) {
        xSql = "UPDATE specialization SET name = ?, description = ? WHERE id = ?";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setString(1, spec.getName());
            ps.setString(2, spec.getDescription());
            ps.setInt(3, spec.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while updating specialization with id " + spec.getId() + ": " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    // Xóa chuyên khoa
    public boolean deleteSpecializations(int id) {
        xSql = "DELETE FROM specialization WHERE id = ?";
        try {
            ps = connection.prepareStatement(xSql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error while deleting specialization with id " + id + ": " + e.getMessage());
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

