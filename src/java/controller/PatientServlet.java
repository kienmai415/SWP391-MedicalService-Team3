package controller;

import dal.AppointmentScheduleDAO;
import dal.DoctorDAO;
import java.io.IOException;
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
import model.Doctor;
import model.Patient;

@WebServlet(name = "PatientServlet", urlPatterns = {"/PatientServlet"})
public class PatientServlet extends HttpServlet {

    AppointmentScheduleDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new AppointmentScheduleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("p") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Patient patient = (Patient) session.getAttribute("p");
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            request.setAttribute("pageContent", "dashboard_placeholder.jsp");
            request.getRequestDispatcher("view/patient/patient_dashboard.jsp").forward(request, response);
            return;
        }

        int patientId = patient.getId();

        if (action.equals("history")) {
            List<AppoinmentScheduleDTO> historyList = dao.getListAppointmentDTOByPId(patientId);
            request.setAttribute("historyList", historyList);
            request.setAttribute("pageContent", "lich_su_kham.jsp");
        } else if (action.equals("schedule")) {
            List<AppointmentSchedule> historyList = dao.getListAppointmentById(patientId);
            List<Doctor> listDoctors = new DoctorDAO().getAllDoctors();
            request.setAttribute("historyList", historyList);
            request.setAttribute("listDoctors", listDoctors);
            request.setAttribute("pageContent", "dat_lich.jsp");
        } else {
            request.setAttribute("pageContent", "dashboard_placeholder.jsp");
        }
        request.getRequestDispatcher("view/patient/patient_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("p") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Patient patient = (Patient) session.getAttribute("p");

        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");
        String symptom = request.getParameter("symptom");
        String doctorIdRaw = request.getParameter("doctorId");

        if (appointmentDate == null || appointmentDate.isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn ngày đặt khám");
        } else if (appointmentTime == null || appointmentTime.isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn giờ đặt khám");
        } else if (doctorIdRaw == null || doctorIdRaw.isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn bác sĩ");
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
                try {
                    int doctorId = Integer.parseInt(doctorIdRaw);
                    int result = dao.addAppointment(patient.getId(), doctorId, appointmentDate, appointmentTime, symptom);
                    if (result != -1) {
                        request.setAttribute("errorB", "Thông tin đặt lịch đã được gửi.");
                    } else {
                        request.setAttribute("errorB", "Có lỗi xảy ra khi gửi thông tin đặt lịch.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID bác sĩ không hợp lệ.");
                }
            }
        }

        // Luôn tải lại danh sách bác sĩ và hiển thị trang đặt lịch
        List<Doctor> listDoctors = new DoctorDAO().getAllDoctors();
        request.setAttribute("listDoctors", listDoctors);
        request.setAttribute("pageContent", "dat_lich.jsp");
        request.getRequestDispatcher("view/patient/patient_dashboard.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "PatientServlet";
    }
}
