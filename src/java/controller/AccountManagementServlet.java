
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.AccountDAO;
import dal.DoctorLevelDao;
import dal.SpecializationDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import model.Account;
import model.Doctor;
import model.DoctorLevel;
import model.Specialization;
import model.Staff;

/**
 *
 * @author maiki
 */
@WebServlet(name = "AccountManagementServlet", urlPatterns = {"/AccountManagementServlet"})
public class AccountManagementServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private AccountDAO accountDAO = new AccountDAO();
    private DoctorLevelDao doctorLevelDao = new DoctorLevelDao();
    private SpecializationDao specializationDao = new SpecializationDao();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int PAGE_SIZE = 5; // Số lượng tài khoản tối đa mỗi trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        // Log để kiểm tra xem Servlet có được gọi không
        System.out.println("AccountManagementServlet doGet called with action: " + action);

        // Đặt các attribute cần thiết cho JSP
        try {
            request.setAttribute("roles", accountDAO.getAllRoles());
            request.setAttribute("specializations", specializationDao.getAll());
            request.setAttribute("doctorLevels", doctorLevelDao.getAll());

            // Đặt doctorRoleId
            request.setAttribute("doctorRoleId", accountDAO.getAllRoles().stream()
                    .filter(r -> r.getRoleName().equals("Doctor"))
                    .findFirst()
                    .map(r -> r.getId())
                    .orElse(0));
        } catch (Exception e) {
            System.err.println("Error while setting attributes in doGet: " + e.getMessage());
            request.setAttribute("message", "Lỗi khi tải dữ liệu: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.setAttribute("showSection", "dashboard");
            request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
            return;
        }

        // Xử lý các action
        try {
            if ("list".equals(action) || "search".equals(action)) {
                // Lấy tham số lọc và phân trang
                String email = request.getParameter("email");
                String roleIdParam = request.getParameter("role");
                String statusParam = request.getParameter("status");
                String pageParam = request.getParameter("page");

                Integer roleId = null;
                if (roleIdParam != null && !roleIdParam.isEmpty() && !roleIdParam.equals("all")) {
                    try {
                        roleId = Integer.parseInt(roleIdParam);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid roleId in search: " + roleIdParam);
                        request.setAttribute("message", "Vai trò không hợp lệ!");
                        request.setAttribute("messageType", "danger");
                    }
                }

                Boolean status = null;
                if (statusParam != null && !statusParam.isEmpty() && !statusParam.equals("all")) {
                    status = statusParam.equals("activated");
                }

                int page = 1;
                if (pageParam != null && !pageParam.isEmpty()) {
                    try {
                        page = Integer.parseInt(pageParam);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid page number: " + pageParam);
                        page = 1;
                    }
                }

                // Tính tổng số tài khoản và số trang
                int totalAccounts = accountDAO.getTotalAccountsWithFilter(email, roleId, status);
                int totalPages = (int) Math.ceil((double) totalAccounts / PAGE_SIZE);

                // Đảm bảo page hợp lệ
                if (page < 1) page = 1;
                if (page > totalPages && totalPages > 0) page = totalPages;

                // Lưu tham số lọc và phân trang để JSP hiển thị lại
                request.setAttribute("filterEmail", email);
                request.setAttribute("filterRole", roleIdParam);
                request.setAttribute("filterStatus", statusParam);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // Tải danh sách tài khoản với bộ lọc và phân trang
                try {
                    List<Account> accounts = accountDAO.getAccountsByPageWithFilter(page, PAGE_SIZE, email, roleId, status);
                    request.setAttribute("accounts", accounts);
                } catch (Exception e) {
                    System.err.println("Error while fetching accounts: " + e.getMessage());
                    request.setAttribute("message", "Lỗi khi tải danh sách tài khoản: " + e.getMessage());
                    request.setAttribute("messageType", "danger");
                }
                request.setAttribute("showSection", "account-management");
                request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
            } else if ("view".equals(action)) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Account account = accountDAO.getAccountDetails(id);
                    Object detail = accountDAO.getAccountDetailsByRole(id);
                    request.setAttribute("selectedAccount", account);
                    request.setAttribute("selectedDetail", detail);
                    request.setAttribute("showSection", "account-detail");
                    request.setAttribute("accounts", accountDAO.getAllAccounts());
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid account ID: " + request.getParameter("id"));
                    request.setAttribute("message", "ID tài khoản không hợp lệ!");
                    request.setAttribute("messageType", "danger");
                    request.setAttribute("showSection", "account-management");
                    request.setAttribute("accounts", accountDAO.getAllAccounts());
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                }
            } else if ("deactivate".equals(action)) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    boolean success = accountDAO.changeAccountStatus(id, false);
                    request.setAttribute("message", success ? "Vô hiệu hóa tài khoản thành công!" : "Vô hiệu hóa tài khoản thất bại!");
                    request.setAttribute("messageType", success ? "success" : "danger");
                    request.setAttribute("showSection", "account-management");
                    String pageParam = request.getParameter("page");
                    int page = pageParam != null ? Integer.parseInt(pageParam) : 1;
                    List<Account> accounts = accountDAO.getAccountsByPageWithFilter(page, PAGE_SIZE, null, null, null);
                    request.setAttribute("accounts", accounts);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", (int) Math.ceil((double) accountDAO.getTotalAccounts() / PAGE_SIZE));
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid account ID: " + request.getParameter("id"));
                    request.setAttribute("message", "ID tài khoản không hợp lệ!");
                    request.setAttribute("messageType", "danger");
                    request.setAttribute("showSection", "account-management");
                    request.setAttribute("accounts", accountDAO.getAllAccounts());
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                }
            } else if ("activate".equals(action)) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    boolean success = accountDAO.changeAccountStatus(id, true);
                    request.setAttribute("message", success ? "Kích hoạt tài khoản thành công!" : "Kích hoạt tài khoản thất bại!");
                    request.setAttribute("messageType", success ? "success" : "danger");
                    request.setAttribute("showSection", "account-management");
                    String pageParam = request.getParameter("page");
                    int page = pageParam != null ? Integer.parseInt(pageParam) : 1;
                    List<Account> accounts = accountDAO.getAccountsByPageWithFilter(page, PAGE_SIZE, null, null, null);
                    request.setAttribute("accounts", accounts);
                    request.setAttribute("currentPage", page);
                    request.setAttribute("totalPages", (int) Math.ceil((double) accountDAO.getTotalAccounts() / PAGE_SIZE));
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid account ID: " + request.getParameter("id"));
                    request.setAttribute("message", "ID tài khoản không hợp lệ!");
                    request.setAttribute("messageType", "danger");
                    request.setAttribute("showSection", "account-management");
                    request.setAttribute("accounts", accountDAO.getAllAccounts());
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                }
            } else if ("statistics".equals(action)) {
                request.setAttribute("showSection", "statistics");
                request.setAttribute("accounts", accountDAO.getAllAccounts());
                request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
            } else if ("add".equals(action)) {
                System.out.println("Redirecting to addaccount.jsp");
                request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
            } else if ("update".equals(action)) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Account account = accountDAO.getAccountDetails(id);
                    Object detail = accountDAO.getAccountDetailsByRole(id);
                    request.setAttribute("selectedAccount", account);
                    request.setAttribute("selectedDetail", detail);
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid account ID: " + request.getParameter("id"));
                    request.setAttribute("message", "ID tài khoản không hợp lệ!");
                    request.setAttribute("messageType", "danger");
                    request.setAttribute("showSection", "account-management");
                    request.setAttribute("accounts", accountDAO.getAllAccounts());
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("accounts", accountDAO.getAllAccounts());
                request.setAttribute("showSection", "dashboard");
                request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.err.println("Error in doGet: " + e.getMessage());
            request.setAttribute("message", "Lỗi xử lý yêuêu cầu: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.setAttribute("showSection", "dashboard");
            request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {
            request.setAttribute("roles", accountDAO.getAllRoles());
            request.setAttribute("specializations", specializationDao.getAll());
            request.setAttribute("doctorLevels", doctorLevelDao.getAll());
            
            request.setAttribute("doctorRoleId", accountDAO.getAllRoles().stream()
                    .filter(r -> r.getRoleName().equals("Doctor"))
                    .findFirst()
                    .map(r -> r.getId())
                    .orElse(0));
        } catch (Exception e) {
            System.err.println("Error while setting attributes in doPost: " + e.getMessage());
            request.setAttribute("message", "Lỗi khi tải dữ liệu: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
            return;
        }

        try {
            if ("add".equals(action)) {
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String roleIdParam = request.getParameter("roleId");
                String fullName = request.getParameter("fullName");
                String address = request.getParameter("address");
                String dateOfBirth = request.getParameter("dateOfBirth");
                String gender = request.getParameter("gender");
                String phoneNumber = request.getParameter("phoneNumber");
                String imageURL = request.getParameter("imageURL");

                // Kiểm tra các tham số bắt buộc trước
                if (username == null || email == null || password == null || roleIdParam == null ||
                    fullName == null || dateOfBirth == null || gender == null || phoneNumber == null ||
                    username.isEmpty() || email.isEmpty() || password.isEmpty() || roleIdParam.isEmpty() ||
                    fullName.isEmpty() || dateOfBirth.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty()) {
                    System.err.println("Missing required parameters: username=" + username + ", email=" + email + 
                                       ", password=" + password + ", roleId=" + roleIdParam + ", fullName=" + fullName + 
                                       ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", phoneNumber=" + phoneNumber);
                    request.setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                    return;
                }

                // Parse roleId
                int roleId;
                try {
                    roleId = Integer.parseInt(roleIdParam);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid roleId: " + roleIdParam);
                    request.setAttribute("message", "Vai trò không hợp lệ!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                    return;
                }

                // Parse dateOfBirth thành LocalDate trước, rồi chuyển thành LocalDateTime
                LocalDateTime dob;
                try {
                    LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);
                    dob = localDate.atStartOfDay(); // Đặt thời gian thành 00:00
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid dateOfBirth format: " + dateOfBirth);
                    request.setAttribute("message", "Ngày sinh không hợp lệ! Định dạng phải là yyyy-MM-dd.");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra các tham số đặc biệt cho Doctor
                int doctorLevelId = 0;
                int specializationId = 0;
                if (roleId == 4) { // Doctor
                    String doctorLevelIdParam = request.getParameter("doctorLevelId");
                    String specializationIdParam = request.getParameter("specializationId");

                    if (doctorLevelIdParam == null || specializationIdParam == null ||
                        doctorLevelIdParam.isEmpty() || specializationIdParam.isEmpty()) {
                        System.err.println("Missing doctorLevelId or specializationId for Doctor role: doctorLevelId=" + doctorLevelIdParam + ", specializationId=" + specializationIdParam);
                        request.setAttribute("message", "Vui lòng chọn trình độ và chuyên khoa cho bác sĩ!");
                        request.setAttribute("messageType", "danger");
                        request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                        return;
                    }

                    try {
                        doctorLevelId = Integer.parseInt(doctorLevelIdParam);
                        specializationId = Integer.parseInt(specializationIdParam);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid doctorLevelId or specializationId: " + doctorLevelIdParam + ", " + specializationIdParam);
                        request.setAttribute("message", "Trình độ hoặc chuyên khoa không hợp lệ!");
                        request.setAttribute("messageType", "danger");
                        request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                        return;
                    }
                }

                // Gọi phương thức trong AccountDAO để thêm tài khoản và thông tin chi tiết trong một transaction
                String resultMessage;
                String messageType;
                boolean success = accountDAO.addAccountWithDetails(email, username, password, roleId, true,
                        fullName, address, dob, gender, phoneNumber, imageURL, specializationId, doctorLevelId);

                if (success) {
                    resultMessage = (roleId == 4) ? "Thêm bác sĩ thành công!" : "Thêm nhân viên thành công!";
                    messageType = "success";
                } else {
                    resultMessage = (roleId == 4) ? "Thêm thông tin bác sĩ thất bại!" : "Thêm thông tin nhân viên thất bại!";
                    messageType = "danger";
                }

                request.setAttribute("message", resultMessage);
                request.setAttribute("messageType", messageType);
                if (success) {
                    request.setAttribute("showSection", "account-management");
                    try {
                        List<Account> accounts = accountDAO.getAccountsByPageWithFilter(1, PAGE_SIZE, null, null, null);
                        request.setAttribute("accounts", accounts);
                        request.setAttribute("currentPage", 1);
                        request.setAttribute("totalPages", (int) Math.ceil((double) accountDAO.getTotalAccounts() / PAGE_SIZE));
                    } catch (Exception e) {
                        System.err.println("Error while fetching accounts after adding: " + e.getMessage());
                        request.setAttribute("message", resultMessage + " Nhưng lỗi khi tải danh sách tài khoản: " + e.getMessage());
                        request.setAttribute("messageType", "warning");
                    }
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                }
            } else if ("updateAccount".equals(action)) {
                String accountIdParam = request.getParameter("accountId");
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String roleIdParam = request.getParameter("roleId");
                String fullName = request.getParameter("fullName");
                String address = request.getParameter("address");
                String dateOfBirth = request.getParameter("dateOfBirth");
                String gender = request.getParameter("gender");
                String phoneNumber = request.getParameter("phoneNumber");
                String imageURL = request.getParameter("imageURL");

                // Kiểm tra các tham số bắt buộc
                if (accountIdParam == null || username == null || email == null || roleIdParam == null ||
                    fullName == null || dateOfBirth == null || gender == null || phoneNumber == null ||
                    accountIdParam.isEmpty() || username.isEmpty() || email.isEmpty() || roleIdParam.isEmpty() ||
                    fullName.isEmpty() || dateOfBirth.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty()) {
                    System.err.println("Missing required parameters: accountId=" + accountIdParam + ", username=" + username + 
                                       ", email=" + email + ", roleId=" + roleIdParam + ", fullName=" + fullName + 
                                       ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", phoneNumber=" + phoneNumber);
                    request.setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                // Parse accountId và roleId
                int accountId;
                int roleId;
                try {
                    accountId = Integer.parseInt(accountIdParam);
                    roleId = Integer.parseInt(roleIdParam);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid accountId or roleId: accountId=" + accountIdParam + ", roleId=" + roleIdParam);
                    request.setAttribute("message", "ID tài khoản hoặc vai trò không hợp lệ!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                // Parse dateOfBirth
                LocalDateTime dob;
                try {
                    LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);
                    dob = localDate.atStartOfDay();
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid dateOfBirth format: " + dateOfBirth);
                    request.setAttribute("message", "Ngày sinh không hợp lệ! Định dạng phải là yyyy-MM-dd.");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra email trùng lặp (không tính tài khoản hiện tại)
                Account existingAccount = accountDAO.getAccountDetails(accountId);
                if (!email.equals(existingAccount.getEmail()) && accountDAO.emailExists(email)) {
                    System.err.println("Email already exists: " + email);
                    request.setAttribute("message", "Email đã tồn tại!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                // Cập nhật thông tin tài khoản trong bảng accounts
                boolean accountUpdated = accountDAO.updateAccount(accountId, username, email, existingAccount.isStatus());
                if (!accountUpdated) {
                    System.err.println("Failed to update account for accountId: " + accountId);
                    request.setAttribute("message", "Cập nhật tài khoản thất bại!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                // Cập nhật thông tin chi tiết tùy theo vai trò
                boolean detailsUpdated = false;
                if (roleId == 4) { // Doctor
                    String doctorLevelIdParam = request.getParameter("doctorLevelId");
                    String specializationIdParam = request.getParameter("specializationId");

                    if (doctorLevelIdParam == null || specializationIdParam == null ||
                        doctorLevelIdParam.isEmpty() || specializationIdParam.isEmpty()) {
                        System.err.println("Missing doctorLevelId or specializationId for Doctor role: doctorLevelId=" + doctorLevelIdParam + ", specializationId=" + specializationIdParam);
                        request.setAttribute("message", "Vui lòng chọn trình độ và chuyên khoa cho bác sĩ!");
                        request.setAttribute("messageType", "danger");
                        request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                        return;
                    }

                    int doctorLevelId;
                    int specializationId;
                    try {
                        doctorLevelId = Integer.parseInt(doctorLevelIdParam);
                        specializationId = Integer.parseInt(specializationIdParam);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid doctorLevelId or specializationId: " + doctorLevelIdParam + ", " + specializationIdParam);
                        request.setAttribute("message", "Trình độ hoặc chuyên khoa không hợp lệ!");
                        request.setAttribute("messageType", "danger");
                        request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                        return;
                    }

                    detailsUpdated = accountDAO.updateDoctor(accountId, fullName, address, dob, gender, phoneNumber, imageURL, doctorLevelId, specializationId);
                } else if (roleId == 3 || roleId == 5) { // Manager hoặc Receptionist
                    detailsUpdated = accountDAO.updateStaff(accountId, fullName, address, dob, gender, phoneNumber, imageURL);
                } else {
                    System.err.println("Invalid roleId for update: " + roleId);
                    request.setAttribute("message", "Vai trò không hợp lệ!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                if (detailsUpdated) {
                    request.setAttribute("message", "Cập nhật thông tin tài khoản thành công!");
                    request.setAttribute("messageType", "success");
                    request.setAttribute("showSection", "account-management");
                    try {
                        List<Account> accounts = accountDAO.getAccountsByPageWithFilter(1, PAGE_SIZE, null, null, null);
                        request.setAttribute("accounts", accounts);
                        request.setAttribute("currentPage", 1);
                        request.setAttribute("totalPages", (int) Math.ceil((double) accountDAO.getTotalAccounts() / PAGE_SIZE));
                    } catch (Exception e) {
                        System.err.println("Error while fetching accounts after updating: " + e.getMessage());
                        request.setAttribute("message", "Cập nhật thông tin tài khoản thành công, nhưng lỗi khi tải danh sách tài khoản: " + e.getMessage());
                        request.setAttribute("messageType", "warning");
                    }
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                } else {
                    System.err.println("Failed to update details for accountId: " + accountId);
                    request.setAttribute("message", "Cập nhật thông tin tài khoản thất bại!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in doPost: " + e.getMessage());
            request.setAttribute("message", "Lỗi xử lý yêuêu cầu: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            if ("updateAccount".equals(action)) {
                request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
            }
        }
    }

}
