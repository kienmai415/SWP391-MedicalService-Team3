/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.GoogleAccount;
import ultil.PasswordGenerator;

/**
 *
 * @author BB-MT
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
            String code = request.getParameter("code");

            GoogleLogin gg = new GoogleLogin();

            String accesToken = gg.getToken(code);
            System.out.println(accesToken);
            GoogleAccount ga = gg.getUserInfo(accesToken);
            System.out.println(ga);

            AccountDAO ad = new AccountDAO();

            Account ac = ad.getAccByEmail(ga.getEmail());
            System.out.println(ac);
            if (ac != null) {
                HttpSession session = request.getSession();
                session.setAttribute("ac", ac);
                request.getRequestDispatcher("patient.jsp").forward(request, response);
            } else {
                PasswordGenerator pg = new PasswordGenerator();
                String pass = pg.generatePassword(6);
//                Account acc = new Account(ga.getEmail(), pass, ga.getName(), "2", "Patient");
//                ad.insertAccount(acc);

                // Tạo session và chuyển hướng luôn sau khi tạo tài khoản mới
                HttpSession session = request.getSession();
                //session.setAttribute("acc", acc);
                request.getRequestDispatcher("admin.jsp").forward(request, response);
            }

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
        doPost(request, response);
        //request.getRequestDispatcher("login.jsp").forward(request, response);
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
        String password = request.getParameter("password");

        Account acc = ad.getAccByEmailPass(email, password);

        try (PrintWriter out = response.getWriter()) {

            if (acc == null) {
                String error = "Đăng nhập thất bại. Kiểm tra lại email và mật khẩu!";
                request.setAttribute("error", error);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("acc", acc);
                if (acc.getRoleId() == 1) {
                    request.getRequestDispatcher("admin/admindashboard.jsp").forward(request, response);
                }
                if (acc.getRoleId() == 4) {
                    request.getRequestDispatcher("bacsi.jsp").forward(request, response);
                }
                if (acc.getRoleId() == 3) {
                    request.getRequestDispatcher("manager.jsp").forward(request, response);
                }
                if (acc.getRoleId() == 2) {
                    request.getRequestDispatcher("admin.jsp").forward(request, response);
                }
                if (acc.getRoleId() == 5) {
                    request.getRequestDispatcher("letan.jsp").forward(request, response);
                }
            }

        }
        //processRequest(request, response);
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
