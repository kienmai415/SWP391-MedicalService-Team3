
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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import model.Account;
import model.Doctor;
import model.DoctorLevel;
import model.Specialization;
import model.Staff;
import java.io.File;
/**
 *
 * @author maiki
 */
@WebServlet(name = "AccountManagementServlet", urlPatterns = {"/AccountManagementServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 20, // 20MB
                 maxFileSize = 1024 * 1024 * 100,      // 100MB
                 maxRequestSize = 1024 * 1024 * 500)   // 500MB
public class AccountManagementServlet extends HttpServlet {

    private AccountDAO accountDAO = new AccountDAO();
    private DoctorLevelDao doctorLevelDao = new DoctorLevelDao();
    private SpecializationDao specializationDao = new SpecializationDao();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int PAGE_SIZE = 5;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,31}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{6,}$");
    private static final Pattern FULLNAME_PATTERN = Pattern.compile("^[\\p{L}]+( {1,4}[\\p{L}]+)*$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile("^(https?://[\\w\\-.]+\\.[a-zA-Z]{2,}(/.*)?\\.(jpg|jpeg|png|jfif))$");

    private String validateInputFields(String username, String email, String password, String fullName, 
                                      String address, String dateOfBirth, String gender, String phoneNumber, 
                                      String roleIdParam, String imageURL, boolean isAddAction) {
        System.out.println("Validating inputs: username=" + username + ", email=" + email + ", fullName=" + fullName);
        if (username == null || email == null || fullName == null || dateOfBirth == null || 
            gender == null || phoneNumber == null || address == null || roleIdParam == null ||
            username.isEmpty() || email.isEmpty() || fullName.isEmpty() || dateOfBirth.isEmpty() || 
            gender.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || roleIdParam.isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin bắt buộc!";
        }

        if (isAddAction && (password == null || password.isEmpty())) {
            return "Mật khẩu không được để trống!";
        }
        if (isAddAction && !PASSWORD_PATTERN.matcher(password).matches()) {
            return "Mật khẩu phải từ 8-31 ký tự và chứa ít nhất 1 ký tự đặc biệt!";
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            return "Tên người dùng phải ít nhất 6 ký tự, chỉ chứa chữ cái hoặc số, không có dấu cách hoặc ký tự đặc biệt!";
        }

        if (fullName.length() < 8) {
            return "Họ tên phải có ít nhất 8 ký tự!";
        }
        if (!FULLNAME_PATTERN.matcher(fullName).matches()) {
            return "Họ tên chỉ được chứa chữ cái (bao gồm tiếng Việt) và tối đa 4 khoảng trắng giữa các từ, không chứa số hoặc ký tự đặc biệt!";
        }

        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            return "Số điện thoại phải chính xác 10 chữ số, không chứa chữ cái, ký tự đặc biệt hoặc dấu cách!";
        }

        try {
            LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);
            if (localDate.isAfter(LocalDate.now())) {
                return "Ngày sinh không được là ngày trong tương lai!";
            }
        } catch (DateTimeParseException e) {
            return "Ngày sinh không hợp lệ! Định dạng phải là yyyy-MM-dd.";
        }

        if (imageURL != null && !imageURL.isEmpty() && !IMAGE_URL_PATTERN.matcher(imageURL).matches()) {
            return "Sai định dạng ảnh";
        }

        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("AccountManagementServlet doGet called with action: " + action);

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
            System.err.println("Error while setting attributes in doGet: " + e.getMessage());
            request.setAttribute("message", "Lỗi khi tải dữ liệu: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.setAttribute("showSection", "dashboard");
            request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
            return;
        }

        try {
            if ("list".equals(action) || "search".equals(action)) {
                String email = request.getParameter("email");
                String roleIdParam = request.getParameter("role");
                String statusParam = request.getParameter("status");
                String pageParam = request.getParameter("page");

                System.out.println("Filter parameters: email=" + email + ", roleIdParam=" + roleIdParam + ", statusParam=" + statusParam + ", pageParam=" + pageParam);

                Integer roleId = null;
                if (roleIdParam != null && !roleIdParam.isEmpty() && !roleIdParam.equals("all")) {
                    try {
                        roleId = Integer.parseInt(roleIdParam);
                        System.out.println("Parsed roleId: " + roleId);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid roleId in search: " + roleIdParam);
                        request.setAttribute("message", "Vai trò không hợp lệ!");
                        request.setAttribute("messageType", "danger");
                        roleId = null;
                    }
                }

                Boolean status = null;
                if (statusParam != null && !statusParam.isEmpty() && !statusParam.equals("all")) {
                    status = statusParam.equals("activated");
                    System.out.println("Parsed status: " + status);
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

                int totalAccounts = accountDAO.getTotalAccountsWithFilter(email, roleId, status);
                int totalPages = (int) Math.ceil((double) totalAccounts / PAGE_SIZE);

                if (page < 1) page = 1;
                if (page > totalPages && totalPages > 0) page = totalPages;

                request.setAttribute("filterEmail", email);
                request.setAttribute("filterRole", roleIdParam);
                request.setAttribute("filterStatus", statusParam);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                try {
                    List<Account> accounts = accountDAO.getAccountsByPageWithFilter(page, PAGE_SIZE, email, roleId, status);
                    request.setAttribute("accounts", accounts);
                    System.out.println("Accounts fetched for roleId=" + roleId + ": " + accounts.size());
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
        System.out.println("doPost called with action: " + action);

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
                String imageURL = null;

                try {
                    Part filePart = request.getPart("imageFile");
                    if (filePart != null && filePart.getSize() > 0) {
                        String contentType = filePart.getContentType();
                        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/jfif") && !contentType.equals("image/jpg")) {
                            request.setAttribute("message", "Chỉ hỗ trợ định dạng ảnh .jpg, .png, .jfif, .jpg!");
                            request.setAttribute("messageType", "danger");
                            request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                            return;
                        }

                        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "images";
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            System.out.println("Creating upload directory: " + uploadPath);
                            if (!uploadDir.mkdirs()) {
                                request.setAttribute("message", "Không thể tạo thư mục lưu trữ ảnh!");
                                request.setAttribute("messageType", "danger");
                                request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                                return;
                            }
                        }
                        String filePath = uploadPath + File.separator + fileName;
                        System.out.println("Saving file to: " + filePath);
                        filePart.write(filePath);

                        String serverBaseURL = "http://localhost:8080" + request.getContextPath();
                        imageURL = serverBaseURL + "/uploads/images/" + fileName;
                        System.out.println("Generated imageURL: " + imageURL);
                    }
                } catch (Exception e) {
                    System.err.println("Error while processing file upload: " + e.getMessage());
                    request.setAttribute("message", "Lỗi khi xử lý file ảnh: " + e.getMessage());
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                    return;
                }

                String validationError = validateInputFields(username, email, password, fullName, 
                                                            address, dateOfBirth, gender, phoneNumber, 
                                                            roleIdParam, imageURL, true);
                if (validationError != null) {
                    System.err.println("Validation error: " + validationError);
                    request.setAttribute("message", validationError);
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                    return;
                }

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

                LocalDateTime dob;
                try {
                    LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);
                    dob = localDate.atStartOfDay();
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid dateOfBirth format: " + dateOfBirth);
                    request.setAttribute("message", "Ngày sinh không hợp lệ! Định dạng phải là yyyy-MM-dd.");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/addaccount.jsp").forward(request, response);
                    return;
                }

                int doctorLevelId = 0;
                int specializationId = 0;
                if (roleId == 4) {
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

                String validationError = validateInputFields(username, email, null, fullName, 
                                                            address, dateOfBirth, gender, phoneNumber, 
                                                            roleIdParam, imageURL, false);
                if (validationError != null) {
                    System.err.println("Validation error: " + validationError);
                    request.setAttribute("message", validationError);
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

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

                Account existingAccount = accountDAO.getAccountDetails(accountId);
                if (!email.equals(existingAccount.getEmail()) && accountDAO.emailExists(email)) {
                    System.err.println("Email already exists: " + email);
                    request.setAttribute("message", "Email đã tồn tại!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                boolean accountUpdated = accountDAO.updateAccount(accountId, username, email, existingAccount.isStatus());
                if (!accountUpdated) {
                    System.err.println("Failed to update account for accountId: " + accountId);
                    request.setAttribute("message", "Cập nhật tài khoản thất bại!");
                    request.setAttribute("messageType", "danger");
                    request.getRequestDispatcher("admin/updateaccount.jsp").forward(request, response);
                    return;
                }

                boolean detailsUpdated = false;
                if (roleId == 4) {
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
                } else if (roleId == 3 || roleId == 5) {
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
