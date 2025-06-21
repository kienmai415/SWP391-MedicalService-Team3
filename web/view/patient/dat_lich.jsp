<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Đăng ký khám bệnh</title>
        <link rel="stylesheet" href="style.css" />
        <style>
            .btn-option {
                margin: 10px;
                padding: 10px 20px;
                font-weight: bold;
                background-color: #4CAF50;
                border: none;
                border-radius: 8px;
                color: white;
                cursor: pointer;
            }

            .form-section {
                display: none;
                margin-top: 20px;
            }

            .form-section.active {
                display: block;
            }
        </style>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #fff;
                padding: 20px;
            }

            form {
                max-width: 900px;
                margin: 0 auto;
            }

            .row {
                display: flex;
                gap: 20px;
                margin-bottom: 15px;
            }

            .col {
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            label {
                font-weight: bold;
                color: #007bff;
                margin-bottom: 5px;
            }

            input[type="text"],
            input[type="tel"],
            input[type="email"],
            input[type="date"],
            select,
            textarea {
                padding: 12px;
                border: 1px solid #ccc;
                border-radius: 6px;
                background-color: #f9f9f9;
                font-size: 14px;
            }

            textarea {
                resize: vertical;
                height: 100px;
            }

            .gender-options {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-top: 6px;
            }

            .note {
                font-size: 13px;
                color: #666;
                margin-bottom: 10px;
            }

            .checkbox {
                margin: 15px 0;
            }

            .checkbox input {
                margin-right: 6px;
            }

            .checkbox label {
                font-weight: normal;
                color: #000;
            }

            .checkbox a {
                color: #007bff;
                text-decoration: none;
            }

            .submit-btn {
                background-color: #50c2aa;
                color: white;
                border: none;
                padding: 15px 30px;
                border-radius: 30px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
            }

            .submit-btn:hover {
                background-color: #3bb498;
            }

            .checkbox {
                visibility: hidden;
                height: 20px;   /* hoặc đúng chiều cao ban đầu */
                margin: 0;
                padding: 0;
            }
            #fullName {
                width: 48%; /* hoặc 300px, tuỳ bạn muốn độ dài bao nhiêu */
            }
        </style>
        <!--        <script>
                    function showForm(option) {
                        document.getElementById("form-auto").classList.remove("active");
                        document.getElementById("form-doctor").classList.remove("active");
        
                        if (option === 'auto') {
                            document.getElementById("form-auto").classList.add("active");
                        } else {
                            document.getElementById("form-doctor").classList.add("active");
                        }
                    }
                </script>-->
    </head>
    <body>
        <div class="container">
            <h2>Đăng ký khám bệnh</h2>
            <c:if test="${not empty errorB}">
                <p style="color:red">${errorB}</p>
            </c:if>
            <!-- <button class="btn-option" onclick="showForm('auto')">Đặt lịch thường</button>
            <button class="btn-option" onclick="showForm('doctor')">Chọn bác sĩ</button> -->

            <!-- Đặt lịch tự động -->
            <div id="form-auto" class="form-section active" >
                <form action="patient" method="post">
                    <div class="row">
                        <div class="col">
                            <label for="fullName">Họ và tên<span style="color:red">*</span></label>
                            <input type="text" id="fullName" name="fullName" placeholder="Họ và tên" value="${p.fullName}"readonly>

                        </div>

                    </div>

                    <div class="row">
                        <div class="col">
                            <label for="phone">Số điện thoại<span style="color:red" >*</span></label>
                            <input type="tel" id="phone" name="phone"  value="${p.phoneNumber}" readonly>

                        </div>
                        <div class="col">
                            <label for="email">Email</label>
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
                            <label for="appointmentDate">Ngày đặt khám<span style="color:red">*</span></label>
                            <input type="date" id="appointmentDate" name="appointmentDate" min="<%= minDate %>"  max="<%= maxDate %>"required>
                        </div>

                        <div class="col">
                            <label for="appointmentTime">Giờ đặt khám<span style="color:red">*</span></label>
                            <select id="appointmentTime" name="appointmentTime" required>
                                <option value="">-- Chọn giờ --</option>
                                <option value="08:00 - 09:00">08:00 - 09:00</option>
                                <option value="09:00 - 10:00">09:00 - 10:00</option>
                                <option value="10:00 - 11:00">10:00 - 11:00</option>
                                <option value="11:00 - 12:00">11:00 - 12:00</option>
                                <option value="13:00 - 14:00">13:00 - 14:00</option>
                                <option value="14:00 - 15:00">14:00 - 15:00</option>
                                <option value="15:00 - 16:00">15:00 - 16:00</option>
                                <option value="16:00 - 17:00">16:00 - 17:00</option>
                            </select>
                        </div>
                    </div>
                    <c:if test="${not empty error}">
                        <p style="color:red">${error}</p>
                    </c:if>


                    <div class="row">
                        <div class="col" style="width: 100%;">
                            <label for="symptom">Lý do khám<span style="color:red">*</span></label>
                            <textarea id="symptom" name="symptom" placeholder="Triệu chứng của bạn" required></textarea>
                        </div>
                    </div>

                    <div class="checkbox">
                        <input type="checkbox" id="agree" name="agree" >
                        <label for="agree">
                            Tôi đã đọc và đồng ý với
                            <a href="#" target="_blank">Chính sách bảo vệ dữ liệu cá nhân</a>
                            và chấp thuận để xử lý dữ liệu cá nhân của tôi.
                        </label>
                    </div>
                    <div class="note">
                        *Lưu ý: Chúng chỉ liên hệ với Thuê bao nội địa. Nếu quý khách sử dụng thuê bao quốc tế, vui lòng bổ sung email chính xác để nhận mã xác nhận và thông tin xác nhận đặt lịch.
                    </div>

                    <button type="submit" class="submit-btn">Gửi thông tin</button>
                </form>
            </div>

            <!-- Chọn bác sĩ -->
            <!--    <div id="form-doctor" class="form-section">
                    <form action="BookingServlet" method="post">
                        <input type="hidden" name="option" value="doctor">
                        Ngày khám: <input type="date" name="date" required><br><br>
                        Khung giờ: 
                        <select name="timeslot">
                            <option value="08:00">08:00</option>
                            <option value="09:00">09:00</option>
                            <option value="10:00">10:00</option>
                        </select><br><br>
                        Chọn bác sĩ:
                        <select name="doctorId">
            <%-- Bạn lấy danh sách bác sĩ từ DB --%>
            <%-- Ví dụ tạm --%>
            <option value="1">BS. Nguyễn Thành Công - Nội khoa</option>
            <option value="2">BS. Trần Thị Mai - Nhi khoa</option>
        </select><br><br>
        <input type="submit" value="Đặt lịch với bác sĩ">
    </form>
</div> -->
        </div>
    </body>
</html>
