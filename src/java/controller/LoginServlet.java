package controller;

import dal.AccountDAO;
import dal.DoctorDAO;
import dal.PatientDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Doctor;
import model.Patient;
import model.User;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        PatientDAO patientDAO = new PatientDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        AccountDAO accountDAO = new AccountDAO();

        Patient patient = patientDAO.getPatientByEmailPass(email, password);
        Doctor doctor = doctorDAO.getDoctorByEmailPass(email, password);
        User user = accountDAO.getUserByEmailPass(email, password);

        HttpSession session = request.getSession();

        if (patient != null) {
            session.setAttribute("account", patient); 
            session.setAttribute("p", patient);
            response.sendRedirect("PatientServlet"); 
            return;
        }

        if (doctor != null) {
            session.setAttribute("account", doctor);
            session.setAttribute("d", doctor);
            response.sendRedirect("DoctorServlet"); 
            return;
        }

        if (user != null) {
            session.setAttribute("account", user);

            String role = user.getRole();
            if ("Admin".equals(role)) {
                response.sendRedirect("AccountManagementServlet?showSection=dashboard");
                return;
            } else if ("Receptionist".equals(role)) {
                response.sendRedirect("ReceptionServlet");
                return;
            } else if ("Manager".equals(role)) {
                response.sendRedirect("manager_dashboard.jsp");
                return;
            } else {
                request.setAttribute("error", "Vai trò người dùng không hợp lệ.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }

        // Đăng nhập thất bại
        request.setAttribute("error", "Đăng nhập thất bại. Kiểm tra lại email và mật khẩu!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login servlet xử lý đăng nhập cho mọi vai trò";
    }
}
