<%-- 
    Document   : appointmentDetail
    Created on : Jun 12, 2025, 11:20:54 AM
    Author     : MinhQuang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chi tiết lịch hẹn</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 30px;
            }

            .container {
                max-width: 800px;
                margin: 0 auto;
                background-color: #fff;
                border-radius: 12px;
                padding: 30px;
                box-shadow: 0 0 10px rgba(0,0,0,0.08);
            }

            h2 {
                text-align: center;
                color: #2d6a4f;
                margin-bottom: 20px;
            }

            .section {
                margin-bottom: 30px;
            }

            .section-title {
                font-size: 20px;
                color: #40916c;
                margin-bottom: 10px;
                border-bottom: 2px solid #74c69d;
                padding-bottom: 5px;
            }

            .info-row {
                margin-bottom: 10px;
            }

            .label {
                font-weight: bold;
                color: #1b4332;
                display: inline-block;
                width: 180px;
            }

            .value {
                color: #343a40;
            }

            .back-button {
                display: block;
                width: fit-content;
                margin: 30px auto 0;
                padding: 10px 25px;
                background-color: #2d6a4f;
                color: #fff;
                text-decoration: none;
                border-radius: 6px;
                transition: 0.3s;
            }

            .back-button:hover {
                background-color: #1b4332;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Chi tiết lịch hẹn</h2>

            <div class="section">
                <div class="section-title">Thông tin lịch hẹn</div>
                <div class="info-row"><span class="label">ID lịch hẹn:</span> <span class="value">${appointment.id}</span></div>
                <div class="info-row"><span class="label">Ngày:</span> <span class="value">${appointment.shiftSlot.date}</span></div>
                <div class="info-row"><span class="label">Giờ:</span> <span class="value">${appointment.shiftSlot.slotStartTime}</span></div>
                <div class="info-row"><span class="label">Trạng thái:</span> <span class="value">${appointment.confirmationStatus}</span></div>
            </div>

            <div class="section">
                <div class="section-title">Thông tin bác sĩ</div>
                <div class="info-row"><span class="label">Họ tên:</span> <span class="value">${appointment.doctor.fullName}</span></div>
                <div class="info-row"><span class="label">Email:</span> <span class="value">${appointment.doctor.email}</span></div>
                <div class="info-row"><span class="label">Giới tính:</span> <span class="value">${appointment.doctor.gender}</span></div>
                <div class="info-row"><span class="label">Điện thoại:</span> <span class="value">${appointment.doctor.phoneNumber}</span></div>
                <div class="info-row"><span class="label">Địa chỉ:</span> <span class="value">${appointment.doctor.address}</span></div>
            </div>

            <div class="section">
                <div class="section-title">Thông tin bệnh nhân</div>
                <div class="info-row"><span class="label">Họ tên:</span> <span class="value">${appointment.patient.fullName}</span></div>
                <div class="info-row"><span class="label">Email:</span> <span class="value">${appointment.patient.email}</span></div>
                <div class="info-row"><span class="label">Giới tính:</span> <span class="value">${appointment.patient.gender}</span></div>
                <div class="info-row"><span class="label">Điện thoại:</span> <span class="value">${appointment.patient.phoneNumber}</span></div>
                <div class="info-row"><span class="label">Địa chỉ:</span> <span class="value">${appointment.patient.address}</span></div>
            </div>
            <div style="display: flex ; justify-content: center; align-content: center;">
                <a href="ReceptionServlet?action=viewAppointments" class="back-button">Quay lại</a>
                <a href="ReceptionServlet?action=editAppointment&id=${appointment.id}" class="back-button">Chỉnh sửa</a>

            </div>

        </div>
    </body>
</html>

