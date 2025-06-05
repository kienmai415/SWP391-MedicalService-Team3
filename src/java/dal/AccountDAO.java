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
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Doctor;
import model.DoctorLevel;
import model.Patient;
import model.Role;
import model.Specialization;
import model.Staff;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ADMIN
 */
public class AccountDAO extends DBContext {

    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String xSql = null;

    public AccountDAO() {
        // Kế thừa connection từ DBContext
    }

    // Kiểm tra email đã tồn tại
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM accounts WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Error while checking email existence: " + e.getMessage());
            return false;
        }
    }

    public Account getAccByEmail(String email) {
        Account a = null;
        String xSql = "SELECT id, email,username, password,role_id,status FROM dbo.accounts WHERE email =? ";

        try {
            PreparedStatement ps = connection.prepareStatement(xSql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), null, null, true);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (a);
    }

    //
    public Account getAccByEmailPass(String email, String pass) {
        Account a = null;
        String sql = "SELECT id, email,username,password,role_id,status FROM dbo.accounts WHERE email = ? AND password = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), null, null, true);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (a);
    }

    // Kiểm tra accountId tồn tại
    private boolean accountExists(int accountId) {
        String sql = "SELECT 1 FROM accounts WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Error while checking account existence for accountId " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra staff tồn tại
    private boolean staffExists(int accountId) {
        String sql = "SELECT 1 FROM staffs WHERE account_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Error while checking staff existence for accountId " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra doctor tồn tại
    private boolean doctorExists(int accountId) {
        String sql = "SELECT 1 FROM doctors WHERE account_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Error while checking doctor existence for accountId " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra specializationId tồn tại
    private boolean specializationExists(int specializationId) {
        String sql = "SELECT 1 FROM specializations WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, specializationId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Error while checking specialization existence for specializationId " + specializationId + ": " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra doctorLevelId tồn tại
    private boolean doctorLevelExists(int doctorLevelId) {
        String sql = "SELECT 1 FROM doctor_levels WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, doctorLevelId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.err.println("Error while checking doctor level existence for doctorLevelId " + doctorLevelId + ": " + e.getMessage());
            return false;
        }
    }

    // Thêm tài khoản mới
    public int addAccount(String email, String username, String password, int roleId, boolean status) {
        String sql = "INSERT INTO accounts (email, username, password, role_id, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if (emailExists(email)) {
                System.err.println("Email already exists: " + email);
                return -2; // Email đã tồn tại
            }
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, hashedPassword);
            ps.setInt(4, roleId);
            ps.setBoolean(5, status);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            System.err.println("Failed to insert account for email: " + email);
            return -1;
        } catch (Exception e) {
            System.err.println("Error while adding account for email " + email + ": " + e.getMessage());
            throw new RuntimeException("Error while adding account: " + e.getMessage(), e);
        }
    }

    // Thêm thông tin Staff
    public boolean addStaff(int accountId, String fullName, String address, LocalDateTime dob, String gender, String phoneNumber, String imageURL) {
        String sql = "INSERT INTO staffs (account_id, full_name, address, date_of_birth, gender, phone_number, imageURL) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (!accountExists(accountId)) {
                System.err.println("Account does not exist for accountId: " + accountId);
                return false;
            }
            if (fullName == null || fullName.trim().isEmpty() || phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
                System.err.println("Invalid input for staff: fullName=" + fullName + ", phoneNumber=" + phoneNumber);
                return false; // Kiểm tra đầu vào
            }
            ps.setInt(1, accountId);
            ps.setString(2, fullName);
            ps.setString(3, address);
            ps.setObject(4, dob);
            ps.setString(5, gender);
            ps.setString(6, phoneNumber);
            ps.setString(7, imageURL);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("No rows inserted in staffs for accountId: " + accountId);
            }
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error while adding staff for accountId " + accountId + ": " + e.getMessage());
            throw new RuntimeException("Error while adding staff: " + e.getMessage(), e);
        }
    }

    // Thêm thông tin Doctor
    public boolean addDoctor(int accountId, String fullName, String address, LocalDateTime dob, String gender, String phoneNumber, String imageURL, int specializationId, int doctorLevelId) {
        String sql = "INSERT INTO doctors (account_id, full_name, address, date_of_birth, gender, phone_number, imageURL, doctor_level_id, specialization_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (!accountExists(accountId) || !specializationExists(specializationId) || !doctorLevelExists(doctorLevelId)) {
                System.err.println("Invalid data for adding doctor: accountId=" + accountId + ", specializationId=" + specializationId + ", doctorLevelId=" + doctorLevelId);
                return false;
            }
            if (fullName == null || fullName.trim().isEmpty() || phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
                System.err.println("Invalid input for doctor: fullName=" + fullName + ", phoneNumber=" + phoneNumber);
                return false; // Kiểm tra đầu vào
            }
            ps.setInt(1, accountId);
            ps.setString(2, fullName);
            ps.setString(3, address);
            ps.setObject(4, dob);
            ps.setString(5, gender);
            ps.setString(6, phoneNumber);
            ps.setString(7, imageURL);
            ps.setInt(8, doctorLevelId);
            ps.setInt(9, specializationId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("No rows inserted in doctors for accountId: " + accountId);
            }
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error while adding doctor for accountId " + accountId + ": " + e.getMessage());
            throw new RuntimeException("Error while adding doctor: " + e.getMessage(), e);
        }
    }

    // Thêm thông tin Patient
    public boolean addPatient(int accountId, String fullName, String address, LocalDateTime dob, String gender, String phoneNumber, String imageURL, String identityNumber, String insuranceNumber) {
        String sql = "INSERT INTO patients (account_id, full_name, address, date_of_birth, gender, phone_number, imageURL, identity_number, insurance_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (!accountExists(accountId)) {
                System.err.println("Account does not exist for accountId: " + accountId);
                return false;
            }
            if (fullName == null || fullName.trim().isEmpty() || phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
                System.err.println("Invalid input for patient: fullName=" + fullName + ", phoneNumber=" + phoneNumber);
                return false; // Kiểm tra đầu vào
            }
            ps.setInt(1, accountId);
            ps.setString(2, fullName);
            ps.setString(3, address);
            ps.setObject(4, dob);
            ps.setString(5, gender);
            ps.setString(6, phoneNumber);
            ps.setString(7, imageURL);
            ps.setString(8, identityNumber);
            ps.setString(9, insuranceNumber);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("No rows inserted in patients for accountId: " + accountId);
            }
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error while adding patient for accountId " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    // Phương thức: Thêm tài khoản và thông tin chi tiết
    public boolean addAccountWithDetails(String email, String username, String password, int roleId, boolean status,
            String fullName, String address, LocalDateTime dob, String gender,
            String phoneNumber, String imageURL, int specializationId, int doctorLevelId) {
        // Thêm tài khoản vào bảng accounts
        int accountId = addAccount(email, username, password, roleId, status);
        if (accountId <= 0) {
            System.err.println("Failed to add account for email: " + email);
            return false;
        }

        // Thêm thông tin chi tiết tùy theo vai trò
        boolean detailsAdded = false;
        if (roleId == 4) { // Doctor
            detailsAdded = addDoctor(accountId, fullName, address, dob, gender, phoneNumber, imageURL, specializationId, doctorLevelId);
        } else if (roleId == 3 || roleId == 5) { // Manager hoặc Receptionist
            detailsAdded = addStaff(accountId, fullName, address, dob, gender, phoneNumber, imageURL);
        } else {
            System.err.println("Invalid roleId: " + roleId);
            return false;
        }

        if (!detailsAdded) {
            System.err.println("Failed to add details for accountId: " + accountId);
            return false;
        }

        return true;
    }

    // Lấy tất cả vai trò
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT id, name FROM roles";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleName(rs.getString("name"));
                roles.add(role);
            }
        } catch (Exception e) {
            System.err.println("Error while fetching roles: " + e.getMessage());
        }
        return roles;
    }

    // Lấy tất cả tài khoản
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT a.id, a.email, a.username, a.status, r.id as roleId "
                + "FROM accounts a "
                + "JOIN roles r ON a.role_id = r.id "
                + "LEFT JOIN staffs s ON a.id = s.account_id "
                + "LEFT JOIN doctors d ON a.id = d.account_id "
                + "LEFT JOIN patients p ON a.id = p.account_id WHERE r.id != 1";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setEmail(rs.getString("email"));
                account.setUsername(rs.getString("username"));
                account.setRoleId(rs.getInt("roleId"));
                account.setStatus(rs.getBoolean("status"));
                accounts.add(account);
            }
        } catch (Exception e) {
            System.err.println("Error while fetching all accounts: " + e.getMessage());
        }
        return accounts;
    }

    // Lấy chi tiết tài khoản cơ bản
    public Account getAccountDetails(int accountId) {
        String sql = "SELECT a.id, a.email, a.username, a.password, a.role_id, a.reset_token, a.reset_token_expiry, a.status "
                + "FROM accounts a "
                + "WHERE a.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setEmail(rs.getString("email"));
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    account.setRoleId(rs.getInt("role_id"));
                    account.setResetToken(rs.getString("reset_token"));
                    account.setResetTokenExpiry(rs.getTimestamp("reset_token_expiry") != null
                            ? rs.getTimestamp("reset_token_expiry").toLocalDateTime() : null);
                    account.setStatus(rs.getBoolean("status"));
                    return account;
                }
            }
        } catch (Exception e) {
            System.err.println("Error while fetching account details for accountId " + accountId + ": " + e.getMessage());
        }
        return null;
    }

    // Lấy chi tiết tài khoản theo vai trò
    public Object getAccountDetailsByRole(int accountId) {
        String sql = "SELECT a.role_id, "
                + "d.id AS doctor_id, d.account_id AS doctor_account_id, d.full_name AS doctor_fullName, d.address AS doctor_address, d.date_of_birth AS doctor_dob, d.gender AS doctor_gender, d.phone_number AS doctor_phone, d.imageURL AS doctor_imageURL, d.doctor_level_id, d.specialization_id, "
                + "p.id AS patient_id, p.full_name AS patient_fullName, p.address AS patient_address, p.date_of_birth AS patient_dob, p.gender AS patient_gender, p.phone_number AS patient_phone, p.imageURL AS patient_imageURL, p.identity_number, p.insurance_number, "
                + "s.id AS staff_id, s.account_id AS staff_account_id, s.full_name AS staff_fullName, s.address AS staff_address, s.date_of_birth AS staff_dob, s.gender AS staff_gender, s.phone_number AS staff_phone, s.imageURL AS staff_imageURL "
                + "FROM accounts a "
                + "LEFT JOIN doctors d ON a.id = d.account_id "
                + "LEFT JOIN patients p ON a.id = p.account_id "
                + "LEFT JOIN staffs s ON a.id = s.account_id "
                + "WHERE a.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int roleId = rs.getInt("role_id");
                    if (roleId == 4) { // Doctor
                        Doctor doctor = new Doctor();
                        doctor.setId(rs.getInt("doctor_id"));
                        doctor.setAccountId(rs.getInt("doctor_account_id"));
                        doctor.setFullName(rs.getString("doctor_fullName"));
                        doctor.setAddress(rs.getString("doctor_address"));
                        doctor.setDob(rs.getTimestamp("doctor_dob") != null
                                ? rs.getTimestamp("doctor_dob").toLocalDateTime() : null);
                        doctor.setGender(rs.getString("doctor_gender"));
                        doctor.setPhoneNumber(rs.getString("doctor_phone"));
                        doctor.setImageURL(rs.getString("doctor_imageURL"));
                        doctor.setDoctorLevelId(rs.getInt("doctor_level_id"));
                        doctor.setSpecializationId(rs.getInt("specialization_id"));
                        return doctor;
                    } else if (roleId == 2) { // Patient
                        Patient patient = new Patient();
                        patient.setId(rs.getInt("patient_id"));
                        patient.setFullName(rs.getString("patient_fullName"));
                        patient.setAddress(rs.getString("patient_address"));
                        patient.setDob(rs.getTimestamp("patient_dob") != null
                                ? rs.getTimestamp("patient_dob").toLocalDateTime() : null);
                        patient.setGender(rs.getString("patient_gender"));
                        patient.setPhoneNumber(rs.getString("patient_phone"));
                        patient.setImageURL(rs.getString("patient_imageURL"));
                        patient.setIdentityNumber(rs.getString("identity_number"));
                        patient.setInsuranceNumber(rs.getString("insurance_number"));
                        return patient;
                    } else if (roleId == 3 || roleId == 5) { // Manager or Receptionist
                        Staff staff = new Staff();
                        staff.setId(rs.getInt("staff_id"));
                        staff.setAccountId(rs.getInt("staff_account_id"));
                        staff.setFullName(rs.getString("staff_fullName"));
                        staff.setAddress(rs.getString("staff_address"));
                        staff.setDob(rs.getTimestamp("staff_dob") != null
                                ? rs.getTimestamp("staff_dob").toLocalDateTime() : null);
                        staff.setGender(rs.getString("staff_gender"));
                        staff.setPhoneNumber(rs.getString("staff_phone"));
                        staff.setImageURL(rs.getString("staff_imageURL"));
                        return staff;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error while fetching account details by role for accountId " + accountId + ": " + e.getMessage());
        }
        return null;
    }

    // Đếm tổng số tài khoản (loại bỏ Admin)
    public int getTotalAccounts() {
        String sql = "SELECT COUNT(*) FROM accounts a WHERE a.role_id != (SELECT id FROM roles WHERE name = 'Admin')";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error while counting total accounts: " + e.getMessage());
        }
        return 0;
    }

    // Lấy tài khoản theo trang
    public List<Account> getAccountsByPage(int page, int pageSize) {
        List<Account> accounts = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT a.id, a.email, a.username, a.status, a.role_id "
                + "FROM accounts a "
                + "WHERE a.role_id != (SELECT id FROM roles WHERE name = 'Admin') "
                + "ORDER BY a.id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setEmail(rs.getString("email"));
                    account.setUsername(rs.getString("username"));
                    account.setRoleId(rs.getInt("role_id"));
                    account.setStatus(rs.getBoolean("status"));
                    accounts.add(account);
                }
            }
        } catch (Exception e) {
            System.err.println("Error while fetching accounts by page: page=" + page + ", pageSize=" + pageSize + ", error: " + e.getMessage());
        }
        return accounts;
    }

    // Đếm tổng số tài khoản theo email
    public int getTotalAccountsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM accounts a WHERE a.role_id != (SELECT id FROM roles WHERE name = 'Admin')"
                + " AND a.email LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + email + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error while counting accounts by email: email=" + email + ", error: " + e.getMessage());
        }
        return 0;
    }

    // Lấy tài khoản theo trang với tìm kiếm theo email
    public List<Account> getAccountsByPageWithSearch(int page, int pageSize, String email) {
        List<Account> accounts = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT a.id, a.email, a.username, a.status, a.role_id "
                + "FROM accounts a "
                + "WHERE a.role_id != (SELECT id FROM roles WHERE name = 'Admin') "
                + "AND a.email LIKE ? "
                + "ORDER BY a.id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + email + "%");
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setEmail(rs.getString("email"));
                    account.setUsername(rs.getString("username"));
                    account.setRoleId(rs.getInt("role_id"));
                    account.setStatus(rs.getBoolean("status"));
                    accounts.add(account);
                }
            }
        } catch (Exception e) {
            System.err.println("Error while fetching accounts by page with search: page=" + page + ", pageSize=" + pageSize + ", email=" + email + ", error: " + e.getMessage());
        }
        return accounts;
    }

    // Đếm tổng số tài khoản với bộ lọc
    public int getTotalAccountsWithFilter(String email, Integer roleId, Boolean status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM accounts a WHERE a.role_id != (SELECT id FROM roles WHERE name = 'Admin')");
        List<Object> params = new ArrayList<>();
        if (email != null && !email.isEmpty()) {
            sql.append(" AND a.email LIKE ?");
            params.add("%" + email + "%");
        }
        if (roleId != null) {
            sql.append(" AND a.role_id = ?");
            params.add(roleId);
        }
        if (status != null) {
            sql.append(" AND a.status = ?");
            params.add(status ? 1 : 0);
        }
        System.out.println("Executing count SQL: " + sql.toString() + " with params: " + params); // Thêm log SQL
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Total accounts count: " + count); // Thêm log số lượng
                    return count;
                }
            }
        } catch (Exception e) {
            System.err.println("Error while counting accounts with filter: email=" + email + ", roleId=" + roleId + ", status=" + status + ", error: " + e.getMessage());
        }
        return 0;
    }

    // Lấy tài khoản theo trang với bộ lọc
    public List<Account> getAccountsByPageWithFilter(int page, int pageSize, String email, Integer roleId, Boolean status) {
        List<Account> accounts = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        StringBuilder sql = new StringBuilder(
                "SELECT a.id, a.email, a.username, a.status, a.role_id "
                + "FROM accounts a "
                + "WHERE a.role_id != (SELECT id FROM roles WHERE name = 'Admin') ");
        List<Object> params = new ArrayList<>();
        if (email != null && !email.isEmpty()) {
            sql.append(" AND a.email LIKE ?");
            params.add("%" + email + "%");
        }
        if (roleId != null) {
            sql.append(" AND a.role_id = ?");
            params.add(roleId);
        }
        if (status != null) {
            sql.append(" AND a.status = ?");
            params.add(status ? 1 : 0);
        }
        sql.append(" ORDER BY a.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offset);
        params.add(pageSize);
        System.out.println("Executing SQL: " + sql.toString() + " with params: " + params); // Thêm log SQL
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setEmail(rs.getString("email"));
                    account.setUsername(rs.getString("username"));
                    account.setRoleId(rs.getInt("role_id"));
                    account.setStatus(rs.getBoolean("status"));
                    accounts.add(account);
                    System.out.println("Account fetched: ID=" + account.getId() + ", RoleID=" + account.getRoleId()); // Thêm log từng tài khoản
                }
            }
        } catch (Exception e) {
            System.err.println("Error while fetching accounts with filter: page=" + page + ", pageSize=" + pageSize + ", email=" + email + ", roleId=" + roleId + ", status=" + status + ", error: " + e.getMessage());
        }
        System.out.println("Total accounts fetched: " + accounts.size()); // Thêm log tổng số tài khoản
        return accounts;
    }

    // Lấy danh sách Doctor Levels
    public List<DoctorLevel> getDoctorLevels() {
        List<DoctorLevel> levels = new ArrayList<>();
        String sql = "SELECT id, level_name, examination_fee FROM doctor_levels";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DoctorLevel level = new DoctorLevel();
                level.setId(rs.getInt("id"));
                level.setLevelName(rs.getString("level_name"));
                level.setExaminationFee(rs.getDouble("examination_fee"));
                levels.add(level);
            }
        } catch (Exception e) {
            System.err.println("Error while fetching doctor levels: " + e.getMessage());
        }
        return levels;
    }

    // Lấy danh sách Specializations
    public List<Specialization> getSpecializations() {
        List<Specialization> specs = new ArrayList<>();
        String sql = "SELECT id, name, description FROM specializations";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Specialization spec = new Specialization();
                spec.setId(rs.getInt("id"));
                spec.setName(rs.getString("name"));
                spec.setDescription(rs.getString("description"));
                specs.add(spec);
            }
        } catch (Exception e) {
            System.err.println("Error while fetching specializations: " + e.getMessage());
        }
        return specs;
    }

    // Cập nhật tài khoản
    public boolean updateAccount(int accountId, String username, String email, boolean status) {
        String sql = "UPDATE accounts SET username = ?, email = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (!accountExists(accountId)) {
                System.err.println("Account does not exist for accountId: " + accountId);
                return false;
            }
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setBoolean(3, status);
            ps.setInt(4, accountId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("No rows updated in accounts for accountId: " + accountId);
            }
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error while updating account for accountId " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    public  String getFormatDate(LocalDateTime myDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDate = dtf.format(myDate);
        return formatDate;
    }
    
    public boolean updateAccountToken(String reset_token, LocalDateTime token_expiry ,String email) {
        String sql = "UPDATE dbo.accounts SET reset_token = ?,reset_token_expiry =? WHERE email =?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reset_token);
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(getFormatDate(token_expiry)));
            int rowsAffected = ps.executeUpdate();
            ps.setString(3, email);
            if (rowsAffected == 0) {
                System.err.println("No rows updated in accounts for accountId: ");
            }
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error while updating account for accountId " + ": " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin Staff
    public boolean updateStaff(int accountId, String fullName, String address, LocalDateTime dob, String gender, String phoneNumber, String imageURL) {
        // Kiểm tra xem bản ghi có tồn tại trong bảng staffs không
        if (staffExists(accountId)) {
            // Nếu tồn tại, thực hiện UPDATE
            String sql = "UPDATE staffs SET full_name = ?, address = ?, date_of_birth = ?, gender = ?, phone_number = ?, imageURL = ? WHERE account_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, fullName);
                ps.setString(2, address);
                ps.setObject(3, dob);
                ps.setString(4, gender);
                ps.setString(5, phoneNumber);
                ps.setString(6, imageURL);
                ps.setInt(7, accountId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("No rows updated in staffs for accountId: " + accountId);
                }
                return rowsAffected > 0;
            } catch (Exception e) {
                System.err.println("Error while updating staff for accountId " + accountId + ": " + e.getMessage());
                return false;
            }
        } else {
            // Nếu không tồn tại, thực hiện INSERT
            return addStaff(accountId, fullName, address, dob, gender, phoneNumber, imageURL);
        }
    }

    // Cập nhật thông tin Doctor
    public boolean updateDoctor(int accountId, String fullName, String address, LocalDateTime dob, String gender, String phoneNumber, String imageURL, int doctorLevelId, int specializationId) {
        // Kiểm tra xem bản ghi có tồn tại trong bảng doctors không
        if (doctorExists(accountId)) {
            // Nếu tồn tại, thực hiện UPDATE
            String sql = "UPDATE doctors SET full_name = ?, address = ?, date_of_birth = ?, gender = ?, phone_number = ?, imageURL = ?, doctor_level_id = ?, specialization_id = ? WHERE account_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, fullName);
                ps.setString(2, address);
                ps.setObject(3, dob);
                ps.setString(4, gender);
                ps.setString(5, phoneNumber);
                ps.setString(6, imageURL);
                ps.setInt(7, doctorLevelId);
                ps.setInt(8, specializationId);
                ps.setInt(9, accountId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("No rows updated in doctors for accountId: " + accountId);
                }
                return rowsAffected > 0;
            } catch (Exception e) {
                System.err.println("Error while updating doctor for accountId " + accountId + ": " + e.getMessage());
                return false;
            }
        } else {
            // Nếu không tồn tại, thực hiện INSERT
            return addDoctor(accountId, fullName, address, dob, gender, phoneNumber, imageURL, specializationId, doctorLevelId);
        }
    }

    // Thay đổi trạng thái tài khoản
    public boolean changeAccountStatus(int accountId, boolean newStatus) {
        String sql = "UPDATE accounts SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, newStatus);
            ps.setInt(2, accountId);
            int rowsAffected = ps.executeUpdate();
            System.out.println("changeAccountStatus - accountId: " + accountId + ", newStatus: " + newStatus + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error while changing account status for accountId " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    // Xác thực người dùng
    public boolean authenticate(String email, String inputPassword) {
        String sql = "SELECT password FROM accounts WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    return BCrypt.checkpw(inputPassword, storedHash);
                }
            }
        } catch (Exception e) {
            System.err.println("Error while authenticating user with email " + email + ": " + e.getMessage());
        }
        return false;
    }

    // Di chuyển mật khẩu plain text sang dạng mã hóa
    public void migratePasswords() {
        String sqlSelect = "SELECT id, password FROM accounts";
        String sqlUpdate = "UPDATE accounts SET password = ? WHERE id = ?";
        try (PreparedStatement psSelect = connection.prepareStatement(sqlSelect); ResultSet rs = psSelect.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String plainPassword = rs.getString("password");
                if (!plainPassword.startsWith("$2a$")) { // Kiểm tra nếu chưa mã hóa
                    String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                    try (PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate)) {
                        psUpdate.setString(1, hashedPassword);
                        psUpdate.setInt(2, id);
                        psUpdate.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error while migrating passwords: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        LocalDateTime dob = LocalDateTime.of(1990, 1, 1, 0, 0);
        boolean success = dao.addDoctor(
                1, // Giả sử accountId đã tồn tại
                "Doctor Test",
                "123 Doctor St",
                dob,
                "Male",
                "0987654321",
                "doctor.jpg",
                1, // specializationId
                1 // doctorLevelId
        );
        System.out.println("Add doctor success: " + success);
    }

}
