/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AppointmentScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.AppoinmentScheduleDTO;
import model.AppointmentSchedule;
import model.Patient;

/**
 *
 * @author BB-MT
 */
@WebServlet(name = "PatientServlet", urlPatterns = {"/patient"})
public class PatientServlet extends HttpServlet {

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
            out.println("<title>Servlet PatientServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PatientServlet at " + request.getContextPath() + "</h1>");
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
    AppointmentScheduleDAO dao = new AppointmentScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String action = request.getParameter("action");

        HttpSession session = request.getSession(false); // Không tạo mới nếu chưa có session

        if (session == null || session.getAttribute("p") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Patient patient = (Patient) session.getAttribute("p");

        if (patient != null) {
            int patientId = patient.getId();
            if ("history".equals(action)) {
                // phan Quý

                List<AppoinmentScheduleDTO> historyList = dao.getListAppointmentDTOByPId(patientId);

                request.setAttribute("historyList", historyList);
                request.setAttribute("pageContent", "lich_su_kham.jsp");
                request.getRequestDispatcher("view/patient/patient_dashboard.jsp?page=history").forward(request, response);

            } else if ("schedule".equals(action)) {
                // phan Quý
                List<AppointmentSchedule> historyList = dao.getListAppointmentById(patientId);

                request.setAttribute("historyList", historyList);
                request.setAttribute("pageContent", "dat_lich.jsp");
                request.getRequestDispatcher("view/patient/patient_dashboard.jsp?page=schedule").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
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

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("p") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Patient patient = (Patient) session.getAttribute("p");

        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");
        String symptom = request.getParameter("symptom");

        if (appointmentDate == null || appointmentDate.isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn ngày đặt khám");
        } else if (appointmentTime == null || appointmentTime.isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn giờ đặt khám");
        } else {
            LocalDate today = LocalDate.now();
            LocalDate minDate = today.plusDays(2);
            LocalDate maxDate = today.plusDays(7);
            LocalDate ngayDat = LocalDate.parse(appointmentDate);

            if (ngayDat.isBefore(minDate)) {
                request.setAttribute("error", "Ngày đặt lịch phải sau hôm nay ít nhất 2 ngày.");
            } else if (ngayDat.isAfter(maxDate)) {
                request.setAttribute("error", "Ngày đặt lịch không được vượt quá 7 ngày kể từ hôm nay.");
            } else {
                int result = dao.addAppointment(patient.getId(), appointmentDate, appointmentTime, symptom);
                if (result != -1) {
                    request.setAttribute("errorB", "Thông tin đặt lịch đã được gửi.");
                } else {
                    request.setAttribute("errorB", "Có lỗi xảy ra khi gửi thông tin đặt lịch.");
                }
            }
        }

        // Đặt lại thuộc tính để hiển thị đúng giao diện
        request.setAttribute("pageContent", "dat_lich.jsp");
        request.getRequestDispatcher("view/patient/patient_dashboard.jsp?page=schedule").forward(request, response);
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
