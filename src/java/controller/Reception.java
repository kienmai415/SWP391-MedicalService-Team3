/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import DAO.BookingDAO;
import Model.Booking;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name="Reception", urlPatterns={"/Reception"})
public class Reception extends HttpServlet {
   
  
    private static final Logger LOGGER = Logger.getLogger(Reception.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookingDAO bd = new BookingDAO();
        try {
            List<Booking> list = bd.getBooking();
            LOGGER.info("Fetched " + list.size() + " bookings for receptionist page.");
            request.setAttribute("book", list);
        } catch (SQLException e) {
            LOGGER.severe("Error fetching bookings in doGet: " + e.getMessage());
            request.setAttribute("error", "Lỗi khi lấy danh sách lịch hẹn: " + e.getMessage());
        }
        request.getRequestDispatcher("receptionist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        BookingDAO bd = new BookingDAO();
        try {
            String action = request.getParameter("action");
            int id = Integer.parseInt(request.getParameter("id"));

            if ("confirm".equals(action)) {
                bd.updateConfirmationStatus(id, "Đã xác nhận");
                request.setAttribute("message", "Lịch hẹn đã được xác nhận.");
            } else if ("cancel".equals(action)) {
                bd.updateConfirmationStatus(id, "Đã hủy");
                request.setAttribute("message", "Lịch hẹn đã bị hủy.");
            }

            List<Booking> list = bd.getBooking();
            LOGGER.info("Fetched " + list.size() + " bookings after action: " + action);
            request.setAttribute("book", list);
        } catch (SQLException e) {
            LOGGER.severe("Error processing action in doPost: " + e.getMessage());
            request.setAttribute("error", "Lỗi khi xử lý yêu cầu: " + e.getMessage());
        } catch (NumberFormatException e) {
            LOGGER.severe("Invalid ID format: " + e.getMessage());
            request.setAttribute("error", "ID lịch hẹn không hợp lệ: " + e.getMessage());
        }
        request.getRequestDispatcher("receptionist.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Receptionist Booking Controller";
    }
}
