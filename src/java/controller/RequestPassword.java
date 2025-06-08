/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import emailservice.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

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
        AccountDAO ad = new AccountDAO();
        String email = request.getParameter("email");

        Account acc = ad.getAccByEmail(email);

        if (acc == null) {
            String error = "Tài khoản đã tồn tại!!";
            request.setAttribute("errorP", error);
            request.setAttribute("tab", "forgot"); // chuyen ve register login neu thanh cong
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        EmailSender es = new EmailSender();
        String token = es.generateToken();
        // để tạm trang login 
        String linkRest = "http://localhost:8080/SWP391-MedicalHealthCareSystem/login?token" + token;

        boolean isUpdate = ad.updateAccountToken(token, es.expireDateTime(), acc.getEmail());

        if (!isUpdate) {
            request.setAttribute("mess", "Email không tồn tại!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        boolean isSend = es.sendEmail(email, linkRest, acc.getUsername());
        if (!isSend) {
            request.setAttribute("mess", "Email không tồn tại!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
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
