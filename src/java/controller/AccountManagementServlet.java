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
            case "update": // Xử lý trang cập nhật tài khoản
                if ("update-account".equals(showSection)) {
                    String email = request.getParameter("email");
                    if (email != null) {
                        AccountDAO accountDAO = new AccountDAO();
                        request.setAttribute("selectedAccount", accountDAO.getAccountByEmail(email));
                        request.setAttribute("selectedDetail", accountDAO.getAccountDetailByEmail(email));
                        // Lấy danh sách specialization và doctorLevel cho Doctor
                        request.setAttribute("specializations", accountDAO.getSpecializations());
                        request.setAttribute("doctorLevels", accountDAO.getDoctorLevels());
                        request.setAttribute("role", request.getAttribute("selectedAccount") != null
                                ? (request.getAttribute("selectedAccount") instanceof User ? ((User) request.getAttribute("selectedAccount")).getRole()
                                : request.getAttribute("selectedAccount") instanceof Doctor ? "Doctor" : "Patient") : null);
                    }
                }
                request.setAttribute("showSection", showSection);
                request.getRequestDispatcher("/admin/updateaccount.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        if (email != null) {
            AccountDAO accountDAO = new AccountDAO();
            Map<String, Object> result = accountDAO.updateAccountStatus(email, status);
            HttpSession session = request.getSession();
            session.setAttribute("message", result.get("message"));
            session.setAttribute("messageType", result.get("messageType"));
        } else {
            System.out.println("No email parameter provided for status update action");
        }
        // Làm mới danh sách sau khi cập nhật trạng thái
        String filterEmail = request.getParameter("filterEmail");
        String filterRole = request.getParameter("filterRole");
        String filterStatus = request.getParameter("filterStatus");
        handleListAccounts(request, response, returnSection);
    }

    private void handleViewAccount(HttpServletRequest request, HttpServletResponse response, String showSection)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email != null) {
            System.out.println("Attempting to view account with email: " + email);
            AccountDAO accountDAO = new AccountDAO();
            Object account = accountDAO.getAccountByEmail(email);
            Object detail = accountDAO.getAccountDetailByEmail(email);
            System.out.println("Retrieved account: " + (account != null ? account.toString() : "null"));
            System.out.println("Retrieved detail: " + (detail != null ? detail.toString() : "null"));
            if (account == null) {
                System.out.println("No account found for email: " + email);
            }
            if (detail == null) {
                System.out.println("No detail found for email: " + email);
            }
            request.setAttribute("selectedAccount", account);
            request.setAttribute("selectedDetail", detail);
        } else {
            System.out.println("No email parameter provided for view action");
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

            // Validate các trường
            String validationMessage = validateInput(username, email, password, fullName, dobStr, phoneNumber, role, specializationId, doctorLevelId);
            if (validationMessage != null) {
                HttpSession session = request.getSession();
                session.setAttribute("message", validationMessage);
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/admin/addaccount.jsp?showSection=add-account");
                return;
            }

            AccountDAO accountDAO = new AccountDAO();
            boolean success = false;
            String message = "";
            String messageType = "danger";
            LocalDate dob = null;

            try {
                if (dobStr != null && !dobStr.isEmpty()) {
                    dob = LocalDate.parse(dobStr);
                    // Kiểm tra ngày sinh không phải tương lai
                    LocalDate currentDate = LocalDate.now(); // 09:41 AM +07, 13/06/2025
                    if (dob.isAfter(currentDate)) {
                        message = "Ngày sinh không được là tương lai!";
                        HttpSession session = request.getSession();
                        session.setAttribute("message", message);
                        session.setAttribute("messageType", messageType);
                        response.sendRedirect(request.getContextPath() + "/admin/addaccount.jsp?showSection=add-account");
                        return;
                    }
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
                }
            } else if ("Doctor".equals(role)) {
                if (specializationId != null && doctorLevelId != null && !specializationId.isEmpty() && !doctorLevelId.isEmpty()) {
                    int specId = Integer.parseInt(specializationId);
                    int levelId = Integer.parseInt(doctorLevelId);
                    if (specId > 0 && levelId > 0) {
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
                        doctor.setSpecialization(new Specialization(specId, ""));
                        doctor.setDoctorLevel(new DoctorLevel(levelId, ""));
                        success = accountDAO.addAccount(doctor);
                        System.out.println("Add Doctor result: " + success);
                        if (success) {
                            message = "Thêm tài khoản Bác sĩ thành công!";
                            messageType = "success";
                        } else {
                            message = "Thêm tài khoản thất bại! Kiểm tra log.";
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

    private void handleUpdateAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email != null) {
            AccountDAO accountDAO = new AccountDAO();
            Object account = accountDAO.getAccountByEmail(email);
            Object detail = accountDAO.getAccountDetailByEmail(email);

            if (account != null) {
                String role = "";
                if (account instanceof User) {
                    role = ((User) account).getRole();
                } else if (account instanceof Doctor) {
                    role = ((Doctor) account).getRole();
                }
                request.setAttribute("selectedAccount", account);
                request.setAttribute("selectedDetail", detail);
                request.setAttribute("role", role);
                request.setAttribute("showSection", "update-account");
                request.getRequestDispatcher("/admin/updateaccount.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("message", "Không tìm thấy tài khoản để cập nhật!");
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/AccountManagementServlet?action=list&showSection=account-management");
            }
        }
    }

    private void handleUpdateAccountPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy email cũ từ hidden field
        String originalEmail = request.getParameter("originalEmail");
        if (originalEmail == null || originalEmail.trim().isEmpty()) {
            HttpSession session = request.getSession();
            originalEmail = (String) session.getAttribute("selectedEmail");
            if (originalEmail == null) {
                System.err.println("Original email not found. Update aborted.");
                response.sendRedirect(request.getContextPath() + "/AccountManagementServlet?action=list&showSection=account-management");
                return;
            }
        }

        // Lấy email mới từ form
        String newEmail = request.getParameter("email");
        if (newEmail != null && newEmail.trim().isEmpty()) {
            newEmail = originalEmail; // Giữ nguyên nếu để trống
        } else if (newEmail == null) {
            newEmail = originalEmail;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String dobStr = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String specializationId = request.getParameter("specializationId");
        String doctorLevelId = request.getParameter("doctorLevelId");

        System.out.println("Attempting to update account with original email: " + originalEmail + ", new email: " + newEmail);
        System.out.println("Details - New Email: " + newEmail + ", Username: " + username + ", Password: " + password
                + ", FullName: " + fullName + ", DOB: " + dobStr + ", Gender: " + gender
                + ", Address: " + address + ", Phone: " + phoneNumber
                + ", SpecializationId: " + specializationId + ", DoctorLevelId: " + doctorLevelId);

        // Validate
        String validationMessage = validateInput(username, newEmail, password, fullName, dobStr, phoneNumber, null, specializationId, doctorLevelId);
        if (validationMessage != null) {
            HttpSession session = request.getSession();
            session.setAttribute("message", validationMessage);
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/admin/updateaccount.jsp?showSection=update-account&email=" + originalEmail);
            return;
        }

        LocalDate dob = null;
        try {
            if (dobStr != null && !dobStr.isEmpty()) {
                dob = LocalDate.parse(dobStr);
                if (dob.isAfter(LocalDate.now())) {
                    throw new DateTimeParseException("Ngày sinh không được là tương lai!", dobStr, 0);
                }
            }
        } catch (DateTimeParseException e) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Định dạng ngày sinh không đúng (yyyy-MM-dd) hoặc không hợp lệ!");
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/admin/updateaccount.jsp?showSection=update-account&email=" + originalEmail);
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        Object account = accountDAO.getAccountByEmail(originalEmail);
        if (account == null) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Không tìm thấy tài khoản để cập nhật!");
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/AccountManagementServlet?action=list&showSection=account-management");
            return;
        }

        boolean success = false;
        if (account instanceof User) {
            User user = (User) account;
            user.setEmail(newEmail);
            user.setUsername(username);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setDob(dob);
            user.setGender(gender);
            user.setAddress(address);
            user.setPhoneNumber(phoneNumber);
            success = accountDAO.updateAccount(user);
        } else if (account instanceof Doctor) {
            Doctor doctor = (Doctor) account;
            doctor.setEmail(newEmail);
            doctor.setUsername(username);
            doctor.setPassword(password);
            doctor.setFullName(fullName);
            doctor.setDob(dob);
            doctor.setGender(gender);
            doctor.setAddress(address);
            doctor.setPhoneNumber(phoneNumber);
            if (specializationId != null && doctorLevelId != null) {
                doctor.setSpecialization(new Specialization(Integer.parseInt(specializationId), ""));
                doctor.setDoctorLevel(new DoctorLevel(Integer.parseInt(doctorLevelId), ""));
            }
            success = accountDAO.updateAccount(doctor);
        }

        HttpSession session = request.getSession();
        if (success) {
            session.setAttribute("message", "Cập nhật tài khoản thành công!");
            session.setAttribute("messageType", "success");
        } else {
            session.setAttribute("message", "Cập nhật tài khoản thất bại! Kiểm tra log.");
            session.setAttribute("messageType", "danger");
        }
        response.sendRedirect(request.getContextPath() + "/AccountManagementServlet?action=list&showSection=account-management");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            handleUpdateAccountPost(request, response);
        } else {
            doGet(request, response);
        }
    }

    // Hàm validate các trường
    private String validateInput(String username, String email, String password, String fullName, String dobStr, String phoneNumber, String role, String specializationId, String doctorLevelId) {
        // Validate username: tối thiểu 4 ký tự, chỉ chữ thường và số, không khoảng cách
        if (username == null || username.length() < 4 || !Pattern.matches("^[a-z0-9]+$", username)) {
            return "Tên người dùng phải có ít nhất 4 ký tự, chỉ chứa chữ thường và số, không khoảng cách!";
        }

        // Validate email: không rỗng
        if (email == null || email.trim().isEmpty()) {
            return "Email không được rỗng!";
        }

        // Validate password: 8-31 ký tự, chứa ít nhất 1 chữ cái, 1 số, 1 ký tự đặc biệt
        if (password == null || password.length() < 8 || password.length() > 31
                || !Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]+$", password)) {
            return "Mật khẩu phải có 8-31 ký tự, chứa ít nhất 1 chữ cái, 1 số, và 1 ký tự đặc biệt (!@#$%^&*)!";
        }

        // Validate fullName: chỉ chữ cái (bao gồm dấu tiếng Việt), khoảng trắng không vượt quá 4 ký tự
        if (fullName == null || !Pattern.matches("^[\\p{L}\\s]{1,50}$", fullName)) {
            return "Họ tên chỉ chấp nhận chữ cái (bao gồm dấu), tối đa 50 ký tự!";
        }
        // Kiểm tra độ dài khoảng trắng tối đa 4 ký tự
        int spaceCount = 0;
        for (int i = 0; i < fullName.length() - 1; i++) {
            if (fullName.charAt(i) == ' ' && fullName.charAt(i + 1) == ' ') {
                spaceCount++;
                int j = i + 1;
                while (j < fullName.length() - 1 && fullName.charAt(j) == ' ' && spaceCount < 4) {
                    spaceCount++;
                    j++;
                }
                if (spaceCount > 4) {
                    return "Khoảng trắng giữa các từ không được vượt quá 4 ký tự!";
                }
                i = j - 1; // Bỏ qua các khoảng trắng đã đếm
            }
        }

        // Validate dob: chỉ chấp nhận quá khứ và hiện tại
        if (dobStr != null && !dobStr.isEmpty()) {
            try {
                LocalDate dob = LocalDate.parse(dobStr);
                LocalDate currentDate = LocalDate.now(); // 09:41 AM +07, 13/06/2025
                if (dob.isAfter(currentDate)) {
                    return "Ngày sinh không được là tương lai!";
                }
            } catch (DateTimeParseException e) {
                return "Định dạng ngày sinh không đúng (yyyy-MM-dd)!";
            }
        }

        // Validate phoneNumber: chính xác 10 số, chỉ chữ số
        if (phoneNumber == null || !Pattern.matches("^[0-9]{10}$", phoneNumber)) {
            return "Số điện thoại phải là 10 chữ số!";
        }

        // Validate specializationId và doctorLevelId chỉ khi là Doctor
        if ("Doctor".equals(role)) {
            if (specializationId == null || specializationId.trim().isEmpty() || Integer.parseInt(specializationId) <= 0) {
                return "ID chuyên khoa không hợp lệ!";
            }
            if (doctorLevelId == null || doctorLevelId.trim().isEmpty() || Integer.parseInt(doctorLevelId) <= 0) {
                return "ID trình độ không hợp lệ!";
            }
        }

        return null; // Validate thành công
    }
}
