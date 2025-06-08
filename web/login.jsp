<%-- 
    Document   : login
    Created on : May 22, 2025, 4:37:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <!-- Swiper CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@8/swiper-bundle.min.css" />
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- Responsive quan trọng -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

        <style>
            :root {
                --primary: #2ecc71;
                --primary-dark: #27ae60;
                --primary-light: #a9dfbf;
                --primary-very-light: #eafaf1;
                --secondary: #3498db;
                --dark: #2c3e50;
                --light: #ecf0f1;
                --gray: #95a5a6;
                --success: #27ae60;
                --info: #3498db;
                --warning: #f39c12;
                --danger: #e74c3c;
                --white: #ffffff;
                --black: #000000;
            }

            body {
                font-family: 'Poppins', sans-serif;
                color: var(--dark);
                line-height: 1.7;
                overflow-x: hidden;
                background-color: var(--primary-very-light);
            }
            .auth-box {
                background-color: var(--white);
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 0 15px rgba(0,0,0,0.1);
                max-width: 400px;
                margin: auto;
                margin-top: 60px;
            }
            .auth-tabs {
                display: flex;
                justify-content: space-around;
                margin-bottom: 20px;
                border-bottom: none;
            }
            .auth-tab {
                padding: 10px 20px;
                font-weight: 500;
                color: #6c757d;
                position: relative;
                cursor: pointer;
                border-bottom: 2px solid transparent;
            }
            .auth-tab.active {
                color: var(--primary);
                border-bottom: 2px solid var(--primary);
            }
            .auth-form {
                display: none;
            }
            .auth-form.active {
                display: block;
            }
            .auth-separator {
                text-align: center;
                margin: 20px 0;
                position: relative;
            }
            .auth-separator span {
                display: inline-block;
                width: 30%;
                height: 1px;
                background: #ccc;
                vertical-align: middle;
            }
            .auth-separator p {
                display: inline-block;
                margin: 0 10px;
                color: #888;
            }
            .social-login {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
            }
            .social-login-btn {
                padding: 8px 20px;
                background-color: #dd4b39;
                color: white;
                border-radius: 4px;
                text-decoration: none;
                font-weight: 500;
                display: flex;
                align-items: center;
                justify-content: center;
                width: 100%;
                max-width: 200px;
                margin: 0 auto;
            }
            .social-login-btn:hover {
                background-color: #c23321;
                color: white;
            }
            .form-check {
                font-size: 14px;
                padding-left: 0; /* bỏ khoảng trống mặc định */
                margin-bottom: 10px;
            }

            .form-check a {
                color: var(--primary);
            }

            .form-check a:hover {
                color: var(--primary-dark);
            }

            /* Responsive chỉnh lại text và padding nhỏ hơn cho mobile */
            @media (max-width: 576px) {
                .auth-box {
                    padding: 20px;
                    margin: 30px auto;
                }
                .auth-tab {
                    font-size: 14px;
                    padding: 8px 10px;
                }
                .form-label, .form-control, .btn {
                    font-size: 14px;
                }
            }
            .toast {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #4BB543; /* xanh lá thành công */
                color: #2ecc71;
                padding: 15px 20px;
                border-radius: 5px;
                box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
                z-index: 9999;
                animation: fadein 0.5s, fadeout 0.5s 3s;
            }

            @keyframes fadein {
                from {
                    opacity: 0;
                    right: 0;
                }
                to {
                    opacity: 1;
                    right: 20px;
                }
            }

            @keyframes fadeout {
                from {
                    opacity: 1;
                }
                to {
                    opacity: 0;
                }
            }

            .btn-primary {
                background-color: var(--primary);
                border-color: var(--primary);
                border-radius: 8px;
                padding: 12px;
                font-weight: 500;
                width: 100%;
            }

            .btn-primary:hover {
                background-color: var(--primary-dark);
                border-color: var(--primary-dark);
            }

            .form-control {
                border: 1px solid #dee2e6;
                border-radius: 8px;
                padding: 12px;
                margin-bottom: 1rem;
            }

            .form-control:focus {
                border-color: var(--primary);
                box-shadow: 0 0 0 0.2rem rgba(46, 204, 113, 0.1);
            }

            .form-label {
                color: var(--dark);
                margin-bottom: 0.5rem;
                font-weight: 500;
            }

            .text-primary {
                color: var(--primary) !important;
            }

            a {
                color: var(--primary);
                text-decoration: none;
            }

            a:hover {
                color: var(--primary-dark);
            }

            /* Nút Hủy trong form quên mật khẩu */
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
                color: white;
                border-radius: 8px;
                padding: 12px 24px;
            }

            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #545b62;
            }

            /* Container cho nút Hủy và Gửi yêu cầu */
            .forgot-password-buttons {
                display: flex;
                gap: 1rem;
                margin-top: 1rem;
            }

            .forgot-password-buttons .btn {
                flex: 1;
            }

            /* Forgot password specific styles */
            .btn-forgot {
                background-color: var(--primary);
                border-color: var(--primary);
                border-radius: 8px;
                padding: 12px;
                font-weight: 500;
                width: 100%;
                color: white;
            }

            .btn-forgot:hover {
                background-color: var(--primary-dark);
                border-color: var(--primary-dark);
            }

            .back-to-login {
                color: var(--primary);
                text-decoration: none;
                text-align: center;
                display: block;
                margin-top: 1rem;
            }

            .back-to-login:hover {
                text-decoration: none;
                color: var(--primary-dark);
            }

            .forgot-title {
                color: var(--dark);
                font-size: 1.5rem;
                text-align: center;
                margin-bottom: 1.5rem;
            }

            .forgot-description {
                color: #6c757d;
                text-align: center;
                margin-bottom: 1.5rem;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="auth-box">
                <h5 class="text-center mb-4">Chào mừng bạn trở lại</h5>

                <div class="auth-tabs">
                    <div class="auth-tab active" onclick="switchTab('login')">Đăng nhập</div>
                    <div class="auth-tab" onclick="switchTab('register')">Đăng ký</div>
                </div>

                <!-- Đăng nhập -->
                <form id="login-form" class="auth-form active" action="login" method="post">
                    <div class="mb-3">
                        <label for="login-email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="login-email" name="email" placeholder="Nhập email của bạn" required autofocus>
                    </div>
                    <div class="mb-3">
                        <label for="login-password" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="login-password" name="password" placeholder="Nhập mật khẩu" required>
                        <div>
                            <%String error = (String) request.getAttribute("error");%>
                            <%if (error != null){%>
                            <span style="color:red;"><%= error %></span>
                            <%}%>
                        </div>
                        <div class="d-flex align-items-center mt-2">
                            <input type="checkbox" class="form-check-input me-2" id="showPassword" onclick="togglePassword()">
                            <label class="form-check-label me-auto" for="showPassword">Hiện mật khẩu</label>

                            <!-- Quên mật khẩu căn phải -->
                            <a href="#" onclick="switchTab('forgot')" class="small text-decoration-none text-primary">Quên mật khẩu?</a>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>

                    <div class="auth-separator">
                        <p>Hoặc đăng nhập với</p>
                    </div>
                    <div class="social-login">
                        <a href="
                           https://accounts.google.com/o/oauth2/auth?scope=profile&redirect_uri=http://localhost:8080/SWP391-MedicalHealthCareSystem/login&response_type=code
                           &client_id=142595737619-8t2hueqpat0ecuruktrp8o8k83edccbl.apps.googleusercontent.com&approval_prompt=force" class="social-login-btn google">

                            <i class="bi bi-google me-2"></i> Google
                        </a>
                    </div>
                    <div class="mt-3 text-center">
                        <p>Bạn chưa có tài khoản? <a href="#" onclick="switchTab('register')">Đăng ký ngay</a></p>
                    </div>
                </form>

                <!-- Đăng ký -->
                <form id="register-form" class="auth-form" action="register" method="post" onsubmit="return validateAll()">
                    <div>
                        <%
                        String errorr = (String) request.getAttribute("errorP");
                                if (errorr != null) {
                        %>
                        <p style="color: red;"><%= errorr %></p>
                        <%
                            }
                        %>
                    </div>
                    <div class="mb-3">
                        <label for="register-name" class="form-label">Họ và tên</label>
                        <input type="text" class="form-control" id="register-name" name="name" onkeyup="validateForm(this.id, 'name')" placeholder="Nhập họ và tên" required autofocus>
                        <div id="register-name_error" class="text-danger"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="register-email" name="email" onkeyup="validateForm(this.id, 'email')" placeholder="Nhập email của bạn" required>
                        <div id="register-email_error" class="text-danger"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-phone" class="form-label">Số điện thoại</label>
                        <input type="tel" class="form-control" id="register-phone" name="phone" onkeyup="validateForm(this.id, 'phone')" placeholder="Nhập số điện thoại" required>
                        <div id="register-phone_error" class="text-danger"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-password" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="register-password" name="password" onkeyup="validateForm(this.id, 'password')" placeholder="Nhập mật khẩu" required>
                        <div id="register-password_error" class="text-danger"></div>
                    </div>

                    <div class="mb-3">
                        <label for="register-confirm-password" class="form-label">Nhập lại mật khẩu</label>
                        <input type="password" class="form-control" id="register-confirm-password" name="confirm-password" onkeyup="validateForm(this.id, 'password-conf')" placeholder="Nhập lại mật khẩu" required>
                        <div id="register-confirm-password_error" class="text-danger"></div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
                    <div class="mt-3 text-center">
                        <p>Bạn đã có tài khoản? <a href="#" onclick="switchTab('login')">Đăng nhập</a></p>
                    </div>
                </form>

                <!-- Quên mật khẩu -->
                <form id="forgot-form" class="auth-form" action="${pageContext.request.contextPath}/forgotpassword" method="post">
                    <p class="forgot-description">Vui lòng nhập email của bạn để nhận hướng dẫn đặt lại mật khẩu.</p>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" class="form-control" id="forgot-email" name="email" placeholder="Nhập email của bạn" required>
                    </div>
                    <div>
                        <%
                        String errorrr = (String) request.getAttribute("errorP");
                                if (errorrr != null) {
                        %>
                        <p style="color: red;"><%= errorrr %></p>
                        <%
                            }
                        %>
                    </div>
                    <button type="submit" class="btn btn-forgot">Gửi yêu cầu</button>
                    <a href="#" onclick="switchTab('login')" class="back-to-login">Quay lại đăng nhập</a>
                </form>
            </div>
        </div>

        <script>
            function switchTab(tab) {
                document.querySelectorAll('.auth-form').forEach(f => f.classList.remove('active'));
                document.querySelectorAll('.auth-tab').forEach(t => t.classList.remove('active'));

                if (tab === 'login') {
                    document.getElementById('login-form').classList.add('active');
                    document.querySelector('.auth-tab:nth-child(1)').classList.add('active');
                } else if (tab === 'register') {
                    document.getElementById('register-form').classList.add('active');
                    document.querySelector('.auth-tab:nth-child(2)').classList.add('active');
                } else if (tab === 'forgot') {
                    document.getElementById('forgot-form').classList.add('active');
                }
            }

            function togglePassword() {
                const pass = document.getElementById('login-password');
                pass.type = pass.type === 'password' ? 'text' : 'password';
            }
        </script>

        <!-- validate register -->
        <script >
            function validateForm(id, type) {
                const  input = document.getElementById(id);
                const errorDiv = document.getElementById(id + "_error");
                const  value = input.value.trim();

                let msg = "";
                switch (type) {
                    case "name":
                        if (!value) {
                            msg = "Họ tên chưa nhập!";
                        } else if (value.length < 3 || value.length > 50) {
                            msg = "Họ tên phải có ít nhất 2 kí tự và không vượt quá 50 kí tự!";
                        }
                        break;

                    case "email":
                        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                        if (!value)
                            msg = "Vui lòng nhập email!";
                        else if (!emailRegex.test(value))
                            msg = "Email không hợp lệ!";
                        break;

                    case "CCCD":
                        const cccdRegex = /^\d{12}$/;
                        if (!value)
                            msg = "Vui lòng nhập số căn cước công dân!";
                        else if (!cccdRegex.test(value))
                            msg = "Căn cước công dân không hợp lệ!";
                        break;
                    case "BHYT":
                        const bhytRegex = /^[A-Z]{2}\d{13}$|^[A-Z]{2}\d{8}$/;
                        if (!value)
                            msg = "Vui lòng nhập số bảo hiểm y tế!";
                        else if (!bhytRegex.test(value))
                            msg = "Số bảo hiểm y tế không hợp lệ!";
                        break;
                    case "phone":
                        const phoneRegex = /^(0|\+84)[0-9]{9}$/;
                        if (!value)
                            msg = "Vui lòng nhập số điện thoại!";
                        else if (!phoneRegex.test(value))
                            msg = "SĐT không hợp lệ!";
                        break;
                    case "password":
                        if (!value)
                            msg = "Vui lòng nhập mật khẩu.";
                        else if (value.length < 6) {
                            msg = "Mật khẩu phải có ít nhất 6 ký tự.";
                        }
                        validateForm("register-confirm-password", "password-conf");
                        break;
                    case "password-conf":
                        const  pass = document.getElementById("register-password");
                        if (pass.value.trim() !== value)
                            msg = "Mật khẩu không trùng khớp!";
                        break;
                    default:
                        msg = "";
                }

                if (msg) {
                    errorDiv.textContent = msg;
                    input.classList.add("is-invalid");
                } else {
                    errorDiv.textContent = "";
                    input.classList.remove("is-invalid");
                }
            }

        </script>
        <script>
            function validateAll() {
                validateForm("register-name", "name");
                validateForm("register-email", "email");
                validateForm("register-phone", "phone");
                validateForm("register-password", "password");
                validateForm("register-confirm-password", "password-conf");

                const invalids = document.querySelectorAll(".is-invalid");
                return invalids.length === 0;
            }
        </script>

        <%
        String activeTab = (String) request.getAttribute("activeTab");
        %>

        <script>
            window.onload = function () {
                // Mặc định là tab login
                let tab = 'login';
                // Nếu có tab từ request (Servlet setAttribute)
            <% if (request.getAttribute("tab") != null) { %>
                tab = '<%= request.getAttribute("tab") %>';
            <% } else if ("register".equals(activeTab)) { %>
                tab = 'register';
            <% } %>

                // Gọi hàm để hiển thị đúng tab
                switchTab(tab);
            };

        </script>

    </body>
</html>
