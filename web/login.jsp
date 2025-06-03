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
                background-color: #f9f9f9;
            }

            h1, h2, h3, h4, h5, h6 {
                font-weight: 700;
                line-height: 1.3;
            }

            .btn {
                border-radius: 50px;
                padding: 12px 30px;
                font-weight: 500;
                transition: all 0.3s ease;
                position: relative;
                overflow: hidden;
                z-index: 1;
            }

            .btn::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 0;
                background-color: rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                z-index: -1;
            }

            .btn:hover::after {
                height: 100%;
            }

            .btn-primary {
                background-color: var(--primary);
                border-color: var(--primary);
                color: var(--white);
                box-shadow: 0 5px 15px rgba(46, 204, 113, 0.3);
            }

            .btn-primary:hover, .btn-primary:focus {
                background-color: var(--primary-dark);
                border-color: var(--primary-dark);
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(46, 204, 113, 0.4);
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-control, .form-select {
                height: 55px;
                border-radius: 10px;
                border: 1px solid #e0e0e0;
                padding: 10px 20px;
                font-size: 1rem;
                transition: all 0.3s ease;
            }

            .form-control:focus, .form-select:focus {
                border-color: var(--primary);
                box-shadow: 0 0 0 0.25rem rgba(46, 204, 113, 0.25);
            }

            .form-label {
                font-weight: 500;
                margin-bottom: 10px;
                color: var(--dark);
            }

            .modal-content {
                border-radius: 20px;
                border: none;
                overflow: hidden;
            }

            .modal-header {
                border-bottom: none;
                padding: 30px 30px 0;
            }

            .modal-body {
                padding: 30px;
            }

            .modal-footer {
                border-top: none;
                padding: 0 30px 30px;
                justify-content: center;
            }

            .modal-title {
                font-size: 1.8rem;
                font-weight: 700;
                color: var(--dark);
            }

            .auth-tabs {
                display: flex;
                margin-bottom: 30px;
            }

            .auth-tab {
                flex: 1;
                text-align: center;
                padding: 15px;
                font-weight: 600;
                color: var(--gray);
                cursor: pointer;
                transition: all 0.3s ease;
                border-bottom: 2px solid transparent;
            }

            .auth-tab.active {
                color: var(--primary);
                border-bottom-color: var(--primary);
            }

            .auth-form {
                margin-bottom: 20px;
            }

            .auth-form .form-group {
                margin-bottom: 20px;
            }

            .auth-form .form-control {
                height: 55px;
                border-radius: 10px;
                border: 1px solid #e0e0e0;
                padding: 10px 20px;
                font-size: 1rem;
                transition: all 0.3s ease;
            }

            .auth-form .form-control:focus {
                border-color: var(--primary);
                box-shadow: 0 0 0 0.25rem rgba(46, 204, 113, 0.25);
            }

            .auth-form .btn {
                width: 100%;
                padding: 15px;
            }

            .auth-form .forgot-password {
                text-align: right;
                margin-bottom: 20px;
            }

            .auth-form .forgot-password a {
                color: var(--primary);
                text-decoration: none;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .auth-form .forgot-password a:hover {
                color: var(--primary-dark);
            }

            .auth-separator {
                display: flex;
                align-items: center;
                margin: 30px 0;
            }

            .auth-separator span {
                flex: 1;
                height: 1px;
                background-color: #e0e0e0;
            }

            .auth-separator p {
                margin: 0 15px;
                color: var(--gray);
                font-weight: 500;
            }

            .social-login {
                display: flex;
                gap: 15px;
            }

            .social-login-btn {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                height: 55px;
                border-radius: 10px;
                border: 1px solid #e0e0e0;
                background-color: var(--white);
                color: var(--dark);
                font-weight: 500;
                transition: all 0.3s ease;
                text-decoration: none;
            }

            .social-login-btn i {
                margin-right: 10px;
                font-size: 1.2rem;
            }

            .social-login-btn:hover {
                background-color: #f5f5f5;
                transform: translateY(-3px);
            }

            .social-login-btn.facebook {
                color: #3b5998;
            }

            .social-login-btn.google {
                color: #db4437;
            }

            .auth-footer {
                text-align: center;
                margin-top: 20px;
            }

            .auth-footer p {
                color: var(--gray);
                margin-bottom: 0;
            }

            .auth-footer a {
                color: var(--primary);
                text-decoration: none;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .auth-footer a:hover {
                color: var(--primary-dark);
            }

            /* Responsive */
            @media (max-width: 991.98px) {
                .hero-section {
                    padding: 150px 0 100px;
                }

                .hero-section h1 {
                    font-size: 2.5rem;
                }

                .section-padding {
                    padding: 80px 0;
                }

                .section-title h2 {
                    font-size: 2rem;
                }

                .about-content h2 {
                    font-size: 2rem;
                }

                .counter-number {
                    font-size: 2rem;
                }

                .cta-content h2 {
                    font-size: 2rem;
                }

                .navbar-nav {
                    background-color: var(--white);
                    padding: 20px;
                    border-radius: 10px;
                    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                }
            }

            @media (max-width: 767.98px) {
                .hero-section {
                    padding: 120px 0 80px;
                }

                .hero-section h1 {
                    font-size: 2rem;
                }

                .section-padding {
                    padding: 60px 0;
                }

                .section-title h2 {
                    font-size: 1.8rem;
                }

                .about-content h2 {
                    font-size: 1.8rem;
                }

                .counter-number {
                    font-size: 1.8rem;
                }

                .cta-content h2 {
                    font-size: 1.8rem;
                }

                .footer-bottom-links {
                    text-align: left;
                    margin-top: 15px;
                }

                .footer-bottom-links a {
                    margin-left: 0;
                    margin-right: 20px;
                }

                .appointment-form {
                    padding: 30px;
                }
            }

            @media (max-width: 575.98px) {
                .hero-section {
                    padding: 100px 0 60px;
                }

                .hero-section h1 {
                    font-size: 1.8rem;
                }

                .section-padding {
                    padding: 50px 0;
                }

                .section-title h2 {
                    font-size: 1.5rem;
                }

                .about-content h2 {
                    font-size: 1.5rem;
                }

                .counter-number {
                    font-size: 1.5rem;
                }

                .cta-content h2 {
                    font-size: 1.5rem;
                }

                .appointment-form {
                    padding: 20px;
                }

                .testimonial-card {
                    padding: 30px;
                }
            }
        </style>
    </head>
    <body>
        <!-- Login Modal -->
        <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <form action="login" method="get">
                        <div class="modal-header">
                            <h5 class="modal-title" id="loginModalLabel">Chào mừng bạn trở lại</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="auth-tabs">
                                <div class="auth-tab active" id="login-tab">Đăng nhập</div>
                                <div class="auth-tab" id="register-tab">Đăng ký</div>
                            </div>

                            <!-- Form Đăng Nhập -->
                            <div id="login-form" class="auth-form">
                                <div class="form-group">
                                    <label for="login-email" class="form-label">Email</label>
                                    <input name="nemail" type="email" class="form-control" id="login-email" name="username" placeholder="Nhập email của bạn" required>
                                </div>
                                <div class="form-group">
                                    <label for="login-password" class="form-label">Mật khẩu</label>
                                    <input name="npass" type="password" class="form-control" id="login-password" name="password" placeholder="Nhập mật khẩu" required>
                                </div>
                                <div class="forgot-password">
                                    <a href="#" data-bs-toggle="modal" data-bs-target="#forgotPasswordModal" data-bs-dismiss="modal">Quên mật khẩu?</a>
                                </div>
                                <button type="submit" class="btn btn-primary w-100" >Đăng nhập</button>
                                <div class="auth-separator text-center mt-3">
                                    <span></span>
                                    <p>Hoặc đăng nhập với</p>
                                    <span></span>
                                </div>

                                <div class="social-login d-flex justify-content-around">
                                    <a href="#" class="social-login-btn btn btn-outline-primary">
                                        <i class="bi bi-facebook"></i> Facebook
                                    </a>
                                    <a href="#" class="social-login-btn btn btn-outline-danger">
                                        <i class="bi bi-google"></i> Google
                                    </a>
                                </div>
                            </div>

                            <!-- Form Đăng Ký -->
                            <div id="register-form" class="auth-form" style="display: none;">
                                <div class="form-group">
                                    <label for="register-name" class="form-label">Họ và tên</label>
                                    <input type="text" class="form-control" id="register-name" placeholder="Nhập họ và tên">
                                </div>
                                <div class="form-group">
                                    <label for="register-email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="register-email" placeholder="Nhập email của bạn">
                                </div>
                                <div class="form-group">
                                    <label for="register-phone" class="form-label">Số điện thoại</label>
                                    <input type="tel" class="form-control" id="register-phone" placeholder="Nhập số điện thoại">
                                </div>
                                <div class="form-group">
                                    <label for="register-password" class="form-label">Mật khẩu</label>
                                    <input type="password" class="form-control" id="register-password" placeholder="Nhập mật khẩu">
                                </div>
                                <div class="form-group">
                                    <label for="register-confirm-password" class="form-label">Xác nhận mật khẩu</label>
                                    <input type="password" class="form-control" id="register-confirm-password" placeholder="Nhập lại mật khẩu">
                                </div>
                                <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="auth-footer w-100 text-center">
                                <p id="login-footer">Bạn chưa có tài khoản? <a href="#" id="show-register">Đăng ký ngay</a></p>
                                <p id="register-footer" style="display: none;">Bạn đã có tài khoản? <a href="#" id="show-login">Đăng nhập</a></p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>

    <script>
        const loginTab = document.getElementById('login-tab');
        const registerTab = document.getElementById('register-tab');
        const loginForm = document.getElementById('login-form');
        const registerForm = document.getElementById('register-form');
        const loginFooter = document.getElementById('login-footer');
        const registerFooter = document.getElementById('register-footer');
        const showRegister = document.getElementById('show-register');
        const showLogin = document.getElementById('show-login');

        loginTab.addEventListener('click', () => {
            loginTab.classList.add('active');
            registerTab.classList.remove('active');
            loginForm.style.display = 'block';
            registerForm.style.display = 'none';
            loginFooter.style.display = 'block';
            registerFooter.style.display = 'none';
        });

        registerTab.addEventListener('click', () => {
            registerTab.classList.add('active');
            loginTab.classList.remove('active');
            registerForm.style.display = 'block';
            loginForm.style.display = 'none';
            registerFooter.style.display = 'block';
            loginFooter.style.display = 'none';
        });

        showRegister.addEventListener('click', (e) => {
            e.preventDefault();
            registerTab.click();
        });

        showLogin.addEventListener('click', (e) => {
            e.preventDefault();
            loginTab.click();
        });
        
    </script>
</form>
</div>
</body>
</html>
