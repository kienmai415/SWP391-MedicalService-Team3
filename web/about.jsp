<%-- 
    Document   : about
    Created on : May 22, 2025, 4:39:43 PM
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

            .section-padding {
                padding: 100px 0;
            }

            .feature-icon {
                width: 80px;
                height: 80px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: var(--primary-very-light);
                color: var(--primary);
                border-radius: 20px;
                margin-bottom: 25px;
                font-size: 2rem;
                transition: all 0.3s ease;
            }

            .feature-box:hover .feature-icon {
                background-color: var(--primary);
                color: var(--white);
                transform: rotateY(180deg);
            }

            .feature-box h4 {
                font-size: 1.5rem;
                margin-bottom: 15px;
                font-weight: 600;
            }

            .feature-box p {
                color: var(--gray);
                margin-bottom: 0;
            }

            .counter-box {
                background-color: var(--white);
                border-radius: 20px;
                padding: 30px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
                text-align: center;
                transition: all 0.3s ease;
                height: 100%;
            }

            .counter-box:hover {
                transform: translateY(-10px);
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            }

            .counter-icon {
                width: 70px;
                height: 70px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: var(--primary-very-light);
                color: var(--primary);
                border-radius: 20px;
                margin: 0 auto 20px;
                font-size: 1.8rem;
                transition: all 0.3s ease;
            }

            .counter-box:hover .counter-icon {
                background-color: var(--primary);
                color: var(--white);
                transform: rotateY(180deg);
            }

            .counter-number {
                font-size: 2.5rem;
                font-weight: 700;
                color: var(--dark);
                margin-bottom: 10px;
            }

            .counter-text {
                color: var(--gray);
                font-weight: 500;
                margin-bottom: 0;
            }

            .about-img {
                position: relative;
            }

            .about-img img {
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            }

            .about-img::before {
                content: '';
                position: absolute;
                top: -20px;
                left: -20px;
                width: 100px;
                height: 100px;
                border-radius: 20px;
                background-color: var(--primary-very-light);
                z-index: -1;
            }

            .about-img::after {
                content: '';
                position: absolute;
                bottom: -20px;
                right: -20px;
                width: 100px;
                height: 100px;
                border-radius: 20px;
                background-color: var(--primary-very-light);
                z-index: -1;
            }

            .about-content h2 {
                font-size: 2.5rem;
                font-weight: 700;
                margin-bottom: 20px;
                position: relative;
                padding-bottom: 20px;
            }

            .about-content h2::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 80px;
                height: 3px;
                background-color: var(--primary);
            }

            .about-content p {
                font-size: 1.1rem;
                color: var(--gray);
                margin-bottom: 30px;
            }

            .about-feature {
                display: flex;
                align-items: flex-start;
                margin-bottom: 30px;
            }

            .about-feature-icon {
                width: 60px;
                height: 60px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: var(--primary-very-light);
                color: var(--primary);
                border-radius: 50%;
                margin-right: 20px;
                font-size: 1.5rem;
                flex-shrink: 0;
                transition: all 0.3s ease;
            }

            .about-feature:hover .about-feature-icon {
                background-color: var(--primary);
                color: var(--white);
                transform: rotateY(180deg);
            }

            .about-feature-content h5 {
                font-size: 1.2rem;
                margin-bottom: 10px;
                font-weight: 600;
            }

            .about-feature-content p {
                color: var(--gray);
                margin-bottom: 0;
                font-size: 1rem;
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

            .footer {
                background-color: var(--dark);
                color: var(--white);
                position: relative;
                overflow: hidden;
            }

            .footer::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
                opacity: 0.1;
            }

            .footer-logo {
                font-size: 2rem;
                font-weight: 700;
                color: var(--white);
                margin-bottom: 20px;
                display: inline-block;
            }

            .footer-logo i {
                color: var(--primary);
                margin-right: 10px;
            }

            .footer-text {
                color: rgba(255, 255, 255, 0.7);
                margin-bottom: 30px;
            }

            .footer-social {
                margin-bottom: 30px;
            }

            .footer-social a {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 40px;
                height: 40px;
                background-color: rgba(255, 255, 255, 0.1);
                color: var(--white);
                border-radius: 50%;
                margin-right: 10px;
                transition: all 0.3s ease;
            }

            .footer-social a:hover {
                background-color: var(--primary);
                transform: translateY(-5px);
            }

            .footer-title {
                font-size: 1.5rem;
                font-weight: 600;
                color: var(--white);
                margin-bottom: 30px;
                position: relative;
                padding-bottom: 15px;
            }

            .footer-title::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 50px;
                height: 2px;
                background-color: var(--primary);
            }

            .footer-links {
                list-style: none;
                padding-left: 0;
                margin-bottom: 0;
            }

            .footer-links li {
                margin-bottom: 15px;
            }

            .footer-links li a {
                color: rgba(255, 255, 255, 0.7);
                text-decoration: none;
                transition: all 0.3s ease;
                display: inline-block;
            }

            .footer-links li a:hover {
                color: var(--primary);
                transform: translateX(5px);
            }

            .footer-links li a i {
                margin-right: 10px;
                color: var(--primary);
            }

            .footer-newsletter p {
                color: rgba(255, 255, 255, 0.7);
                margin-bottom: 20px;
            }

            .footer-newsletter .form-control {
                background-color: rgba(255, 255, 255, 0.1);
                border: none;
                color: var(--white);
                height: 50px;
            }

            .footer-newsletter .form-control::placeholder {
                color: rgba(255, 255, 255, 0.5);
            }

            .footer-newsletter .btn {
                height: 50px;
                padding: 0 20px;
            }

            .footer-bottom {
                border-top: 1px solid rgba(255, 255, 255, 0.1);
                padding-top: 30px;
                margin-top: 60px;
            }

            .footer-bottom p {
                color: rgba(255, 255, 255, 0.7);
                margin-bottom: 0;
            }

            .footer-bottom-links {
                text-align: right;
            }

            .footer-bottom-links a {
                color: rgba(255, 255, 255, 0.7);
                text-decoration: none;
                margin-left: 20px;
                transition: all 0.3s ease;
            }

            .footer-bottom-links a:hover {
                color: var(--primary);
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

            .navbar {
                padding: 20px 0;
                transition: all 0.3s ease;
                background-color: transparent;
            }

            .navbar-scrolled {
                background-color: var(--white);
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
                padding: 15px 0;
            }

            .navbar-brand {
                font-weight: 700;
                font-size: 1.8rem;
                color: var(--primary);
            }

            .navbar-brand span {
                color: var(--dark);
            }

            .navbar-brand i {
                color: var(--primary);
                font-size: 2rem;
                margin-right: 10px;
            }

            .navbar-nav .nav-link {
                font-weight: 500;
                color: var(--dark);
                padding: 10px 15px;
                position: relative;
                transition: all 0.3s ease;
            }

            .navbar-nav .nav-link::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 50%;
                width: 0;
                height: 2px;
                background-color: var(--primary);
                transition: all 0.3s ease;
                transform: translateX(-50%);
            }

            .navbar-nav .nav-link:hover::after,
            .navbar-nav .nav-link.active::after {
                width: 70%;
            }

            .navbar-nav .nav-link:hover,
            .navbar-nav .nav-link.active {
                color: var(--primary);
            }

            .navbar-toggler {
                border: none;
                padding: 0;
            }

            .navbar-toggler:focus {
                box-shadow: none;
            }

            .navbar-toggler-icon {
                width: 30px;
                height: 20px;
                position: relative;
                transition: all 0.3s ease;
            }

            .navbar-toggler-icon::before,
            .navbar-toggler-icon::after {
                content: '';
                position: absolute;
                width: 100%;
                height: 2px;
                background-color: var(--dark);
                left: 0;
                transition: all 0.3s ease;
            }

            .navbar-toggler-icon::before {
                top: 0;
            }

            .navbar-toggler-icon::after {
                bottom: 0;
            }

            .navbar-toggler-icon .middle-bar {
                position: absolute;
                width: 100%;
                height: 2px;
                background-color: var(--dark);
                left: 0;
                top: 50%;
                transform: translateY(-50%);
                transition: all 0.3s ease;
            }

            .navbar-toggler[aria-expanded="true"] .navbar-toggler-icon::before {
                transform: rotate(45deg);
                top: 9px;
            }

            .navbar-toggler[aria-expanded="true"] .navbar-toggler-icon::after {
                transform: rotate(-45deg);
                bottom: 9px;
            }

            .navbar-toggler[aria-expanded="true"] .navbar-toggler-icon .middle-bar {
                opacity: 0;
            }

            .navbar-nav {
                background-color: var(--white);
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <form action="about" method="get">
            <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
                <div class="container">
                    <a class="navbar-brand" href="#">
                        <i class="bi bi-heart-pulse-fill"></i><span>MediGreen</span>
                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon">
                            <span class="middle-bar"></span>
                        </span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item">
                                <a class="nav-link active" href="home">Trang chủ</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="about">Giới thiệu</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="service">Dịch vụ</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="doctor">Bác sĩ</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="booking">Đặt lịch</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="contact">Liên hệ</a>
                            </li>
                        </ul>

                    </div>
                </div>
            </nav>
            <!-- About Section -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="home">Trang chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="about">Giới thiệu</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="service">Dịch vụ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="doctor">Bác sĩ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="booking">Đặt lịch</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="contact">Liên hệ</a>
                    </li>
                </ul>

            </div>
            <section id="about" class="section-padding about-section">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-6 mb-5 mb-lg-0">
                            <div class="about-img">
                                <img src="/placeholder.svg?height=500&width=600" alt="Về chúng tôi" class="img-fluid">
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="about-content">
                                <h2>Tại sao chọn MediGreen?</h2>
                                <p>MediGreen là phòng khám đa khoa uy tín hàng đầu, với sứ mệnh mang đến dịch vụ chăm sóc sức khỏe toàn diện và chất lượng cao cho mọi người.</p>

                                <div class="about-feature">
                                    <div class="about-feature-icon">
                                        <i class="bi bi-hospital"></i>
                                    </div>
                                    <div class="about-feature-content">
                                        <h5>Cơ sở vật chất hiện đại</h5>
                                        <p>Phòng khám được trang bị các thiết bị y tế hiện đại, đảm bảo chẩn đoán chính xác và điều trị hiệu quả.</p>
                                    </div>
                                </div>

                                <div class="about-feature">
                                    <div class="about-feature-icon">
                                        <i class="bi bi-people"></i>
                                    </div>
                                    <div class="about-feature-content">
                                        <h5>Đội ngũ y bác sĩ giàu kinh nghiệm</h5>
                                        <p>Các bác sĩ của chúng tôi đều có trình độ chuyên môn cao và nhiều năm kinh nghiệm trong lĩnh vực y tế.</p>
                                    </div>
                                </div>

                                <div class="about-feature">
                                    <div class="about-feature-icon">
                                        <i class="bi bi-heart"></i>
                                    </div>
                                    <div class="about-feature-content">
                                        <h5>Môi trường thân thiện</h5>
                                        <p>Chúng tôi tạo ra môi trường thân thiện, thoải mái để bệnh nhân cảm thấy an tâm khi đến khám.</p>
                                    </div>
                                </div>

                                <a href="#services" class="btn btn-primary mt-4">Khám phá dịch vụ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Counter Section -->
            <section class="section-padding bg-light-green">
                <div class="container">
                    <div class="row g-4">
                        <div class="col-lg-3 col-md-6">
                            <div class="counter-box">
                                <div class="counter-icon">
                                    <i class="bi bi-people"></i>
                                </div>
                                <h2 class="counter-number" data-count="10000">10,000+</h2>
                                <p class="counter-text">Bệnh nhân</p>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="counter-box">
                                <div class="counter-icon">
                                    <i class="bi bi-person-badge"></i>
                                </div>
                                <h2 class="counter-number" data-count="50">50+</h2>
                                <p class="counter-text">Bác sĩ</p>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="counter-box">
                                <div class="counter-icon">
                                    <i class="bi bi-award"></i>
                                </div>
                                <h2 class="counter-number" data-count="15">15+</h2>
                                <p class="counter-text">Năm kinh nghiệm</p>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6">
                            <div class="counter-box">
                                <div class="counter-icon">
                                    <i class="bi bi-hospital"></i>
                                </div>
                                <h2 class="counter-number" data-count="5">5</h2>
                                <p class="counter-text">Cơ sở y tế</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Footer -->
            <footer class="footer section-padding">
                <div class="container">
                    <div class="row g-4">
                        <div class="col-lg-4 col-md-6">
                            <a href="#" class="footer-logo">
                                <i class="bi bi-heart-pulse-fill"></i>MediGreen
                            </a>
                            <p class="footer-text">MediGreen là phòng khám đa khoa uy tín hàng đầu, cung cấp dịch vụ chăm sóc sức khỏe chất lượng cao với đội ngũ y bác sĩ giàu kinh nghiệm.</p>
                            <div class="footer-social">
                                <a href="#"><i class="bi bi-facebook"></i></a>
                                <a href="#"><i class="bi bi-youtube"></i></a>
                                <a href="#"><i class="bi bi-instagram"></i></a>
                                <a href="#"><i class="bi bi-twitter"></i></a>
                            </div>
                        </div>
                        <div class="col-lg-2 col-md-6">
                            <h5 class="footer-title">Liên kết nhanh</h5>
                            <ul class="footer-links">
                                <li><a href="#"><i class="bi bi-chevron-right"></i> Trang chủ</a></li>
                                <li><a href="#about"><i class="bi bi-chevron-right"></i> Giới thiệu</a></li>
                                <li><a href="#services"><i class="bi bi-chevron-right"></i> Dịch vụ</a></li>
                                <li><a href="#doctors"><i class="bi bi-chevron-right"></i> Bác sĩ</a></li>
                                <li><a href="#appointment"><i class="bi bi-chevron-right"></i> Đặt lịch</a></li>
                                <li><a href="#contact"><i class="bi bi-chevron-right"></i> Liên hệ</a></li>
                            </ul>
                        </div>
                        <div class="col-lg-2 col-md-6">
                            <h5 class="footer-title">Dịch vụ</h5>
                            <ul class="footer-links">
                                <li><a href="#"><i class="bi bi-chevron-right"></i> Khám tổng quát</a></li>
                                <li><a href="#"><i class="bi bi-chevron-right"></i> Khám chuyên khoa</a></li>
                                <li><a href="#"><i class="bi bi-chevron-right"></i> Xét nghiệm</a></li>
                                <li><a href="#"><i class="bi bi-chevron-right"></i> Chẩn đoán hình ảnh</a></li>
                                <li><a href="#"><i class="bi bi-chevron-right"></i> Phẫu thuật</a></li>
                            </ul>
                        </div>
                        <div class="col-lg-4 col-md-6">
                            <h5 class="footer-title">Đăng ký nhận tin</h5>
                            <p class="footer-text">Đăng ký để nhận thông tin mới nhất về sức khỏe và các chương trình khuyến mãi.</p>
                            <div class="footer-newsletter">
                                <form class="mb-3">
                                    <div class="input-group">
                                        <input type="email" class="form-control" placeholder="Email của bạn" aria-label="Email của bạn">
                                        <button class="btn btn-primary" type="button">Đăng ký</button>
                                    </div>
                                </form>
                            </div>
                            <p class="footer-text">
                                <i class="bi bi-telephone-fill me-2"></i> Hotline: 1900 1234
                            </p>
                        </div>
                    </div>
                    <div class="footer-bottom">
                        <div class="row">
                            <div class="col-md-6">
                                <p>© 2023 MediGreen. Tất cả các quyền được bảo lưu.</p>
                            </div>
                            <div class="col-md-6 footer-bottom-links">
                                <a href="#">Điều khoản sử dụng</a>
                                <a href="#">Chính sách bảo mật</a>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
        </form>
    </body>
</html>
