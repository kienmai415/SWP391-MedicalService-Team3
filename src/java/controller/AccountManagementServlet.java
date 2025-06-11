package controller;

import dal.AccountDAO;
import dal.DoctorLevelDao;
import dal.SpecializationDao;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import model.User;
import model.Doctor;
import model.Patient;
import model.Specialization;
import model.DoctorLevel;

/**
 *
 * @author maiki
 */
@WebServlet(name = "AccountManagementServlet", urlPatterns = {"/AccountManagementServlet"})
public class AccountManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String showSection = request.getParameter("showSection");
        if (action == null) {
            action = "list";
        }
        if (showSection == null) {
            showSection = "dashboard"; // Mặc định là dashboard khi vào lần đầu
        }

        switch (action) {
            case "list":
            case "search":
                handleListAccounts(request, response, showSection);
                break;
            case "deactivate":
                handleStatusUpdate(request, response, 0, "account-management");
                break;
            case "activate":
                handleStatusUpdate(request, response, 1, "account-management");
                break;
            case "view":
                handleViewAccount(request, response, showSection);
                break;
            case "add":
                handleAddAccount(request, response);
                break;
            default:
                request.setAttribute("showSection", showSection);
                request.getRequestDispatcher("/admin/admindashboard.jsp").forward(request, response);
                break;
        }
    }

    private void handleListAccounts(HttpServletRequest request, HttpServletResponse response, String showSection)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        // Reset filter khi vào lần đầu từ trang chủ
        if (email == null && role == null && status == null) {
            request.setAttribute("filterEmail", "");
            request.setAttribute("filterRole", null);
            request.setAttribute("filterStatus", null);
        } else {
            request.setAttribute("filterEmail", email);
            request.setAttribute("filterRole", role);
            request.setAttribute("filterStatus", status);
        }

        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        String messageType = (String) session.getAttribute("messageType");
        if (message != null && messageType != null) {
            request.setAttribute("message", message);
            request.setAttribute("messageType", messageType);
            session.removeAttribute("message"); // Xóa session sau khi dùng
            session.removeAttribute("messageType");
        }

        AccountDAO accountDAO = new AccountDAO();
        List<Object> accounts = accountDAO.getAccounts(email, role, status);

        request.setAttribute("accounts", accounts);
        request.setAttribute("showSection", showSection);

        request.getRequestDispatcher("/admin/admindashboard.jsp").forward(request, response);
    }

    private void handleStatusUpdate(HttpServletRequest request, HttpServletResponse response, int status, String returnSection)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            AccountDAO accountDAO = new AccountDAO();
            Map<String, Object> result = accountDAO.updateAccountStatus(id, status);
            HttpSession session = request.getSession();
            session.setAttribute("message", result.get("message"));
            session.setAttribute("messageType", result.get("messageType"));
        }
        // Giữ nguyên các tham số lọc hiện tại
        String email = request.getParameter("filterEmail");
        String role = request.getParameter("filterRole");
        String statusParam = request.getParameter("filterStatus");
        handleListAccounts(request, response, returnSection);
    }

    private void handleViewAccount(HttpServletRequest request, HttpServletResponse response, String showSection)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            AccountDAO accountDAO = new AccountDAO();
            Object account = accountDAO.getAccountById(id);
            Object detail = accountDAO.getAccountDetailById(id); // Lấy chi tiết dựa trên role

            request.setAttribute("selectedAccount", account);
            request.setAttribute("selectedDetail", detail);
        }
        request.setAttribute("showSection", showSection);
        request.getRequestDispatcher("/admin/admindashboard.jsp").forward(request, response);
    }

    private void handleAddAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            // Xử lý POST để thêm tài khoản
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String fullName = request.getParameter("fullName");
            String dobStr = request.getParameter("dob");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String phoneNumber = request.getParameter("phoneNumber");
            String specializationId = request.getParameter("specializationId");
            String doctorLevelId = request.getParameter("doctorLevelId");

            System.out.println("Attempting to add account with role: " + role);
            System.out.println("Details - Email: " + email + ", Username: " + username + ", Password: " + password
                    + ", FullName: " + fullName + ", DOB: " + dobStr + ", Gender: " + gender
                    + ", Address: " + address + ", Phone: " + phoneNumber
                    + ", SpecializationId: " + specializationId + ", DoctorLevelId: " + doctorLevelId);

            AccountDAO accountDAO = new AccountDAO();
            boolean success = false;
            String message = "";
            String messageType = "danger";
            LocalDate dob = null;

            try {
                if (dobStr != null && !dobStr.isEmpty()) {
                    dob = LocalDate.parse(dobStr);
                } else {
                    System.out.println("DOB is null or empty!");
                    message = "Ngày sinh không hợp lệ!";
                    HttpSession session = request.getSession();
                    session.setAttribute("message", message);
                    session.setAttribute("messageType", messageType);
                    response.sendRedirect(request.getContextPath() + "/admin/addaccount.jsp?showSection=add-account");
                    return;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error parsing DOB: " + e.getMessage());
                message = "Định dạng ngày sinh không đúng (yyyy-MM-dd)!";
                HttpSession session = request.getSession();
                session.setAttribute("message", message);
                session.setAttribute("messageType", messageType);
                response.sendRedirect(request.getContextPath() + "/admin/addaccount.jsp?showSection=add-account");
                return;
            }

            if ("Manager".equals(role) || "Receptionist".equals(role)) {
                User user = new User();
                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
                user.setFullName(fullName);
                user.setDob(dob);
                user.setGender(gender);
                user.setAddress(address);
                user.setPhoneNumber(phoneNumber);
                user.setStatus(true);
                success = accountDAO.addAccount(user);
                System.out.println("Add User result: " + success);
                if (success) {
                    message = "Thêm tài khoản " + role + " thành công!";
                    messageType = "success";
                } else {
                    message = "Thêm tài khoản thất bại! Kiểm tra log.";
                    // In stack trace để debug
                    System.err.println("Add User failed. Stack trace:");
                    new Exception().printStackTrace();
                }
            } else if ("Doctor".equals(role)) {
                if (specializationId != null && doctorLevelId != null && !specializationId.isEmpty() && !doctorLevelId.isEmpty()) {
                    int specId = Integer.parseInt(specializationId);
                    int levelId = Integer.parseInt(doctorLevelId);
                    if (specId > 0 && levelId > 0) { // Kiểm tra ID hợp lệ
                        Doctor doctor = new Doctor();
                        doctor.setEmail(email);
                        doctor.setUsername(username);
                        doctor.setPassword(password);
                        doctor.setRole(role);
                        doctor.setFullName(fullName);
                        doctor.setDob(dob);
                        doctor.setGender(gender);
                        doctor.setAddress(address);
                        doctor.setPhoneNumber(phoneNumber);
                        doctor.setStatus(true);
                        doctor.setSpecialization(new Specialization(specId, "", "")); // Chỉ gán ID, name và description sẽ được lấy từ DB
                        doctor.setDoctorLevel(new DoctorLevel(levelId, "", 0.0));   // Chỉ gán ID, name và fee sẽ được lấy từ DB
                        success = accountDAO.addAccount(doctor);
                        System.out.println("Add Doctor result: " + success);
                        if (success) {
                            message = "Thêm tài khoản Bác sĩ thành công!";
                            messageType = "success";
                        } else {
                            message = "Thêm tài khoản thất bại! Kiểm tra log.";
                            // In stack trace để debug
                            System.err.println("Add Doctor failed. Stack trace:");
                            new Exception().printStackTrace();
                        }
                    } else {
                        System.out.println("SpecializationId or DoctorLevelId is invalid (0 or negative)!");
                        message = "ID chuyên khoa hoặc trình độ không hợp lệ!";
                    }
                } else {
                    System.out.println("SpecializationId or DoctorLevelId is null or empty!");
                    message = "Thiếu thông tin chuyên khoa hoặc trình độ!";
                }
            }

            HttpSession session = request.getSession();
            session.setAttribute("message", message);
            session.setAttribute("messageType", messageType);
            // Chuyển hướng về danh sách quản lý tài khoản
            String filterEmail = request.getParameter("filterEmail");
            String filterRole = request.getParameter("filterRole");
            String filterStatus = request.getParameter("filterStatus");
            response.sendRedirect(request.getContextPath() + "/AccountManagementServlet?action=list&showSection=account-management"
                    + (filterEmail != null ? "&filterEmail=" + filterEmail : "")
                    + (filterRole != null ? "&filterRole=" + filterRole : "")
                    + (filterStatus != null ? "&filterStatus=" + filterStatus : ""));
            return;
        }

        // Xử lý GET (hiển thị form)
        AccountDAO accountDAO = new AccountDAO();
        List<Specialization> specializations = accountDAO.getSpecializations();
        List<DoctorLevel> doctorLevels = accountDAO.getDoctorLevels();
        request.setAttribute("specializations", specializations);
        request.setAttribute("doctorLevels", doctorLevels);
        request.setAttribute("showSection", "add-account");
        request.getRequestDispatcher("/admin/addaccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
