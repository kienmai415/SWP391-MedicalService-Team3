package controller;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package controller;
//
//import DAO.UserDAO;
//import Model.UserLogin;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
///**
// *
// * @author Admin
// */
//public class LoginServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet LoginServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        //processRequest(request, response);
//        String nemail = request.getParameter("nemail");
//        String npass = request.getParameter("npass");
//        String action = request.getParameter("action");
//        if (action != null) {
//            if (!action.isEmpty()) {
//                request.getRequestDispatcher("ForgetPassword.jsp").forward(request, response);
//            }
//        }
//
//        String login = request.getParameter("lg");
//        int lg = Integer.parseInt(login);
//        if (lg == 1) {
//            response.sendRedirect("login.jsp");
//        } else {
//            HttpSession session = request.getSession();
//            session.removeAttribute("account");
//            session.removeAttribute("cart");
//            session.removeAttribute("size");
//            response.sendRedirect("home");
//        }
//
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String remember = request.getParameter("remember");
//        UserDAO dao = new UserDAO();
//        UserLogin u = dao.login(username, password);
//
//        if (u == null) {
//            String er = "Email or Password incorrect! Please enter again!";
//            request.setAttribute("error", er);
//            request.getRequestDispatcher("login.jsp").forward(request, response);
//            return;  // <== important, tránh code tiếp tục chạy
//        }
//
//        if ("on".equals(remember)) {
//            Cookie cookiename = new Cookie("cookname", username);
//            cookiename.setMaxAge(3600);
//            Cookie cookiepass = new Cookie("cookpass", password);
//            cookiepass.setMaxAge(3600);
//            Cookie cookremember = new Cookie("cookremember", remember);
//            cookremember.setMaxAge(3600);
//
//            cookiename.setHttpOnly(true);
//            cookiepass.setHttpOnly(true);
//            cookremember.setHttpOnly(true);
//
//            response.addCookie(cookiename);
//            response.addCookie(cookiepass);
//            response.addCookie(cookremember);
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute("user", u);
//
//        int role = u.getRole();
//        if (role == 6) {
//            request.getRequestDispatcher("admin.jsp").forward(request, response);
//        } else if (role == 2) {
//            request.getRequestDispatcher("manager.jsp").forward(request, response);
//        } else if (role == 3) {
//            request.getRequestDispatcher("doctor.jsp").forward(request, response);
//        } else if (role == 4) {
//            response.sendRedirect("receptionist");
//        }else if (role == 5) {
//            request.getRequestDispatcher("cashier.jsp").forward(request, response);
//        }else if (role == 7) {
//            request.getRequestDispatcher("patient.jsp").forward(request, response);
//        } else {
//            response.sendRedirect("Login.jsp");  // nếu role không hợp lệ
//        }
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
