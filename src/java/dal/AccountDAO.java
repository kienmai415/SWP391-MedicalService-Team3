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

    // Cập nhật trạng thái tài khoản và trả về thông tin
    public Map<String, Object> updateAccountStatus(int id, int status) {
        Map<String, Object> result = new HashMap<>();
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, status); // 0 cho vô hiệu hóa, 1 cho kích hoạt
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result.put("success", true);
                result.put("message", status == 0 ? "Tài khoản đã được vô hiệu hóa." : "Tài khoản đã được kích hoạt.");
                result.put("messageType", "success");
                // Cập nhật bảng doctor nếu id tồn tại
                sql = "UPDATE doctor SET status = ? WHERE id = ?";
                try (PreparedStatement psDoctor = connection.prepareStatement(sql)) {
                    psDoctor.setInt(1, status);
                    psDoctor.setInt(2, id);
                    psDoctor.executeUpdate(); // Không cần kiểm tra rowsAffected
                }
                // Cập nhật bảng patient nếu id tồn tại
                sql = "UPDATE patient SET status = ? WHERE id = ?";
                try (PreparedStatement psPatient = connection.prepareStatement(sql)) {
                    psPatient.setInt(1, status);
                    psPatient.setInt(2, id);
                    psPatient.executeUpdate(); // Không cần kiểm tra rowsAffected
                }
            } else {
                result.put("success", false);
                result.put("message", "Không tìm thấy tài khoản để cập nhật.");
                result.put("messageType", "danger");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
            result.put("messageType", "danger");
        }
        return result;
    }

    // Lấy thông tin cơ bản của tài khoản dựa trên id
    public Object getAccountById(int id) {
        String sql = "SELECT id, username, email, role, status FROM users WHERE id = ? "
                + "UNION "
                + "SELECT id, username, email, role, status FROM doctor WHERE id = ? "
                + "UNION "
                + "SELECT id, username, email, role, status FROM patient WHERE id = ? "
                + "LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setInt(3, id);
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
            System.err.println("Lỗi khi lấy thông tin tài khoản: " + e.getMessage());
        }
        return null;
    }

    // Lấy chi tiết của tài khoản dựa trên id và role
    public Object getAccountDetailById(int id) {
        String sql = "";
        try {
            // Lấy role trước để xác định bảng chi tiết
            String roleSql = "SELECT role FROM users WHERE id = ? UNION SELECT role FROM doctor WHERE id = ? UNION SELECT role FROM patient WHERE id = ? LIMIT 1";
            String role = null;
            try (PreparedStatement rolePs = connection.prepareStatement(roleSql)) {
                rolePs.setInt(1, id);
                rolePs.setInt(2, id);
                rolePs.setInt(3, id);
                try (ResultSet rs = rolePs.executeQuery()) {
                    if (rs.next()) {
                        role = rs.getString("role");
                    }
                }
            }

            if ("Manager".equals(role) || "Receptionist".equals(role)) {
                sql = "SELECT fullName, address, dob, gender, phoneNumber FROM users WHERE id = ?";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            User user = new User();
                            user.setFullName(rs.getString("fullName"));
                            user.setAddress(rs.getString("address"));
                            java.sql.Date sqlDate = rs.getDate("dob");
                            user.setDob(sqlDate != null ? sqlDate.toLocalDate() : null); // Chuyển đổi sang LocalDate
                            user.setGender(rs.getString("gender"));
                            user.setPhoneNumber(rs.getString("phoneNumber"));
                            return user;
                        }
                    }
                }
            } else if ("Doctor".equals(role)) {
                sql = "SELECT d.fullName, d.address, d.dob, d.gender, d.phoneNumber, s.id AS specializationId, s.name AS specializationName, dl.id AS doctorLevelId, dl.name AS doctorLevelName "
                        + "FROM doctor d "
                        + "LEFT JOIN specialization s ON d.specializationId = s.id "
                        + "LEFT JOIN doctorLevel dl ON d.doctorLevelId = dl.id "
                        + "WHERE d.id = ?";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Doctor doctor = new Doctor();
                            doctor.setFullName(rs.getString("fullName"));
                            doctor.setAddress(rs.getString("address"));
                            java.sql.Date sqlDate = rs.getDate("dob");
                            doctor.setDob(sqlDate != null ? sqlDate.toLocalDate() : null); // Chuyển đổi sang LocalDate
                            doctor.setGender(rs.getString("gender"));
                            doctor.setPhoneNumber(rs.getString("phoneNumber"));
                            doctor.setSpecialization(new Specialization(rs.getInt("specializationId"), rs.getString("specializationName")));
                            doctor.setDoctorLevel(new DoctorLevel(rs.getInt("doctorLevelId"), rs.getString("doctorLevelName")));
                            return doctor;
                        }
                    }
                }
            } else if ("Patient".equals(role)) {
                sql = "SELECT fullName, address, dob, gender, phoneNumber, identityNumber, insuranceNumber FROM patient WHERE id = ?";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Patient patient = new Patient();
                            patient.setFullName(rs.getString("fullName"));
                            patient.setAddress(rs.getString("address"));
                            java.sql.Date sqlDate = rs.getDate("dob");
                            patient.setDob(sqlDate != null ? sqlDate.toLocalDate() : null); // Chuyển đổi sang LocalDate
                            patient.setGender(rs.getString("gender"));
                            patient.setPhoneNumber(rs.getString("phoneNumber"));
                            patient.setIdentityNumber(rs.getString("identityNumber"));
                            patient.setInsuranceNumber(rs.getString("insuranceNumber"));
                            return patient;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy chi tiết tài khoản: " + e.getMessage());
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
        String sql = "INSERT INTO users (email, username, password, role, fullName, dob, gender, address, phoneNumber, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(5, user.getFullName());
            ps.setObject(6, user.getDob());
            ps.setString(7, user.getGender());
            ps.setString(8, user.getAddress());
            ps.setString(9, user.getPhoneNumber());
            ps.setBoolean(10, user.isStatus());
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
        String sql = "INSERT INTO doctor (email, username, password, role, fullName, dob, gender, address, phoneNumber, specializationId, doctorLevelId, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(5, doctor.getFullName());
            ps.setObject(6, doctor.getDob());
            ps.setString(7, doctor.getGender());
            ps.setString(8, doctor.getAddress());
            ps.setString(9, doctor.getPhoneNumber());
            ps.setInt(10, doctor.getSpecialization().getId());
            ps.setInt(11, doctor.getDoctorLevel().getId());
            ps.setBoolean(12, doctor.isStatus());
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
    
    public User getUserByEmailPass(String email, String pass) {
        String sql = "SELECT * FROM dbo.users WHERE email = ? AND password = ?";
        User u = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date sqlDate = rs.getDate("dateOfBirth");
                LocalDate dob = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                u = new User(rs.getInt("id"), 
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
        return (u);
    }
   
    
    
    // Hàm main để test phương thức getAccounts, lấy tất cả tài khoản
//    public static void main(String[] args) {
//        AccountDAO accountDAO = new AccountDAO();
//
//        // Kiểm tra kết nối database
//        if (accountDAO.connection != null) {
//            System.out.println("Kết nối database thành công!");
//        } else {
//            System.out.println("Kết nối database thất bại!");
//            return;
//        }
//
//        try {
//            // Test phương thức getAccounts để lấy tất cả tài khoản
//            String email = null; // Không lọc theo email
//            String role = "all"; // Lấy tất cả vai trò
//            String status = "all"; // Lấy tất cả trạng thái
//
//            System.out.println("Test lấy tất cả tài khoản:");
//            List<Object> accounts = accountDAO.getAccounts(email, role, status);
//
//            if (accounts.isEmpty()) {
//                System.out.println("Không có tài khoản nào trong database.");
//            } else {
//                for (Object account : accounts) {
//                    if (account instanceof User) {
//                        User user = (User) account;
//                        System.out.printf("ID: %d, Username: %s, Email: %s, Role: %s, Status: %s%n",
//                                user.getId(), user.getUsername(), user.getEmail(), user.getRole(),
//                                user.isStatus() ? "Hoạt động" : "Vô hiệu hóa");
//                    } else if (account instanceof Doctor) {
//                        Doctor doctor = (Doctor) account;
//                        System.out.printf("ID: %d, Username: %s, Email: %s, Role: %s, Status: %s%n",
//                                doctor.getId(), doctor.getUsername(), doctor.getEmail(), doctor.getRole(),
//                                doctor.isStatus() ? "Hoạt động" : "Vô hiệu hóa");
//                    } else if (account instanceof Patient) {
//                        Patient patient = (Patient) account;
//                        System.out.printf("ID: %d, Username: %s, Email: %s, Role: %s, Status: %s%n",
//                                patient.getId(), patient.getUserName(), patient.getEmail(), patient.getRole(),
//                                patient.isStatus() ? "Hoạt động" : "Vô hiệu hóa");
//                    }
//                }
//            }
//
//            // Test số lượng tài khoản
//            int totalAccounts = accountDAO.getTotalAccounts(email, role, status);
//            System.out.println("Tổng số tài khoản: " + totalAccounts);
//
//        } catch (Exception e) {
//            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
//            e.printStackTrace();
//        }
//        
//    }
    
    public static void main(String[] args) {
        AccountDAO a = new AccountDAO();
        User u = a.getUserByEmailPass("admin@admin.com", "admin");
        System.out.println(u);
    }
}
