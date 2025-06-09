/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

//import DAO.BookingDAO;
//import Model.Booking;
import dal.AppointmentScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.AppointmentSchedule;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ReceptionServlet", urlPatterns = {"/ReceptionServlet"})
public class ReceptionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ReceptionServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        BookingDAO bd = new BookingDAO();
//        try {
//            List<Booking> list = bd.getBooking();
//            LOGGER.info("Fetched " + list.size() + " bookings for receptionist page.");
//            request.setAttribute("book", list);
//        } catch (SQLException e) {
//            LOGGER.severe("Error fetching bookings in doGet: " + e.getMessage());
//            request.setAttribute("error", "Lỗi khi lấy danh sách lịch hẹn: " + e.getMessage());
//        }
//        request.getRequestDispatcher("receptionist.jsp").forward(request, response);

//        AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
//        ArrayList<appointment_schedules> list = dao.getAllSchedules();
//        
//        request.setAttribute("activeTab", "appointments");
//        
//        request.setAttribute("listApp", list);
//        request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        String action = request.getParameter("action");
        if ("viewAppointments".equals(action)) {
            // 1. Lấy listApp từ DB/DAO
            AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
            ArrayList<AppointmentSchedule> list = dao.getAllSchedules();
            request.setAttribute("listApp", list);

            // 2. Đánh dấu tab “appointments” là đang active
            request.setAttribute("activeTab", "appointments");

            // 3. Forward lại về dashboard.jsp (JSP chứa các tab)
            request.getRequestDispatcher("receptionist.jsp").forward(request, response);

        } else if ("searchAppointments".equals(action)) {
            AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
            List<AppointmentSchedule> list;
            String keyword = request.getParameter("keyword");

            if (keyword != null && !keyword.trim().isEmpty()) {
                list = (List<AppointmentSchedule>) dao.getAppointmentSchedulesByName(keyword.trim());
            } else {
                list = dao.getAllSchedules();
            }
            request.setAttribute("listApp", list);
            request.setAttribute("activeTab", "appointments");
            request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        } else if ("viewDetail".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("id"));

            AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
            AppointmentSchedule ap = dao.getAppointmentSchedulesById(appointmentId);

            if (ap != null) {
                request.setAttribute("appointment", ap);
                request.getRequestDispatcher("appointmentDetail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Lịch hẹn không tồn tại.");
            }
        } else {
            request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
//        BookingDAO bd = new BookingDAO();
//        try {
//            String action = request.getParameter("action");
//            int id = Integer.parseInt(request.getParameter("id"));
//
//            if ("confirm".equals(action)) {
//                bd.updateConfirmationStatus(id, "Đã xác nhận");
//                request.setAttribute("message", "Lịch hẹn đã được xác nhận.");
//            } else if ("cancel".equals(action)) {
//                bd.updateConfirmationStatus(id, "Đã hủy");
//                request.setAttribute("message", "Lịch hẹn đã bị hủy.");
//            }
//
//            List<Booking> list = bd.getBooking();
//            LOGGER.info("Fetched " + list.size() + " bookings after action: " + action);
//            request.setAttribute("book", list);
//        } catch (SQLException e) {
//            LOGGER.severe("Error processing action in doPost: " + e.getMessage());
//            request.setAttribute("error", "Lỗi khi xử lý yêu cầu: " + e.getMessage());
//        } catch (NumberFormatException e) {
//            LOGGER.severe("Invalid ID format: " + e.getMessage());
//            request.setAttribute("error", "ID lịch hẹn không hợp lệ: " + e.getMessage());
//        }
//        request.getRequestDispatcher("receptionist.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Receptionist Booking Controller";
    }
}
