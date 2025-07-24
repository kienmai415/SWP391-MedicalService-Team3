<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Đăng ký khám bệnh</title>
        <link rel="stylesheet" href="style.css" />
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f6f7;
                margin: 0;
                padding: 0;
                display: flex;
            }

            /* Sidebar giả định */
            .sidebar {
                width: 250px;
                background-color: #4CAF50;
                height: 100vh;
                color: white;
                padding: 20px;
            }

            /* Container mở rộng phần còn lại */
            .container {
                flex: 1;
                padding: 40px 80px;
                background-color: #fff;
                min-height: 100vh;
                box-sizing: border-box;
                border-radius: 30px;
                width: 168%;
            }

            h2 {
                text-align: center;
                color: #2b8a3e;
                font-size: 32px;
                font-weight: bold;
                margin-bottom: 40px;
            }

            .row {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                margin-bottom: 20px;
            }

            .col {
                flex: 1;
                display: flex;
                flex-direction: column;
                min-width: 250px;
            }

            label {
                font-weight: bold;
                color: #1e88e5;
                margin-bottom: 6px;
            }

            input[type="text"],
            input[type="tel"],
            input[type="email"],
            input[type="date"],
            select,
            textarea {
                padding: 14px 12px;
                border: 1px solid #ccc;
                border-radius: 8px;
                background-color: #f1f1f1;
                font-size: 15px;
                transition: border 0.3s, background-color 0.3s;
                width: 100%;
            }

            input:focus,
            select:focus,
            textarea:focus {
                outline: none;
                border-color: #4CAF50;
                background-color: #fff;
            }

            textarea {
                resize: vertical;
                height: 120px;
            }

            .submit-btn {
                background-color: #50c2aa;
                color: white;
                border: none;
                padding: 16px 40px;
                border-radius: 40px;
                font-size: 17px;
                font-weight: bold;
                cursor: pointer;
                transition: background-color 0.3s ease;
                display: block;
                margin: 30px auto 0;
            }

            .submit-btn:hover {
                background-color: #3bb498;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h2>Đăng ký khám bệnh</h2>

            <c:if test="${not empty errorB}">
                <p style="color:red">${errorB}</p>
            </c:if>

            <form action="VNPayServlet" method="post">
                <div class="row">
                    <div class="col">
                        <label for="fullName">Họ và tên<span style="color:red">*</span></label>
                        <input type="text" id="fullName" name="fullName" placeholder="Họ và tên" value="${p.fullName}" readonly>
                    </div>

                    <div class="col">
                        <label for="doctorId">Chọn bác sĩ</label>
                        <select name="doctorId" id="doctorId" required>
                            <option value="">-- Chọn bác sĩ --</option>
                            <c:forEach var="d" items="${listDoctors}">
                                <option value="${d.id}">${d.fullName} - ${d.specialization.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <label for="phone">Số điện thoại<span style="color:red">*</span></label>
                        <input type="tel" id="phone" name="phone" value="${p.phoneNumber}" readonly>
                    </div>

                    <div class="col">
                        <label for="email">Email<span style="color:red">*</span></label>
                        <input type="email" id="email" name="email" value="${p.email}" readonly>
                    </div>
                </div>

                <%
                   java.time.LocalDate today = java.time.LocalDate.now();
                   java.time.LocalDate minDate = today.plusDays(2);
                   java.time.LocalDate maxDate = today.plusDays(7);
                %>

                <div class="row">
                    <div class="col">
                        <label for="appointmentDate">Ngày đặt khám</label>
                        <input type="date" id="appointmentDate" name="appointmentDate" min="<%= minDate %>" max="<%= maxDate %>" required>
                    </div>

                    <div class="col">
                        <label for="appointmentTime">Giờ đặt khám</label>
                        <select id="appointmentTime" name="appointmentTime" required>
                            <option value="">-- Chọn giờ --</option>
                            <option value="08:00">08:00</option>
                            <option value="09:00">09:00</option>
                            <option value="10:00">10:00</option>
                            <option value="11:00">11:00</option>
                            <option value="13:00">13:00</option>
                            <option value="14:00">14:00</option>
                            <option value="15:00">15:00</option>
                            <option value="16:00">16:00</option>
                        </select>
                    </div>
                </div>

                <c:if test="${not empty error}">
                    <p style="color:red">${error}</p>
                </c:if>

                <div class="row">
                    <div class="col" style="width: 100%;">
                        <label for="symptom">Lý do khám</label>
                        <textarea id="symptom" name="symptom" placeholder="Triệu chứng của bạn" required></textarea>
                    </div>
                </div>

                <div class="note" style="text-align:center; font-weight:bold; color:#e53935;">
                    Vui lòng thanh toán 200.000 VND để hoàn tất đăng ký khám bệnh
                </div>

                <button type="submit" class="submit-btn">Thanh toán</button>
            </form>

        </div>
    </div>
</body>
</html>
