//package controller;
//
//import DAO.BookingDAO;
//import DAO.PatientDAO;
//import Model.Booking;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Time;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.logging.Logger;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet(name = "BookingServlet", urlPatterns = {"/booking"})
//public class BookingServlet extends HttpServlet {
//    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Medicare_Booking;encrypt=true;trustServerCertificate=true";
//    private static final String DB_USER = "sa";
//    private static final String DB_PASSWORD = "123";
//    private static final Logger LOGGER = Logger.getLogger(BookingServlet.class.getName());
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.getRequestDispatcher("booking.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        PatientDAO patientDAO = new PatientDAO();
//        BookingDAO bookingDAO = new BookingDAO();
//
//        try {
//            // Extract form fields
//            String fullName = request.getParameter("name");
//            String gender = request.getParameter("gender");
//            String phoneNumber = request.getParameter("phone");
//            String dobStr = request.getParameter("dob");
//            String address = request.getParameter("address");
//            String insuranceNumber = request.getParameter("insurance_number");
//            String dateStr = request.getParameter("date");
//            String startTimeStr = request.getParameter("start_time");
//            String endTimeStr = request.getParameter("end_time");
//
//            // Validate required fields
//            if (fullName == null || fullName.trim().isEmpty() || gender == null || phoneNumber == null ||
//                phoneNumber.length() > 15 || dobStr == null || address == null || address.trim().isEmpty() ||
//                dateStr == null || startTimeStr == null || endTimeStr == null) {
//                request.setAttribute("error", "Vui lòng điền đầy đủ và đúng định dạng các trường bắt buộc!");
//                request.getRequestDispatcher("booking.jsp").forward(request, response);
//                return;
//            }
//
//            // Parse date and time
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date sqlDate;
//            try {
//                sqlDate = new Date(dateFormat.parse(dateStr).getTime());
//            } catch (Exception e) {
//                request.setAttribute("error", "Định dạng ngày không hợp lệ: " + dateStr);
//                request.getRequestDispatcher("booking.jsp").forward(request, response);
//                return;
//            }
//            java.util.Date dob;
//            try {
//                dob = dateFormat.parse(dobStr);
//            } catch (Exception e) {
//                request.setAttribute("error", "Định dạng ngày sinh không hợp lệ: " + dobStr);
//                request.getRequestDispatcher("booking.jsp").forward(request, response);
//                return;
//            }
//            Time startTime;
//            Time endTime;
//            try {
//                startTime = Time.valueOf(startTimeStr + ":00");
//                endTime = Time.valueOf(endTimeStr + ":00");
//            } catch (IllegalArgumentException e) {
//                request.setAttribute("error", "Định dạng thời gian không hợp lệ: " + startTimeStr + " hoặc " + endTimeStr);
//                request.getRequestDispatcher("booking.jsp").forward(request, response);
//                return;
//            }
//
//            // Generate insurance_number if not provided
//            if (insuranceNumber == null || insuranceNumber.trim().isEmpty()) {
//                insuranceNumber = "TEMP_" + System.currentTimeMillis();
//            }
//
//            // Randomly select any doctor
//            int doctorId = getRandomDoctorId();
//            LOGGER.info("Random doctorId selected: " + doctorId);
//            if (doctorId == -1) {
//                request.setAttribute("error", "Không có bác sĩ nào khả dụng.");
//                request.getRequestDispatcher("booking.jsp").forward(request, response);
//                return;
//            }
//
//            // Create booking without checking slot availability
//            Booking booking = new Booking();
//            booking.setDateAppointment(sqlDate);
//            booking.setStartTime(startTime);
//            booking.setEndTime(endTime);
//            booking.setPatientId(insuranceNumber);
//            booking.setDoctorId(doctorId);
//            booking.setAppointmentSlotId(null); // No slot check, set to null
//            booking.setConfirmationStatus("Pending");
//
//            // Insert booking
//            boolean isBooked = bookingDAO.addBooking(booking);
//            if (isBooked) {
//                LOGGER.warning("Booking created without slot verification for doctorId=" + doctorId + ", date=" + sqlDate + ", startTime=" + startTimeStr + ", endTime=" + endTimeStr);
//                request.setAttribute("message", "Đặt lịch khám thành công. Lịch này chưa được xác nhận khung giờ trống, vui lòng liên hệ để điều chỉnh nếu cần.");
//            } else {
//                request.setAttribute("error", "Đặt lịch khám thất bại. Vui lòng thử lại.");
//            }
//
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi đặt lịch khám: " + e.getMessage());
//            request.setAttribute("error", "Đã xảy ra lỗi khi xử lý yêu cầu: " + e.getMessage());
//        }
//
//        request.getRequestDispatcher("booking.jsp").forward(request, response);
//    }
//
//    private int getRandomDoctorId() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT id FROM doctors WHERE account_id IS NOT NULL";
//        List<Integer> doctorIds = new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             ResultSet rs = pstmt.executeQuery()) {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            while (rs.next()) {
//                doctorIds.add(rs.getInt("id"));
//            }
//        }
//        if (doctorIds.isEmpty()) {
//            return -1; // No active doctors
//        }
//        Random random = new Random();
//        return doctorIds.get(random.nextInt(doctorIds.size()));
//    }
//}