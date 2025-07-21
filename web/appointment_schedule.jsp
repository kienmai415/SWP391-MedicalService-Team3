<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.AppointmentSchedule" %>

<html>
    <head>
        <title>Danh sách lịch khám</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    </head>
    <body>
        <div class="container mt-3">
            <div class="text-start mt-4">
                <a href = "login" class="btn btn-secondary">⬅ Quay lại trang chủ</a>
            </div>
            <h2 class="text-center mb-4">📋 Danh sách lịch hẹn</h2>

            <table class="table table-bordered table-striped text-center">
                <thead class="table-dark">
                    <tr>
                        <th>ID bệnh nhân</th>
                        <th>Tên bệnh nhân</th>
                        <th>SDT</th>
                        <th>Năm sinh</th>
                        <th>Ngày hẹn khám</th>
                        <th>Giờ bắt đầu</th>
                        <th>Bs phụ trách</th>
                        <th>Xem chi tiết</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.ListAppointment}" var="a">
                        <tr>
                            <td>${a.patient.id}</td>
                            <td>${a.patient.fullName}</td>
                            <td>${a.patient.phoneNumber}</td>
                            <td>${a.patient.dob}</td>
                            <td>${a.shiftSlot.date}</td>
                            <td>${a.shiftSlot.slotStartTime}</td>
                            <td>${a.doctor.fullName}</td>
                            <td>
                                <a href="AppointmentScheduleServlet?action=view&pid=${a.patient.id}" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-eye"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>
        </div>

    </body>
</html>
