/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

//import DAO.BookingDAO;
//import Model.Booking;
import dal.ReceptionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import model.AppointmentSchedule;
import model.Doctor;
import model.Patient;

/**
 *
 * @author MinhQuang
 */
@WebServlet(name = "ReceptionServlet", urlPatterns = {"/ReceptionServlet"})
public class ReceptionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ReceptionServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("p") == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
//        AppointmentSchedule appointmentSchedule = (AppointmentSchedule) session.getAttribute("p");
        
        String action = request.getParameter("action");
        String message = request.getParameter("message");
        if (message != null) {
            request.setAttribute("message", message);
        }
        if ("viewAppointments".equals(action)) {
            int pageIndex = 1;
            int pageSize = 10;

            String pageRaw = request.getParameter("page");
            if (pageRaw != null) {
                try {
                    pageIndex = Integer.parseInt(pageRaw);
                } catch (NumberFormatException e) {
                    pageIndex = 1;
                }
            }

            int offset = (pageIndex - 1) * pageSize;

            ReceptionDAO dao = new ReceptionDAO();

            List<AppointmentSchedule> list = dao.getAppointmentsWithPaging(offset, pageSize);

            int total = dao.countTotalAppointments();
            int totalPage = (int) Math.ceil((double) total / pageSize);

            request.setAttribute("listApp", list);
            request.setAttribute("page", pageIndex);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("activeTab", "appointments");

            request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        } else if ("searchAppointments".equals(action)) {
            ReceptionDAO dao = new ReceptionDAO();
            List<AppointmentSchedule> list;

            String keyword = request.getParameter("keyword");
            String pageRaw = request.getParameter("page");

            int pageIndex = 1;
            int pageSize = 10;

            if (pageRaw != null) {
                try {
                    pageIndex = Integer.parseInt(pageRaw);
                } catch (NumberFormatException e) {
                    pageIndex = 1;
                }
            }

            int offset = (pageIndex - 1) * pageSize;

            if (keyword != null) {
                keyword = keyword.trim().replaceAll("\\s+", " ");
                if (!keyword.isEmpty()) {
                    list = dao.getAppointmentSchedulesByName(keyword, offset, pageSize);
                    int total = dao.countTotalSearchAppointments(keyword);
                    int totalPage = (int) Math.ceil((double) total / pageSize);

                    request.setAttribute("page", pageIndex);
                    request.setAttribute("totalPage", totalPage);
                } else {
                    list = dao.getAppointmentsWithPaging(offset, pageSize);
                    int total = dao.countTotalAppointments();
                    int totalPage = (int) Math.ceil((double) total / pageSize);

                    request.setAttribute("page", pageIndex);
                    request.setAttribute("totalPage", totalPage);
                }
            } else {
                list = dao.getAppointmentsWithPaging(offset, pageSize);
                int total = dao.countTotalAppointments();
                int totalPage = (int) Math.ceil((double) total / pageSize);

                request.setAttribute("page", pageIndex);
                request.setAttribute("totalPage", totalPage);
            }

            request.setAttribute("listApp", list);
            request.setAttribute("activeTab", "appointments");
            request.setAttribute("keyword", keyword); // giữ lại từ khóa trong ô input
            request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        } else if ("viewDetail".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("id"));

            ReceptionDAO dao = new ReceptionDAO();
            AppointmentSchedule ap = dao.getAppointmentSchedulesById(appointmentId);

            if (ap != null) {
                request.setAttribute("appointment", ap);
                request.getRequestDispatcher("appointmentDetail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Lịch hẹn không tồn tại.");
            }

        } else if ("viewHistory".equals(action)) {
            int pageIndex = 1;
            int pageSize = 10;

            String pageRaw = request.getParameter("page");
            if (pageRaw != null) {
                try {
                    pageIndex = Integer.parseInt(pageRaw);
                } catch (NumberFormatException e) {
                    pageIndex = 1;
                }
            }

            ReceptionDAO dao = new ReceptionDAO();
            List<AppointmentSchedule> list = dao.getHistoryAppointmentsWithPaging(pageIndex, pageSize);
            int total = dao.countTotalHistoryAppointments();
            int totalPage = (int) Math.ceil((double) total / pageSize);

            request.setAttribute("listApp", list);
            request.setAttribute("page", pageIndex);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("activeTab", "history");

            request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        } else if ("searchHistory".equals(action)) {
            ReceptionDAO dao = new ReceptionDAO();
            String keyword = request.getParameter("keyword");
            List<AppointmentSchedule> list;

            if (keyword != null && !keyword.trim().isEmpty()) {
                keyword = keyword.trim().replaceAll("\\s+", " ");
                list = dao.searchHistoryAppointmentsByName(keyword);
            } else {
                list = dao.getHistoryAppointmentsWithPaging(1, 10);
            }

            request.setAttribute("listApp", list);
            request.setAttribute("activeTab", "history");
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("receptionist.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("p") == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
//        AppointmentSchedule appointmentSchedule = (AppointmentSchedule) session.getAttribute("p");
        
        String action = request.getParameter("action");
        if ("confirm".equals(action) || "cancel".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ReceptionDAO dao = new ReceptionDAO();
            AppointmentSchedule ap = dao.getAppointmentScheduleByIdRaw(id);

            if (ap != null) {
                ap.setConfirmationStatus("confirm".equals(action) ? "Done" : "Cancel");

                boolean updated = dao.updateAppointment(ap);

                if (updated) {
                    response.sendRedirect("ReceptionServlet?action=viewHistory&message=success");
                } else {
                    // Nếu không cập nhật được thì reload lại lịch sử để hiển thị lỗi
                    int pageIndex = 1;
                    int pageSize = 10;

                    List<AppointmentSchedule> list = dao.getHistoryAppointmentsWithPaging(pageIndex, pageSize);
                    int total = dao.countTotalHistoryAppointments();
                    int totalPage = (int) Math.ceil((double) total / pageSize);

                    request.setAttribute("listApp", list);
                    request.setAttribute("page", pageIndex);
                    request.setAttribute("totalPage", totalPage);
                    request.setAttribute("activeTab", "history");
                    request.setAttribute("error", "Không thể cập nhật trạng thái lịch hẹn.");
                    request.getRequestDispatcher("receptionist.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("ReceptionServlet?action=viewAppointments&error=Không+tìm+thấy+lịch+hẹn");
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Receptionist Booking Controller";
    }
}
