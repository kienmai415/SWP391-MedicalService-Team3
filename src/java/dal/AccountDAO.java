/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author maiki
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.Doctor;
import model.Patient;
import model.Specialization;
import model.DoctorLevel;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author maiki
 */
public class AccountDAO extends DBContext {

    public AccountDAO() {
        super(); // Gọi constructor của DBContext để khởi tạo connection
    }

    public List<Object> getAccounts(String email, String role, String status) {
        List<Object> accounts = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        // Chuẩn hóa role
        String normalizedRole = "all";
        if (role != null && !role.isEmpty()) {
            switch (role) {
                case "2":
                    normalizedRole = "Patient";
                    break;
                case "3":
                    normalizedRole = "Manager";
                    break;
                case "4":
                    normalizedRole = "Doctor";
                    break;
                case "5":
                    normalizedRole = "Receptionist";
                    break;
            }
        }

        // Chuẩn hóa status
        String normalizedStatus = "all";
        if (status != null && !status.isEmpty()) {
            normalizedStatus = status.equals("activated") ? "1" : status.equals("deactivated") ? "0" : "all";
        }

        // Xây dựng query dựa trên role và status
        if ("Manager".equals(normalizedRole) || "Receptionist".equals(normalizedRole)) {
            sql.append("SELECT id, username, email, role, status FROM users WHERE 1=1");
            if (!"all".equals(normalizedRole)) {
                sql.append(" AND role = ?");
                params.add(normalizedRole);
            }
            if (!"all".equals(normalizedStatus)) {
                if (params.isEmpty()) {
                    sql.append(" WHERE status = ?");
                } else {
                    sql.append(" AND status = ?");
                }
                params.add(Integer.parseInt(normalizedStatus));
            }
        } else if ("Doctor".equals(normalizedRole)) {
            sql.append("SELECT id, username, email, role, status FROM doctor WHERE role = 'Doctor'");
            if (!"all".equals(normalizedStatus)) {
                sql.append(" AND status = ?");
                params.add(Integer.parseInt(normalizedStatus));
            }
        } else if ("Patient".equals(normalizedRole)) {
            sql.append("SELECT id, username, email, role, status FROM patient WHERE role = 'Patient'");
            if (!"all".equals(normalizedStatus)) {
                sql.append(" AND status = ?");
                params.add(Integer.parseInt(normalizedStatus));
            }
        } else { // role is "all"
            sql.append("SELECT id, username, email, role, status FROM users WHERE role IN ('Manager', 'Receptionist')");
            if (!"all".equals(normalizedStatus)) {
                sql.append(" AND status = ?");
                params.add(Integer.parseInt(normalizedStatus));
            }
            sql.append(" UNION SELECT id, username, email, role, status FROM doctor WHERE role = 'Doctor'");
            if (!"all".equals(normalizedStatus)) {
                if (params.size() == 1) {
                    sql.append(" AND status = ?");
                } else {
                    sql.append(" WHERE status = ?");
                }
                params.add(Integer.parseInt(normalizedStatus));
            }
            sql.append(" UNION SELECT id, username, email, role, status FROM patient WHERE role = 'Patient'");
            if (!"all".equals(normalizedStatus)) {
                if (params.size() == 2) {
                    sql.append(" AND status = ?");
                } else {
                    sql.append(" WHERE status = ?");
                }
                params.add(Integer.parseInt(normalizedStatus));
            }
        }

        // Thêm điều kiện email
        if (email != null && !email.trim().isEmpty()) {
            if (params.isEmpty()) {
                sql.append(" WHERE email LIKE ?");
            } else {
                sql.append(" AND email LIKE ?");
            }
            params.add("%" + email + "%");
        }

        sql.append(" ORDER BY id");

        System.out.println("SQL Query: " + sql.toString());
        System.out.println("Parameters: " + params);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String accountRole = rs.getString("role");
                    if ("Manager".equals(accountRole) || "Receptionist".equals(accountRole)) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(accountRole);
                        user.setStatus(rs.getBoolean("status"));
                        accounts.add(user);
                    } else if ("Doctor".equals(accountRole)) {
                        Doctor doctor = new Doctor();
                        doctor.setId(rs.getInt("id"));
                        doctor.setUsername(rs.getString("username"));
                        doctor.setEmail(rs.getString("email"));
                        doctor.setRole(accountRole);
                        doctor.setStatus(rs.getBoolean("status"));
                        accounts.add(doctor);
                    } else if ("Patient".equals(accountRole)) {
                        Patient patient = new Patient();
                        patient.setId(rs.getInt("id"));
                        patient.setUserName(rs.getString("username"));
                        patient.setEmail(rs.getString("email"));
                        patient.setRole(accountRole);
                        patient.setStatus(rs.getBoolean("status"));
                        accounts.add(patient);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }
        return accounts;
    }

    public int getTotalAccounts(String email, String role, String status) {
        int total = 0;
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        // Chuẩn hóa role
        if (role != null && !role.isEmpty()) {
            switch (role) {
                case "2":
                    role = "Patient";
                    break;
                case "3":
                    role = "Manager";
                    break;
                case "4":
                    role = "Doctor";
                    break;
                case "5":
                    role = "Receptionist";
                    break;
                default:
                    role = "all";
            }
        } else {
            role = "all";
        }

        // Chuẩn hóa status
        if (status != null && !status.isEmpty()) {
            status = status.equals("activated") ? "1" : status.equals("deactivated") ? "0" : "all";
        } else {
            status = "all";
        }

        sql.append("SELECT COUNT(*) FROM (");
        sql.append("  SELECT id FROM users WHERE role IN ('Manager', 'Receptionist')");
        if (!role.equals("all") && (role.equals("Manager") || role.equals("Receptionist"))) {
            sql.append(" AND role = ?");
            params.add(role);
        }
        if (!status.equals("all")) {
            sql.append(" AND status = ?");
            params.add(Integer.parseInt(status));
        }
        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + email + "%");
        }

        sql.append(" UNION ");
        sql.append("  SELECT id FROM doctor WHERE role = 'Doctor'");
        if (!role.equals("all") && role.equals("Doctor")) {
            sql.append(" AND role = ?");
            params.add(role);
        }
        if (!status.equals("all")) {
            sql.append(" AND status = ?");
            params.add(Integer.parseInt(status));
        }
        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + email + "%");
        }

        sql.append(" UNION ");
        sql.append("  SELECT id FROM patient WHERE role = 'Patient'");
        if (!role.equals("all") && role.equals("Patient")) {
            sql.append(" AND role = ?");
            params.add(role);
        }
        if (!status.equals("all")) {
            sql.append(" AND status = ?");
            params.add(Integer.parseInt(status));
        }
        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + email + "%");
        }
        sql.append(") AS accounts");

        System.out.println("SQL Count Query: " + sql.toString());
        System.out.println("Count Parameters: " + params);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm số lượng tài khoản: " + e.getMessage());
        }
        return total;
    }

    // Cập nhật trạng thái tài khoản theo email
    public Map<String, Object> updateAccountStatus(String email, int status) {
        Map<String, Object> result = new HashMap<>();
        String sql = "UPDATE users SET status = ? WHERE email = ?";
        try (Connection conn = super.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, status); // 0 cho vô hiệu hóa, 1 cho kích hoạt
                ps.setString(2, email);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    result.put("success", true);
                    result.put("message", status == 0 ? "Tài khoản đã được vô hiệu hóa." : "Tài khoản đã được kích hoạt.");
                    result.put("messageType", "success");
                    // Cập nhật bảng doctor nếu email tồn tại
                    sql = "UPDATE doctor SET status = ? WHERE email = ?";
                    try (PreparedStatement psDoctor = conn.prepareStatement(sql)) {
                        psDoctor.setInt(1, status);
                        psDoctor.setString(2, email);
                        psDoctor.executeUpdate(); // Không cần kiểm tra rowsAffected
                    }
                    // Cập nhật bảng patient nếu email tồn tại
                    sql = "UPDATE patient SET status = ? WHERE email = ?";
                    try (PreparedStatement psPatient = conn.prepareStatement(sql)) {
                        psPatient.setInt(1, status);
                        psPatient.setString(2, email);
                        psPatient.executeUpdate(); // Không cần kiểm tra rowsAffected
                    }
                } else {
                    result.put("success", false);
                    result.put("message", "Không tìm thấy tài khoản để cập nhật trạng thái.");
                    result.put("messageType", "danger");
                }
                conn.commit(); // Đảm bảo commit thay đổi
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật trạng thái tài khoản: " + e.getMessage());
            result.put("success", false);
            result.put("message", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
            result.put("messageType", "danger");
        }
        return result;
    }

    // Lấy thông tin cơ bản của tài khoản dựa trên email
    public Object getAccountByEmail(String email) {
        String sql = "SELECT TOP 1 id, username, email, role, status FROM users WHERE email = ? "
                + "UNION "
                + "SELECT TOP 1 id, username, email, role, status FROM doctor WHERE email = ? "
                + "UNION "
                + "SELECT TOP 1 id, username, email, role, status FROM patient WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, email);
            ps.setString(3, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    if ("Manager".equals(role) || "Receptionist".equals(role)) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(role);
                        user.setStatus(rs.getBoolean("status"));
                        return user;
                    } else if ("Doctor".equals(role)) {
                        Doctor doctor = new Doctor();
                        doctor.setId(rs.getInt("id"));
                        doctor.setUsername(rs.getString("username"));
                        doctor.setEmail(rs.getString("email"));
                        doctor.setRole(role);
                        doctor.setStatus(rs.getBoolean("status"));
                        return doctor;
                    } else if ("Patient".equals(role)) {
                        Patient patient = new Patient();
                        patient.setId(rs.getInt("id"));
                        patient.setUserName(rs.getString("username"));
                        patient.setEmail(rs.getString("email"));
                        patient.setRole(role);
                        patient.setStatus(rs.getBoolean("status"));
                        return patient;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy thông tin tài khoản bằng email: " + e.getMessage());
        }
        return null;
    }

    // Lấy chi tiết của tài khoản dựa trên email
    public Object getAccountDetailByEmail(String email) {
        Object account = getAccountByEmail(email); // Lấy vai trò từ getAccountByEmail
        String role = null;
        if (account != null) {
            if (account instanceof User) {
                role = ((User) account).getRole();
            } else if (account instanceof Doctor) {
                role = "Doctor";
            } else if (account instanceof Patient) {
                role = "Patient";
            }
        }
        String sql = "";
        try {
            System.out.println("Using role from getAccountByEmail for email " + email + ": " + role);
            if (role == null) {
                System.out.println("No role detected for email " + email + ", skipping detail retrieval");
                return null;
            }
            if ("Manager".equals(role) || "Receptionist".equals(role)) {
                sql = "SELECT fullName, address, dateOfBirth, gender, phoneNumber FROM users WHERE email = ?";
                System.out.println("Executing detail query for User/Manager/Receptionist: " + sql);
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, email);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            User user = (User) account; // Sử dụng đối tượng đã lấy từ getAccountByEmail
                            user.setFullName(rs.getString("fullName"));
                            user.setAddress(rs.getString("address"));
                            java.sql.Date sqlDate = rs.getDate("dateOfBirth");
                            user.setDob(sqlDate != null ? sqlDate.toLocalDate() : null);
                            user.setGender(rs.getString("gender"));
                            user.setPhoneNumber(rs.getString("phoneNumber"));
                            System.out.println("Detail retrieved for email " + email + ": FullName=" + user.getFullName() + ", Gender=" + user.getGender());
                            return user;
                        } else {
                            System.out.println("No detail found in users for email " + email);
                        }
                    }
                }
            } else if ("Doctor".equals(role)) {
                sql = "SELECT d.fullName, d.address, d.dateOfBirth, d.gender, d.phoneNumber, s.id AS specializationId, s.name AS specializationName, dl.id AS doctorLevelId, dl.name AS doctorLevelName "
                        + "FROM doctor d "
                        + "LEFT JOIN specialization s ON d.specializationId = s.id "
                        + "LEFT JOIN doctorLevel dl ON d.doctorLevelId = dl.id "
                        + "WHERE d.email = ?";
                System.out.println("Executing detail query for Doctor: " + sql);
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, email);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Doctor doctor = (Doctor) account; // Sử dụng đối tượng đã lấy từ getAccountByEmail
                            doctor.setFullName(rs.getString("fullName"));
                            doctor.setAddress(rs.getString("address"));
                            java.sql.Date sqlDate = rs.getDate("dateOfBirth");
                            doctor.setDob(sqlDate != null ? sqlDate.toLocalDate() : null);
                            doctor.setGender(rs.getString("gender"));
                            doctor.setPhoneNumber(rs.getString("phoneNumber"));
                            doctor.setSpecialization(new Specialization(rs.getInt("specializationId"), rs.getString("specializationName")));
                            doctor.setDoctorLevel(new DoctorLevel(rs.getInt("doctorLevelId"), rs.getString("doctorLevelName")));
                            System.out.println("Detail retrieved for email " + email + ": FullName=" + doctor.getFullName() + ", Specialization=" + doctor.getSpecialization().getName());
                            return doctor;
                        } else {
                            System.out.println("No detail found in doctor for email " + email);
                        }
                    }
                }
            } else if ("Patient".equals(role)) {
                sql = "SELECT fullName, address, dateOfBirth, gender, phoneNumber, identityNumber, insuranceNumber FROM patient WHERE email = ?";
                System.out.println("Executing detail query for Patient: " + sql);
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, email);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Patient patient = (Patient) account; // Sử dụng đối tượng đã lấy từ getAccountByEmail
                            patient.setFullName(rs.getString("fullName"));
                            patient.setAddress(rs.getString("address"));
                            java.sql.Date sqlDate = rs.getDate("dateOfBirth");
                            patient.setDob(sqlDate != null ? sqlDate.toLocalDate() : null);
                            patient.setGender(rs.getString("gender"));
                            patient.setPhoneNumber(rs.getString("phoneNumber"));
                            patient.setIdentityNumber(rs.getString("identityNumber"));
                            patient.setInsuranceNumber(rs.getString("insuranceNumber"));
                            System.out.println("Detail retrieved for email " + email + ": FullName=" + patient.getFullName() + ", IdentityNumber=" + patient.getIdentityNumber());
                            return patient;
                        } else {
                            System.out.println("No detail found in patient for email " + email);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy chi tiết tài khoản bằng email: " + e.getMessage());
        }
        return null;
    }

    // Thêm phương thức để lấy danh sách specialization
    public List<Specialization> getSpecializations() {
        List<Specialization> specializations = new ArrayList<>();
        String sql = "SELECT id, name FROM specialization";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    specializations.add(new Specialization(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách chuyên khoa: " + e.getMessage());
        }
        return specializations;
    }

    // Thêm phương thức để lấy danh sách doctorLevel
    public List<DoctorLevel> getDoctorLevels() {
        List<DoctorLevel> doctorLevels = new ArrayList<>();
        String sql = "SELECT id, name FROM doctorLevel";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    doctorLevels.add(new DoctorLevel(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách trình độ: " + e.getMessage());
        }
        return doctorLevels;
    }

    // Thêm phương thức để thêm tài khoản
    public boolean addAccount(User user) {
        String sql = "INSERT INTO users (email, username, password, role, fullName, dateOfBirth, gender, address, phoneNumber, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            System.out.println("Executing SQL for User: " + sql);
            System.out.println("Parameters - Email: " + user.getEmail() + ", Username: " + user.getUsername()
                    + ", Password: " + user.getPassword() + ", Role: " + user.getRole()
                    + ", FullName: " + user.getFullName() + ", DOB: " + user.getDob()
                    + ", Gender: " + user.getGender() + ", Address: " + user.getAddress()
                    + ", Phone: " + user.getPhoneNumber() + ", Status: " + user.isStatus());
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setObject(5, user.getDob());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getPhoneNumber());
            ps.setBoolean(9, user.isStatus());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                        System.out.println("Successfully inserted User with ID: " + user.getId());
                    }
                }
                return true;
            } else {
                System.out.println("No rows affected for User insertion.");
            }
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm tài khoản User: " + e.getMessage());
            return false;
        }
    }

    public boolean addAccount(Doctor doctor) {
        String sql = "INSERT INTO doctor (email, username, password, role, fullName, dateOfBirth, gender, address, phoneNumber, specializationId, doctorLevelId, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            System.out.println("Executing SQL for Doctor: " + sql);
            System.out.println("Parameters - Email: " + doctor.getEmail() + ", Username: " + doctor.getUsername()
                    + ", Password: " + doctor.getPassword() + ", Role: " + doctor.getRole()
                    + ", FullName: " + doctor.getFullName() + ", DOB: " + doctor.getDob()
                    + ", Gender: " + doctor.getGender() + ", Address: " + doctor.getAddress()
                    + ", Phone: " + doctor.getPhoneNumber() + ", SpecializationId: " + doctor.getSpecialization().getId()
                    + ", DoctorLevelId: " + doctor.getDoctorLevel().getId() + ", Status: " + doctor.isStatus());
            ps.setString(1, doctor.getEmail());
            ps.setString(2, doctor.getUsername());
            ps.setString(3, doctor.getPassword());
            ps.setString(4, doctor.getRole());
            ps.setObject(5, doctor.getDob());
            ps.setString(6, doctor.getGender());
            ps.setString(7, doctor.getAddress());
            ps.setString(8, doctor.getPhoneNumber());
            ps.setInt(9, doctor.getSpecialization().getId());
            ps.setInt(10, doctor.getDoctorLevel().getId());
            ps.setBoolean(11, doctor.isStatus());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        doctor.setId(generatedKeys.getInt(1));
                        System.out.println("Successfully inserted Doctor with ID: " + doctor.getId());
                    }
                }
                return true;
            } else {
                System.out.println("No rows affected for Doctor insertion.");
            }
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm tài khoản Doctor: " + e.getMessage());
            return false;
        }
    }

    // Thêm phương thức để cập nhật tài khoản
    public boolean updateAccount(User user) {
        String sql = "UPDATE users SET email = ?, username = ?, password = ?, fullName = ?, dateOfBirth = ?, gender = ?, address = ?, phoneNumber = ? WHERE email = ?";
        try (Connection conn = super.getConnection()) {
            System.out.println("Executing SQL for User update: " + sql);
            System.out.println("Parameters - Original Email: " + user.getEmail() + ", New Email: " + user.getEmail() + ", Username: " + user.getUsername()
                    + ", Password: " + user.getPassword() + ", FullName: " + user.getFullName()
                    + ", DOB: " + user.getDob() + ", Gender: " + user.getGender()
                    + ", Address: " + user.getAddress() + ", Phone: " + user.getPhoneNumber());
            // Xác nhận bản ghi hiện tại dựa trên email
            Object originalAccount = getAccountByEmail(user.getEmail());
            if (originalAccount == null || !(originalAccount instanceof User)) {
                System.err.println("No matching account found for email: " + user.getEmail() + ". Update aborted.");
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.getEmail()); // New email
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getFullName());
                ps.setObject(5, user.getDob());
                ps.setString(6, user.getGender());
                ps.setString(7, user.getAddress());
                ps.setString(8, user.getPhoneNumber());
                ps.setString(9, ((User) originalAccount).getEmail()); // Original email to match
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("No rows updated for User with email: " + user.getEmail() + ". Possible constraint violation or no matching record.");
                    throw new SQLException("No rows updated. Check for duplicate emails or data integrity.");
                }
                conn.commit(); // Đảm bảo commit thay đổi
                System.out.println("Update successful for User with email: " + user.getEmail());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật tài khoản User: " + e.getMessage());
            if (e.getMessage().contains("duplicate key")) {
                System.err.println("Update failed: Email " + user.getEmail() + " already exists. Check for duplicate entries.");
            } else if (e.getMessage().contains("No rows updated")) {
                System.err.println("Update failed: No matching record found for email: " + user.getEmail() + ". Verify data.");
            }
            return false;
        }
    }

    public boolean updateAccount(Doctor doctor) {
        String sql = "UPDATE doctor SET email = ?, username = ?, password = ?, fullName = ?, dateOfBirth = ?, gender = ?, address = ?, phoneNumber = ?, specializationId = ?, doctorLevelId = ? WHERE email = ?";
        try (Connection conn = super.getConnection()) {
            System.out.println("Executing SQL for Doctor update: " + sql);
            System.out.println("Parameters - Original Email: " + doctor.getEmail() + ", New Email: " + doctor.getEmail() + ", Username: " + doctor.getUsername()
                    + ", Password: " + doctor.getPassword() + ", FullName: " + doctor.getFullName()
                    + ", DOB: " + doctor.getDob() + ", Gender: " + doctor.getGender()
                    + ", Address: " + doctor.getAddress() + ", Phone: " + doctor.getPhoneNumber()
                    + ", SpecializationId: " + doctor.getSpecialization().getId()
                    + ", DoctorLevelId: " + doctor.getDoctorLevel().getId());
            // Xác nhận bản ghi hiện tại dựa trên email
            Object originalAccount = getAccountByEmail(doctor.getEmail());
            if (originalAccount == null || !(originalAccount instanceof Doctor)) {
                System.err.println("No matching account found for email: " + doctor.getEmail() + ". Update aborted.");
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, doctor.getEmail()); // New email
                ps.setString(2, doctor.getUsername());
                ps.setString(3, doctor.getPassword());
                ps.setString(4, doctor.getFullName());
                ps.setObject(5, doctor.getDob());
                ps.setString(6, doctor.getGender());
                ps.setString(7, doctor.getAddress());
                ps.setString(8, doctor.getPhoneNumber());
                ps.setInt(9, doctor.getSpecialization().getId());
                ps.setInt(10, doctor.getDoctorLevel().getId());
                ps.setString(11, ((Doctor) originalAccount).getEmail()); // Original email to match
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("No rows updated for Doctor with email: " + doctor.getEmail() + ". Possible constraint violation or no matching record.");
                    throw new SQLException("No rows updated. Check for duplicate emails or data integrity.");
                }
                conn.commit(); // Đảm bảo commit thay đổi
                System.out.println("Update successful for Doctor with email: " + doctor.getEmail());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật tài khoản Doctor: " + e.getMessage());
            if (e.getMessage().contains("duplicate key")) {
                System.err.println("Update failed: Email " + doctor.getEmail() + " already exists. Check for duplicate entries.");
            } else if (e.getMessage().contains("No rows updated")) {
                System.err.println("Update failed: No matching record found for email: " + doctor.getEmail() + ". Verify data.");
            }
            return false;
        }
    }

    // Thêm hàm main để test
    public static void main(String[] args) {
        // Khởi tạo AccountDAO
        AccountDAO accountDAO = new AccountDAO();

        // Test cập nhật User
        System.out.println("Testing User update...");
        User user = new User();
        String originalUserEmail = "patient1@email.com"; // Email cũ tồn tại trong DB
        user.setEmail(originalUserEmail);
        user.setUsername("khainh2");
        user.setPassword("khai123!");
        user.setFullName("Nguyễn Hữu Khải");
        user.setDob(LocalDate.of(1990, 1, 1)); // Sử dụng LocalDate
        user.setGender("Nam");
        user.setAddress("123 New Street");
        user.setPhoneNumber("0901658354");

        // Kiểm tra kết nối trước
        try (Connection conn = accountDAO.super.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Database connection is closed or null. Cannot proceed with test. Connection state: " + (conn != null && !conn.isClosed() ? "Open" : "Closed"));
                return;
            }
            System.out.println("Database connection is open. Proceeding with test...");
        } catch (SQLException e) {
            System.err.println("Failed to check connection: " + e.getMessage());
            return;
        }

        // Kiểm tra trước khi cập nhật
        Object existingUser = accountDAO.getAccountByEmail(originalUserEmail);
        if (existingUser == null || !(existingUser instanceof User)) {
            System.err.println("No User found with email: " + originalUserEmail + ". Please insert a record first. Connection state: " + (accountDAO.getConnection() != null && !accountDAO.getConnection().isClosed() ? "Open" : "Closed"));
            // Thêm bản ghi mẫu nếu cần (tùy chọn)
            User sampleUser = new User();
            sampleUser.setEmail("patient1@email.com");
            sampleUser.setUsername("oldusername");
            sampleUser.setPassword("OldPass123!");
            sampleUser.setFullName("Old User");
            sampleUser.setDob(LocalDate.of(1990, 1, 1));
            sampleUser.setGender("Nam");
            sampleUser.setAddress("Old Address");
            sampleUser.setPhoneNumber("0901111111");
            boolean addSuccess = accountDAO.addAccount(sampleUser); // Giả sử addAccount đã được triển khai
            if (addSuccess) {
                System.out.println("Sample User record inserted successfully.");
                existingUser = sampleUser;
            } else {
                System.err.println("Failed to insert sample User record. Check DB configuration.");
                return;
            }
        } else {
            System.out.println("Found User with email: " + originalUserEmail + ". Proceeding with update...");
        }

        String newUserEmail = "newpatient@email.com"; // Email mới
        user.setEmail(newUserEmail); // Cập nhật email trong đối tượng
        boolean userSuccess = accountDAO.updateAccount(user);
        System.out.println("User update result: " + (userSuccess ? "Success" : "Failed"));
        if (!userSuccess) {
            System.err.println("User update failed. Check DAO logs for details.");
        }

        // Test cập nhật Doctor
        System.out.println("\nTesting Doctor update...");
        Doctor doctor = new Doctor();
        String originalDoctorEmail = "doctor3@hospital.com"; // Email cũ tồn tại trong DB
        doctor.setEmail(originalDoctorEmail);
        doctor.setUsername("cuongmt4");
        doctor.setPassword("cuong234!");
        doctor.setFullName("Mạc Thái Cường");
        doctor.setDob(LocalDate.of(1985, 5, 15)); // Sử dụng LocalDate
        doctor.setGender("Nam");
        doctor.setAddress("hhhhhhhhhhhhh");
        doctor.setPhoneNumber("0915367946");
        doctor.setSpecialization(new Specialization(1, "Cardiology")); // Giả sử ID 1 tồn tại
        doctor.setDoctorLevel(new DoctorLevel(1, "Senior")); // Giả sử ID 1 tồn tại

        // Kiểm tra kết nối trước
        try (Connection conn = accountDAO.super.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Database connection is closed or null. Cannot proceed with test. Connection state: " + (conn != null && !conn.isClosed() ? "Open" : "Closed"));
                return;
            }
            System.out.println("Database connection is open. Proceeding with test...");
        } catch (SQLException e) {
            System.err.println("Failed to check connection: " + e.getMessage());
            return;
        }

        // Kiểm tra trước khi cập nhật
        Object existingDoctor = accountDAO.getAccountByEmail(originalDoctorEmail);
        if (existingDoctor == null || !(existingDoctor instanceof Doctor)) {
            System.err.println("No Doctor found with email: " + originalDoctorEmail + ". Please insert a record first. Connection state: " + (accountDAO.getConnection() != null && !accountDAO.getConnection().isClosed() ? "Open" : "Closed"));
            // Thêm bản ghi mẫu nếu cần (tùy chọn)
            Doctor sampleDoctor = new Doctor();
            sampleDoctor.setEmail("doctor3@hospital.com");
            sampleDoctor.setUsername("olddoctorname");
            sampleDoctor.setPassword("OldDocPass123!");
            sampleDoctor.setFullName("Old Doctor");
            sampleDoctor.setDob(LocalDate.of(1985, 5, 15));
            sampleDoctor.setGender("Nam");
            sampleDoctor.setAddress("Old Doctor Address");
            sampleDoctor.setPhoneNumber("0911111111");
            sampleDoctor.setSpecialization(new Specialization(1, "Cardiology"));
            sampleDoctor.setDoctorLevel(new DoctorLevel(1, "Senior"));
            boolean addSuccess = accountDAO.addAccount(sampleDoctor); // Giả sử addAccount đã được triển khai
            if (addSuccess) {
                System.out.println("Sample Doctor record inserted successfully.");
                existingDoctor = sampleDoctor;
            } else {
                System.err.println("Failed to insert sample Doctor record. Check DB configuration.");
                return;
            }
        } else {
            System.out.println("Found Doctor with email: " + originalDoctorEmail + ". Proceeding with update...");
        }

        String newDoctorEmail = "newdoctor3@hospital.com"; // Email mới
        doctor.setEmail(newDoctorEmail); // Cập nhật email trong đối tượng
        boolean doctorSuccess = accountDAO.updateAccount(doctor);
        System.out.println("Doctor update result: " + (doctorSuccess ? "Success" : "Failed"));
        if (!doctorSuccess) {
            System.err.println("Doctor update failed. Check DAO logs for details.");
        }

        // Kết quả tổng quát
        if (userSuccess || doctorSuccess) {
            System.out.println("Test completed. Check database to verify updates.");
        } else {
            System.out.println("Test failed. Check logs for errors.");
        }
    }
}
