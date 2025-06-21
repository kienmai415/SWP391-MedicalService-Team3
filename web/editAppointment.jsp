<%-- 
    Document   : editAppointment
    Created on : Jun 14, 2025, 11:04:24 PM
    Author     : MinhQuang
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<!DOCTYPE html>
<html>
    <head>
        <title>Chỉnh sửa lịch hẹn</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                font-family: "Segoe UI", sans-serif;
                background-color: #f9f9f9;
                padding: 30px;
            }

            .container {
                max-width: 800px;
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 12px;
                padding: 40px 30px;
                box-shadow: 0 0 15px rgba(0,0,0,0.08);
            }

            h2 {
                text-align: center;
                color: #2d6a4f;
                margin-bottom: 25px;
            }

            .form-label {
                font-weight: 600;
                color: #1b4332;
            }

            .form-control, .form-select {
                border-radius: 10px;
                border: 1px solid #ced4da;
                background-color: #ffffff;
                transition: all 0.3s;
            }

            .form-control:focus, .form-select:focus {
                border-color: #2d6a4f;
                box-shadow: 0 0 0 0.2rem rgba(46, 125, 50, 0.25);
            }

            .id-box {
                background-color: #e6f4ec;
                padding: 10px 20px;
                border-radius: 10px;
                display: inline-block;
                font-weight: 600;
                color: #2d6a4f;
                margin-bottom: 25px;
            }

            .button-group {
                display: flex;
                justify-content: center;
                gap: 20px;
                margin-top: 30px;
            }

            .btn-save {
                background-color: #2d6a4f;
                color: #fff;
                padding: 10px 25px;
                border: none;
                border-radius: 8px;
                font-weight: 500;
                transition: background-color 0.3s ease;
            }

            .btn-save:hover {
                background-color: #1b4332;
            }

            .btn-back {
                background-color: #2d6a4f;
                color: #ffffff;
                padding: 10px 25px;
                border: none;
                border-radius: 8px;
                font-weight: 500;
                display: inline-block;
                text-align: center;
                text-decoration: none;
                transition: all 0.3s ease;
            }

            .btn-back:hover {
                background-color: #1b4332;
                color: #ffffff;
            }



        </style>
    </head>
    <body>
        <div class="container">
            <h2>Chỉnh sửa lịch hẹn</h2>
            <div class="text-center">
                <div class="id-box">ID : ${appointment.id}</div>
            </div>

            <form action="ReceptionServlet" method="post">
                <input type="hidden" name="action" value="updateAppointment"/>
                <input type="hidden" name="id" value="${appointment.id}"/>

                <div class="mb-4">
                    <label class="form-label">Bệnh nhân(Không chỉnh sửa được bệnh nhân)</label>
                    <select name="patientId" class="form-select" disabled>
                        <c:forEach var="p" items="${patients}">
                            <option value="${p.id}" ${p.id == appointment.patient.id ? 'selected' : ''}>
                                ${p.fullName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-4">
                    <label class="form-label">Bác sĩ</label>
                    <select name="doctorId" class="form-select" >
                        <c:forEach var="d" items="${doctors}">
                            <option value="${d.id}" ${d.id == appointment.doctor.id ? 'selected' : ''}>
                                ${d.fullName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-4">
                    <label class="form-label">Ca khám (Ngày - Giờ)</label>
                    <select name="doctorShiftId" class="form-select">
                        <c:forEach var="s" items="${slots}">
                            <option value="${s.id}" ${s.id == appointment.shiftSlot.id ? 'selected' : ''}>
                                ${s.date} - ${s.slotStartTime}
                            </option>
                        </c:forEach>
                    </select>
                </div>


                <div class="mb-4">
                    <label class="form-label">Trạng thái</label>
                    <select name="status" class="form-select">
                        <option value="Pending" ${appointment.confirmationStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Đã xác nhận" ${appointment.confirmationStatus == 'Đã xác nhận' ? 'selected' : ''}>Đã xác nhận</option>
                        <option value="Đã hủy" ${appointment.confirmationStatus == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                    </select>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn-save">
                        <i class="fas fa-save me-1"></i> Lưu thay đổi
                    </button>
                    <a href="ReceptionServlet?action=viewAppointments" class="btn-back">
                        <i class="fas fa-arrow-left me-1"></i> Quay lại
                    </a>
                </div>
            </form>
        </div>
    </body>
</html>
