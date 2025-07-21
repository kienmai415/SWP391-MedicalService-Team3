package controller;

import dal.AppointmentScheduleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.AppoinmentScheduleDTO;
import model.AppointmentSchedule;
import model.Doctor;

@WebServlet(name = "AppointmentScheduleServlet", urlPatterns = {"/AppointmentScheduleServlet"})
public class AppointmentScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        AppointmentScheduleDAO dao = new AppointmentScheduleDAO();

        if ("view".equals(action)) {
            int patientId = Integer.parseInt(request.getParameter("pid"));
            List<AppoinmentScheduleDTO> appointments = dao.getListAppointmentDTOByPId(patientId);
            if (!appointments.isEmpty()) {
                AppoinmentScheduleDTO appointmentInfo = appointments.get(0);
                request.setAttribute("appointmentInfo", appointmentInfo);
            }
            request.getRequestDispatcher("appointment_info.jsp").forward(request, response);
            return;
        }

        if ("detail".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            AppointmentSchedule detail = dao.getAppointmentScheduleById(appointmentId);
            request.setAttribute("appointment", detail);
            request.getRequestDispatcher("appointment_info.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Doctor sessionDoctor = (Doctor) session.getAttribute("d");

        if (sessionDoctor == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int doctorId = sessionDoctor.getId();
        List<AppoinmentScheduleDTO> list = dao.getListAppointmentDTOByDoctorId(doctorId);

        request.setAttribute("appointmentList", list);
        request.getRequestDispatcher("appointment_schedule.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        AppointmentScheduleDAO dao = new AppointmentScheduleDAO();

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));

            switch (action) {
                case "complete":
                    dao.updateAttendanceStatus(appointmentId, "Completed"); 
                    dao.markMedicalRecordAsDone(appointmentId);             
                    break;

                case "noShow":
                    dao.updateAttendanceStatus(appointmentId, "No-show"); 
                    break;

                
            }

        } catch (NumberFormatException e) {
            
        } catch (Exception e) {
            e.printStackTrace();
        }

// Reload lại danh sách lịch hẹn cho bác sĩ
        response.sendRedirect("AppointmentScheduleServlet");
    }

    @Override
    public String getServletInfo() {
        return "AppointmentScheduleServlet using DTO model.";
    }
}
