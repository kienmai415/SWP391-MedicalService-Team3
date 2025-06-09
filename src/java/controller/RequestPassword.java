/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PatientDAO;
import emailService.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Patient;
import ultil.PasswordGenerator;

/**
 *
 * @author BB-MT
 */
public class RequestPassword extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RequestPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestPassword at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String email = request.getParameter("email");
        PatientDAO pd = new PatientDAO();

        Patient p = pd.getPatientByEmail(email);
        if (p != null) {
            PasswordGenerator pg = new PasswordGenerator();
            String newPass = pg.generatePassword(10);
            boolean updated = pd.updatePassword(email, newPass);
            if (updated) {
                EmailSender es = new EmailSender();
                es.sendEmail(email, p.getFullName(), newPass);

                request.setAttribute("errorP", "Bạn hãy kiểm tra mail để lấy lại mật khẩu mới để đăng nhập");
                request.setAttribute("tab", "forgot"); // chuyen ve tab login neu thanh cong
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorP", "Không thể cập nhật mật khẩu. Vui lòng thử lại sau.");
                request.setAttribute("tab", "forgot");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            // Email không tồn tại trong cơ sở dữ liệu
            request.setAttribute("errorP", "Không tồn tại tài khoản");
            request.setAttribute("tab", "forgot");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
