<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>MediGreen - Phòng Khám Chuyên Nghiệp</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@8/swiper-bundle.min.css" />


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@8/swiper-bundle.min.css" />


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

            .text-primary {
                color: var(--primary) !important;
            }

            .bg-primary {
                background-color: var(--primary) !important;
            }

            .bg-gradient-primary {
                background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            }

            .bg-light-green {
                background-color: var(--primary-very-light);
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

            .btn-outline-primary {
                color: var(--primary);
                border-color: var(--primary);
            }

            .btn-outline-primary:hover {
                background-color: var(--primary);
                border-color: var(--primary);
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(46, 204, 113, 0.4);
            }

            .btn-light {
                background-color: var(--white);
                border-color: var(--white);
                color: var(--primary);
                box-shadow: 0 5px 15px rgba(255, 255, 255, 0.2);
            }

            .btn-light:hover {
                background-color: var(--light);
                border-color: var(--light);
                color: var(--primary-dark);
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(255, 255, 255, 0.3);
            }

            .btn-outline-light {
                color: var(--white);
                border-color: var(--white);
            }

            .btn-outline-light:hover {
                background-color: var(--white);
                border-color: var(--white);
                color: var(--primary);
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(255, 255, 255, 0.3);
            }

            .btn-lg {
                padding: 15px 40px;
                font-size: 1.1rem;
            }

            .btn-sm {
                padding: 8px 20px;
                font-size: 0.9rem;
            }

            .btn-icon {
                width: 50px;
                height: 50px;
                padding: 0;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                border-radius: 50%;
            }

            .btn-icon-sm {
                width: 40px;
                height: 40px;
            }

            .btn-floating {
                position: fixed;
                bottom: 30px;
                right: 30px;
                width: 60px;
                height: 60px;
                border-radius: 50%;
                background-color: var(--primary);
                color: var(--white);
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 1.5rem;
                box-shadow: 0 5px 15px rgba(46, 204, 113, 0.4);
                z-index: 999;
                transition: all 0.3s ease;
            }

            .btn-floating:hover {
                background-color: var(--primary-dark);
                transform: translateY(-5px) rotate(360deg);
                box-shadow: 0 8px 25px rgba(46, 204, 113, 0.5);
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

            .hero-section {
                position: relative;
                padding: 180px 0 120px;
                background: linear-gradient(135deg, rgba(46, 204, 113, 0.9) 0%, rgba(39, 174, 96, 0.9) 100%), url('/placeholder.svg?height=800&width=1600');
                background-size: cover;
                background-position: center;
                color: var(--white);
                overflow: hidden;
            }

            .hero-section::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
                opacity: 0.1;
            }

            .hero-section .container {
                position: relative;
                z-index: 1;
            }

            .hero-section h1 {
                font-size: 3.5rem;
                font-weight: 700;
                margin-bottom: 20px;
                line-height: 1.2;
            }

            .hero-section p {
                font-size: 1.2rem;
                margin-bottom: 30px;
                opacity: 0.9;
            }

            .hero-image {
                position: relative;
            }

            .hero-image img {
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
                transition: all 0.5s ease;
            }

            .hero-image::before {
                content: '';
                position: absolute;
                top: -20px;
                left: -20px;
                width: 100px;
                height: 100px;
                border-radius: 20px;
                background-color: rgba(255, 255, 255, 0.1);
                z-index: -1;
            }

            .hero-image::after {
                content: '';
                position: absolute;
                bottom: -20px;
                right: -20px;
                width: 100px;
                height: 100px;
                border-radius: 20px;
                background-color: rgba(255, 255, 255, 0.1);
                z-index: -1;
            }

            .section-padding {
                padding: 100px 0;
            }

            .section-title {
                margin-bottom: 60px;
                text-align: center;
            }

            .section-title h2 {
                font-size: 2.5rem;
                font-weight: 700;
                margin-bottom: 20px;
                position: relative;
                display: inline-block;
            }

            .section-title h2::after {
                content: '';
                position: absolute;
                bottom: -15px;
                left: 50%;
                width: 80px;
                height: 3px;
                background-color: var(--primary);
                transform: translateX(-50%);
            }

            .section-title p {
                font-size: 1.1rem;
                color: var(--gray);
                max-width: 700px;
                margin: 0 auto;
            }

            .feature-box {
                padding: 40px 30px;
                border-radius: 20px;
                background-color: var(--white);
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                height: 100%;
                position: relative;
                z-index: 1;
                overflow: hidden;
            }

            .feature-box::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 0;
                background: linear-gradient(135deg, var(--primary-very-light) 0%, rgba(255, 255, 255, 0) 100%);
                transition: all 0.5s ease;
                z-index: -1;
            }

            .feature-box:hover::before {
                height: 100%;
            }

            .feature-box:hover {
                transform: translateY(-10px);
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
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

            .service-card {
                border-radius: 20px;
                overflow: hidden;
                background-color: var(--white);
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                height: 100%;
                position: relative;
            }

            .service-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            }

            .service-card .card-body {
                padding: 30px;
            }

            .service-icon {
                width: 80px;
                height: 80px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: var(--primary-very-light);
                color: var(--primary);
                border-radius: 20px;
                margin: 0 auto 25px;
                font-size: 2rem;
                transition: all 0.3s ease;
            }

            .service-card:hover .service-icon {
                background-color: var(--primary);
                color: var(--white);
                transform: rotateY(180deg);
            }

            .service-card h4 {
                font-size: 1.5rem;
                margin-bottom: 15px;
                font-weight: 600;
                text-align: center;
            }

            .service-card p {
                color: var(--gray);
                text-align: center;
                margin-bottom: 25px;
            }

            .doctor-card {
                border-radius: 20px;
                overflow: hidden;
                background-color: var(--white);
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                height: 100%;
            }

            .doctor-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            }

            .doctor-img {
                position: relative;
                overflow: hidden;
            }

            .doctor-img img {
                width: 100%;
                height: 300px;
                object-fit: cover;
                transition: all 0.5s ease;
            }

            .doctor-card:hover .doctor-img img {
                transform: scale(1.1);
            }

            .doctor-social {
                position: absolute;
                top: 20px;
                right: -60px;
                transition: all 0.3s ease;
            }

            .doctor-card:hover .doctor-social {
                right: 20px;
            }

            .doctor-social a {
                display: block;
                width: 40px;
                height: 40px;
                background-color: var(--white);
                color: var(--primary);
                border-radius: 50%;
                text-align: center;
                line-height: 40px;
                margin-bottom: 10px;
                transition: all 0.3s ease;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }

            .doctor-social a:hover {
                background-color: var(--primary);
                color: var(--white);
                transform: translateY(-3px);
            }

            .doctor-info {
                padding: 30px;
                text-align: center;
            }

            .doctor-info h4 {
                font-size: 1.5rem;
                margin-bottom: 5px;
                font-weight: 600;
            }

            .doctor-info p {
                color: var(--primary);
                font-weight: 500;
                margin-bottom: 15px;
            }

            .doctor-info .doctor-text {
                color: var(--gray);
                margin-bottom: 0;
            }

            .appointment-section {
                position: relative;
                background-color: var(--primary-very-light);
                overflow: hidden;
            }

            .appointment-section::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%232ecc71' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
                opacity: 0.5;
            }

            .appointment-form {
                background-color: var(--white);
                border-radius: 20px;
                padding: 40px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.05);
                position: relative;
                z-index: 1;
            }

            .appointment-form h4 {
                font-size: 1.8rem;
                margin-bottom: 30px;
                text-align: center;
                font-weight: 600;
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

            textarea.form-control {
                height: auto;
                min-height: 120px;
            }

            .form-label {
                font-weight: 500;
                margin-bottom: 10px;
                color: var(--dark);
            }

            .contact-info {
                display: flex;
                align-items: center;
                margin-bottom: 30px;
            }

            .contact-icon {
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
                transition: all 0.3s ease;
            }

            .contact-info:hover .contact-icon {
                background-color: var(--primary);
                color: var(--white);
                transform: rotateY(180deg);
            }

            .contact-text h5 {
                font-size: 1.2rem;
                margin-bottom: 5px;
                font-weight: 600;
            }

            .contact-text p {
                color: var(--gray);
                margin-bottom: 0;
            }

            .testimonial-section {
                position: relative;
                overflow: hidden;
            }

            .testimonial-card {
                background-color: var(--white);
                border-radius: 20px;
                padding: 40px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
                position: relative;
                margin: 20px 15px;
            }

            .testimonial-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            }

            .testimonial-card .quote {
                position: absolute;
                top: 20px;
                right: 30px;
                font-size: 4rem;
                color: var(--primary-light);
                opacity: 0.3;
            }

            .testimonial-text {
                font-size: 1.1rem;
                color: var(--gray);
                margin-bottom: 30px;
                position: relative;
                z-index: 1;
            }

            .testimonial-author {
                display: flex;
                align-items: center;
            }

            .testimonial-author img {
                width: 60px;
                height: 60px;
                border-radius: 50%;
                object-fit: cover;
                margin-right: 20px;
                border: 3px solid var(--primary-light);
            }

            .testimonial-author-info h5 {
                font-size: 1.2rem;
                margin-bottom: 5px;
                font-weight: 600;
            }

            .testimonial-author-info p {
                color: var(--primary);
                margin-bottom: 0;
                font-weight: 500;
            }

            .swiper-pagination-bullet {
                width: 12px;
                height: 12px;
                background-color: var(--primary);
                opacity: 0.5;
            }

            .swiper-pagination-bullet-active {
                opacity: 1;
                background-color: var(--primary);
            }

            .swiper-button-next, .swiper-button-prev {
                color: var(--primary);
                width: 50px;
                height: 50px;
                border-radius: 50%;
                background-color: var(--white);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
            }

            .swiper-button-next:hover, .swiper-button-prev:hover {
                background-color: var(--primary);
                color: var(--white);
            }

            .swiper-button-next::after, .swiper-button-prev::after {
                font-size: 1.5rem;
                font-weight: bold;
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

            .cta-section {
                position: relative;
                background: linear-gradient(135deg, rgba(46, 204, 113, 0.9) 0%, rgba(39, 174, 96, 0.9) 100%), url('/placeholder.svg?height=400&width=1200');
                background-size: cover;
                background-position: center;
                background-attachment: fixed;
                color: var(--white);
                overflow: hidden;
            }

            .cta-section::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
                opacity: 0.1;
            }

            .cta-section .container {
                position: relative;
                z-index: 1;
            }

            .cta-content {
                text-align: center;
            }

            .cta-content h2 {
                font-size: 2.5rem;
                font-weight: 700;
                margin-bottom: 20px;
            }

            .cta-content p {
                font-size: 1.2rem;
                margin-bottom: 30px;
                opacity: 0.9;
            }

            .about-section {
                position: relative;
                overflow: hidden;
            }

            .about-img {
                position: relative;
            }

            .about-img img {
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                width: 300px;
                height: 300px;
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

            /* Login Modal */
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
                    <button class="btn btn-primary ms-lg-4" data-bs-toggle="modal" data-bs-target="#loginModal">
                        <i class="bi bi-person-fill me-2"></i>Đăng nhập
                    </button>
                </div>
            </div>
        </nav>

        <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="loginModalLabel">Chào mừng bạn trở lại</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="auth-tabs">
                            <div class="auth-tab active" id="login-tab">Đăng nhập</div>
                            <div class="auth-tab" id="register-tab">Đăng ký</div>
                        </div>

                        <div id="login-form" class="auth-form">
                            <div class="form-group">
                                <label for="login-email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="login-email" placeholder="Nhập email của bạn">
                            </div>
                            <div class="form-group">
                                <label for="login-password" class="form-label">Mật khẩu</label>
                                <input type="password" class="form-control" id="login-password" placeholder="Nhập mật khẩu">
                            </div>
                            <div class="forgot-password">
                                <a href="#" data-bs-toggle="modal" data-bs-target="#forgotPasswordModal" data-bs-dismiss="modal">Quên mật khẩu?</a>
                            </div>
                            <button type="submit" class="btn btn-primary">Đăng nhập</button>

                            <div class="auth-separator">
                                <span></span>
                                <p>Hoặc đăng nhập với</p>
                                <span></span>
                            </div>

                            <div class="social-login">
                                <a href="#" class="social-login-btn facebook">
                                    <i class="bi bi-facebook"></i> Facebook
                                </a>
                                <a href="#" class="social-login-btn google">
                                    <i class="bi bi-google"></i> Google
                                </a>
                            </div>
                        </div>

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
                            <button type="submit" class="btn btn-primary">Đăng ký</button>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="auth-footer">
                            <p id="login-footer">Bạn chưa có tài khoản? <a href="#" id="show-register">Đăng ký ngay</a></p>
                            <p id="register-footer" style="display: none;">Bạn đã có tài khoản? <a href="#" id="show-login">Đăng nhập</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="forgotPasswordModal" tabindex="-1" aria-labelledby="forgotPasswordModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="forgotPasswordModalLabel">Quên mật khẩu</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p class="text-muted mb-4">Vui lòng nhập email của bạn để nhận hướng dẫn đặt lại mật khẩu.</p>
                        <div class="form-group">
                            <label for="forgot-email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="forgot-email" placeholder="Nhập email của bạn">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-primary">Gửi yêu cầu</button>
                    </div>
                </div>
            </div>
        </div>

        <section class="hero-section" id="home">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-6 mb-5 mb-lg-0">
                        <h1 class="mb-4">Chăm sóc sức khỏe tận tâm cho bạn và gia đình</h1>
                        <p class="mb-4">MediGreen cung cấp dịch vụ y tế chuyên nghiệp với đội ngũ bác sĩ giàu kinh nghiệm và trang thiết bị hiện đại, mang đến sự chăm sóc tốt nhất cho bạn và gia đình.</p>
                        <div class="d-flex flex-wrap gap-3">
                            <a href="#appointment" class="btn btn-light btn-lg">
                                <i class="bi bi-calendar-check me-2"></i>Đặt lịch khám
                            </a>
                            <a href="#services" class="btn btn-outline-light btn-lg">
                                <i class="bi bi-arrow-right me-2"></i>Tìm hiểu thêm
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="hero-image">
                            <img src="images/home.png?height=100&width=100" alt="Đội ngũ y tế chuyên nghiệp" class="img-fluid">
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="section-padding" style="margin-top: -80px;">
            <div class="container">
                <div class="row g-4">
                    <div class="col-lg-4 col-md-6">
                        <div class="feature-box text-center">
                            <div class="feature-icon mx-auto">
                                <i class="bi bi-shield-check"></i>
                            </div>
                            <h4>Chăm sóc chất lượng</h4>
                            <p>Chúng tôi cam kết mang đến dịch vụ y tế chất lượng cao với sự tận tâm và chuyên nghiệp.</p>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="feature-box text-center">
                            <div class="feature-icon mx-auto">
                                <i class="bi bi-person-check"></i>
                            </div>
                            <h4>Bác sĩ chuyên môn</h4>
                            <p>Đội ngũ y bác sĩ giàu kinh nghiệm, được đào tạo chuyên sâu trong nhiều lĩnh vực.</p>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="feature-box text-center">
                            <div class="feature-icon mx-auto">
                                <i class="bi bi-clock-history"></i>
                            </div>
                            <h4>Hỗ trợ 24/7</h4>
                            <p>Luôn sẵn sàng hỗ trợ và tư vấn y tế mọi lúc mọi nơi khi bạn cần.</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>

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

        <section id="services" class="section-padding">
            <div class="container">
                <div class="section-title">
                    <h2>Dịch vụ của chúng tôi</h2>
                    <p>Chúng tôi cung cấp nhiều dịch vụ y tế chất lượng cao để đáp ứng nhu cầu chăm sóc sức khỏe của bạn</p>
                </div>
                <div class="row g-4">
                    <div class="col-lg-4 col-md-6">
                        <div class="service-card">
                            <div class="card-body">
                                <div class="service-icon">
                                    <i class="bi bi-heart-pulse"></i>
                                </div>
                                <h4>Khám tổng quát</h4>
                                <p>Kiểm tra sức khỏe toàn diện với các xét nghiệm cần thiết để đánh giá tình trạng sức khỏe của bạn.</p>
                                <a href="#" class="btn btn-outline-primary">Tìm hiểu thêm</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="service-card">
                            <div class="card-body">
                                <div class="service-icon">
                                    <i class="bi bi-lungs"></i>
                                </div>
                                <h4>Khám chuyên khoa</h4>
                                <p>Dịch vụ khám chuyên sâu với các bác sĩ chuyên khoa giàu kinh nghiệm trong nhiều lĩnh vực.</p>
                                <a href="#" class="btn btn-outline-primary">Tìm hiểu thêm</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="service-card">
                            <div class="card-body">
                                <div class="service-icon">
                                    <i class="bi bi-capsule"></i>
                                </div>
                                <h4>Dược phẩm</h4>
                                <p>Cung cấp thuốc chất lượng cao với đầy đủ thông tin và hướng dẫn sử dụng chi tiết.</p>
                                <a href="#" class="btn btn-outline-primary">Tìm hiểu thêm</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="service-card">
                            <div class="card-body">
                                <div class="service-icon">
                                    <i class="bi bi-clipboard2-pulse"></i>
                                </div>
                                <h4>Xét nghiệm</h4>
                                <p>Dịch vụ xét nghiệm máu, nước tiểu và các xét nghiệm chẩn đoán khác với kết quả nhanh chóng.</p>
                                <a href="#" class="btn btn-outline-primary">Tìm hiểu thêm</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="service-card">
                            <div class="card-body">
                                <div class="service-icon">
                                    <i class="bi bi-image"></i>
                                </div>
                                <h4>Chẩn đoán hình ảnh</h4>
                                <p>Dịch vụ chụp X-quang, siêu âm, CT scan và MRI với công nghệ hiện đại nhất.</p>
                                <a href="#" class="btn btn-outline-primary">Tìm hiểu thêm</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="service-card">
                            <div class="card-body">
                                <div class="service-icon">
                                    <i class="bi bi-bandaid"></i>
                                </div>
                                <h4>Phẫu thuật</h4>
                                <p>Dịch vụ phẫu thuật an toàn với đội ngũ bác sĩ giàu kinh nghiệm và trang thiết bị hiện đại.</p>
                                <a href="#" class="btn btn-outline-primary">Tìm hiểu thêm</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section id="doctors" class="section-padding bg-light-green">
            <div class="container">
                <div class="section-title">
                    <h2>Đội ngũ bác sĩ</h2>
                    <p>Gặp gỡ đội ngũ bác sĩ chuyên nghiệp của chúng tôi</p>
                </div>
                <div class="row g-4">
                    <div class="col-lg-3 col-md-6">
                        <div class="doctor-card">
                            <div class="doctor-img">
                                <img src="/placeholder.svg?height=300&width=300" alt="Bác sĩ Nguyễn Văn A">
                                <div class="doctor-social">
                                    <a href="#"><i class="bi bi-facebook"></i></a>
                                    <a href="#"><i class="bi bi-twitter"></i></a>
                                    <a href="#"><i class="bi bi-linkedin"></i></a>
                                </div>
                            </div>
                            <div class="doctor-info">
                                <h4>BS. Nguyễn Văn A</h4>
                                <p>Khoa Tim mạch</p>
                                <p class="doctor-text">15 năm kinh nghiệm trong lĩnh vực tim mạch và can thiệp mạch vành.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="doctor-card">
                            <div class="doctor-img">
                                <img src="/placeholder.svg?height=300&width=300" alt="Bác sĩ Trần Thị B">
                                <div class="doctor-social">
                                    <a href="#"><i class="bi bi-facebook"></i></a>
                                    <a href="#"><i class="bi bi-twitter"></i></a>
                                    <a href="#"><i class="bi bi-linkedin"></i></a>
                                </div>
                            </div>
                            <div class="doctor-info">
                                <h4>BS. Trần Thị B</h4>
                                <p>Khoa Nhi</p>
                                <p class="doctor-text">12 năm kinh nghiệm trong chăm sóc và điều trị các bệnh lý ở trẻ em.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="doctor-card">
                            <div class="doctor-img">
                                <img src="/placeholder.svg?height=300&width=300" alt="Bác sĩ Lê Văn C">
                                <div class="doctor-social">
                                    <a href="#"><i class="bi bi-facebook"></i></a>
                                    <a href="#"><i class="bi bi-twitter"></i></a>
                                    <a href="#"><i class="bi bi-linkedin"></i></a>
                                </div>
                            </div>
                            <div class="doctor-info">
                                <h4>BS. Lê Văn C</h4>
                                <p>Khoa Nội tổng hợp</p>
                                <p class="doctor-text">20 năm kinh nghiệm trong chẩn đoán và điều trị các bệnh nội khoa.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="doctor-card">
                            <div class="doctor-img">
                                <img src="/placeholder.svg?height=300&width=300" alt="Bác sĩ Phạm Thị D">
                                <div class="doctor-social">
                                    <a href="#"><i class="bi bi-facebook"></i></a>
                                    <a href="#"><i class="bi bi-twitter"></i></a>
                                    <a href="#"><i class="bi bi-linkedin"></i></a>
                                </div>
                            </div>
                            <div class="doctor-info">
                                <h4>BS. Phạm Thị D</h4>
                                <p>Khoa Da liễu</p>
                                <p class="doctor-text">10 năm kinh nghiệm trong chẩn đoán và điều trị các bệnh về da.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="text-center mt-5">
                    <a href="#" class="btn btn-primary">Xem tất cả bác sĩ</a>
                </div>
            </div>
        </section>

        <section class="section-padding cta-section">
            <div class="container">
                <div class="cta-content">
                    <h2>Bạn cần tư vấn y tế?</h2>
                    <p>Đội ngũ bác sĩ của chúng tôi luôn sẵn sàng hỗ trợ và tư vấn cho bạn. Hãy liên hệ ngay để được giải đáp mọi thắc mắc.</p>
                    <div class="d-flex flex-wrap justify-content-center gap-3">
                        <a href="#appointment" class="btn btn-light btn-lg">
                            <i class="bi bi-calendar-check me-2"></i>Đặt lịch ngay
                        </a>
                        <a href="tel:19001234" class="btn btn-outline-light btn-lg">
                            <i class="bi bi-telephone-fill me-2"></i>1900 1234
                        </a>
                    </div>
                </div>
            </div>
        </section>

        <section class="section-padding testimonial-section">
            <div class="container">
                <div class="section-title">
                    <h2>Đánh giá từ bệnh nhân</h2>
                    <p>Những gì bệnh nhân nói về chúng tôi</p>
                </div>
                <div class="swiper testimonial-slider">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <div class="testimonial-card">
                                <div class="quote">
                                    <i class="bi bi-quote"></i>
                                </div>
                                <div class="d-flex mb-3">
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                </div>
                                <p class="testimonial-text">"Tôi rất hài lòng với dịch vụ tại MediGreen. Bác sĩ rất tận tâm và giải thích chi tiết về tình trạng bệnh của tôi. Nhân viên phòng khám cũng rất thân thiện và nhiệt tình."</p>
                                <div class="testimonial-author">
                                    <img src="/placeholder.svg?height=60&width=60" alt="Nguyễn Văn X">
                                    <div class="testimonial-author-info">
                                        <h5>Nguyễn Văn X</h5>
                                        <p>Bệnh nhân Tim mạch</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide">
                            <div class="testimonial-card">
                                <div class="quote">
                                    <i class="bi bi-quote"></i>
                                </div>
                                <div class="d-flex mb-3">
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                </div>
                                <p class="testimonial-text">"Đội ngũ y tá và bác sĩ rất chuyên nghiệp. Tôi đã được chẩn đoán và điều trị hiệu quả. Cảm ơn MediGreen đã chăm sóc sức khỏe cho gia đình tôi trong nhiều năm qua."</p>
                                <div class="testimonial-author">
                                    <img src="/placeholder.svg?height=60&width=60" alt="Trần Thị Y">
                                    <div class="testimonial-author-info">
                                        <h5>Trần Thị Y</h5>
                                        <p>Bệnh nhân Nội tổng hợp</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="swiper-slide">
                            <div class="testimonial-card">
                                <div class="quote">
                                    <i class="bi bi-quote"></i>
                                </div>
                                <div class="d-flex mb-3">
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <i class="bi bi-star-half text-warning"></i>
                                </div>
                                <p class="testimonial-text">"Con tôi được điều trị tại khoa Nhi của MediGreen. Bác sĩ rất giỏi và thân thiện với trẻ em. Phòng khám sạch sẽ và có khu vui chơi cho trẻ em trong lúc chờ đợi."</p>
                                <div class="testimonial-author">
                                    <img src="/placeholder.svg?height=60&width=60" alt="Lê Văn Z">
                                    <div class="testimonial-author-info">
                                        <h5>Lê Văn Z</h5>
                                        <p>Phụ huynh bệnh nhi</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-pagination"></div>
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                </div>
            </div>
        </section>

        <section id="appointment" class="section-padding appointment-section">
            <div class="container">
                <div class="row g-4 align-items-center">
                    <div class="col-lg-6 mb-5 mb-lg-0">
                        <h2 class="mb-4">Đặt lịch khám ngay hôm nay</h2>
                        <p class="mb-4">Đặt lịch khám trực tuyến để tiết kiệm thời gian và được ưu tiên khi đến phòng khám. Chúng tôi sẽ liên hệ lại để xác nhận lịch hẹn của bạn.</p>

                        <div class="contact-info">
                            <div class="contact-icon">
                                <i class="bi bi-telephone-fill"></i>
                            </div>
                            <div class="contact-text">
                                <h5>Gọi trực tiếp</h5>
                                <p>1900 1234</p>
                            </div>
                        </div>

                        <div class="contact-info">
                            <div class="contact-icon">
                                <i class="bi bi-envelope-fill"></i>
                            </div>
                            <div class="contact-text">
                                <h5>Email</h5>
                                <p>info@medigreen.vn</p>
                            </div>
                        </div>

                        <div class="contact-info">
                            <div class="contact-icon">
                                <i class="bi bi-geo-alt-fill"></i>
                            </div>
                            <div class="contact-text">
                                <h5>Địa chỉ</h5>
                                <p>123 Đường Lê Lợi, Quận 1, TP. Hồ Chí Minh</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="appointment-form">
                            <h4>Thông tin đặt lịch</h4>
                            <form>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="name" class="form-label">Họ và tên</label>
                                            <input type="text" class="form-control" id="name" placeholder="Nguyễn Văn A">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="phone" class="form-label">Số điện thoại</label>
                                            <input type="tel" class="form-control" id="phone" placeholder="0912345678">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="email" class="form-label">Email</label>
                                            <input type="email" class="form-control" id="email" placeholder="example@gmail.com">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="date" class="form-label">Ngày khám</label>
                                            <input type="date" class="form-control" id="date">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="time" class="form-label">Giờ khám</label>
                                            <select class="form-select" id="time">
                                                <option selected>Chọn giờ</option>
                                                <option>08:00 - 09:00</option>
                                                <option>09:00 - 10:00</option>
                                                <option>10:00 - 11:00</option>
                                                <option>14:00 - 15:00</option>
                                                <option>15:00 - 16:00</option>
                                                <option>16:00 - 17:00</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="department" class="form-label">Chuyên khoa</label>
                                            <select class="form-select" id="department">
                                                <option selected>Chọn chuyên khoa</option>
                                                <option>Khoa Tim mạch</option>
                                                <option>Khoa Nhi</option>
                                                <option>Khoa Nội tổng hợp</option>
                                                <option>Khoa Da liễu</option>
                                                <option>Khoa Mắt</option>
                                                <option>Khoa Tai Mũi Họng</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group">
                                            <label for="message" class="form-label">Triệu chứng</label>
                                            <textarea class="form-control" id="message" rows="3" placeholder="Mô tả triệu chứng của bạn"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <button type="submit" class="btn btn-primary w-100">Đặt lịch ngay</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section id="contact" class="section-padding">
            <div class="container">
                <div class="section-title">
                    <h2>Liên hệ với chúng tôi</h2>
                    <p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi</p>
                </div>
                <div class="row g-4">
                    <div class="col-lg-6 mb-5 mb-lg-0">
                        <div class="ratio ratio-16x9 h-100" style="min-height: 400px;">
                            <iframe
                                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.0964841664996!2d105.5252892!3d21.0124167!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc60e7d3f19%3A0x2be9d7d0b5abcbf4!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFRIIEhOIEPDoCBO4buZaQ!5e0!3m2!1svi!2s!4v1621234567890"
                                width="600"
                                height="450"
                                style="border:0;"
                                allowfullscreen
                                referrerpolicy="no-referrer-when-downgrade">
                            </iframe>                         
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="card border-0 shadow-sm">
                            <div class="card-body p-4">
                                <h4 class="card-title mb-4">Gửi tin nhắn</h4>
                                <form>
                                    <div class="form-group mb-3">
                                        <label for="contactName" class="form-label">Họ và tên</label>
                                        <input type="text" class="form-control" id="contactName">
                                    </div>
                                    <div class="form-group mb-3">
                                        <label for="contactEmail" class="form-label">Email</label>
                                        <input type="email" class="form-control" id="contactEmail">
                                    </div>
                                    <div class="form-group mb-3">
                                        <label for="contactPhone" class="form-label">Số điện thoại</label>
                                        <input type="tel" class="form-control" id="contactPhone">
                                    </div>
                                    <div class="form-group mb-3">
                                        <label for="contactSubject" class="form-label">Chủ đề</label>
                                        <input type="text" class="form-control" id="contactSubject">
                                    </div>
                                    <div class="form-group mb-4">
                                        <label for="contactMessage" class="form-label">Nội dung</label>
                                        <textarea class="form-control" id="contactMessage" rows="4"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary w-100">Gửi tin nhắn</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

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

        <a href="#" class="btn-floating" id="back-to-top">
            <i class="bi bi-arrow-up"></i>
        </a>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/swiper@8/swiper-bundle.min.js"></script>
        <script>
            // Navbar scroll effect
            const navbar = document.getElementById('mainNav');
            window.addEventListener('scroll', () => {
                if (window.scrollY > 50) {
                    navbar.classList.add('navbar-scrolled');
                } else {
                    navbar.classList.remove('navbar-scrolled');
                }
            });

            // Back to top button
            const backToTopButton = document.getElementById('back-to-top');

            window.addEventListener('scroll', () => {
                if (window.pageYOffset > 300) {
                    backToTopButton.style.display = 'flex';
                } else {
                    backToTopButton.style.display = 'none';
                }
            });

            backToTopButton.addEventListener('click', (e) => {
                e.preventDefault();
                window.scrollTo({top: 0, behavior: 'smooth'});
            });

            // Testimonial Slider
            const testimonialSlider = new Swiper('.testimonial-slider', {
                slidesPerView: 1,
                spaceBetween: 30,
                loop: true,
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                },
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                breakpoints: {
                    768: {
                        slidesPerView: 2,
                    },
                    992: {
                        slidesPerView: 3,
                    },
                },
                autoplay: {
                    delay: 5000,
                    disableOnInteraction: false,
                },
            });

            // Login/Register Tab Switch
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

            // Counter Animation
            const counters = document.querySelectorAll('.counter-number');

            const animateCounter = (counter) => {
                const target = parseInt(counter.getAttribute('data-count'));
                const count = parseInt(counter.innerText.replace(/,/g, '').replace(/\+/g, ''));
                const increment = target / 100;

                if (count < target) {
                    counter.innerText = Math.ceil(count + increment) + '+';
                    setTimeout(() => animateCounter(counter), 10);
                } else {
                    counter.innerText = target + '+';
                }
            };

            const observerOptions = {
                threshold: 0.5
            };

            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        animateCounter(entry.target);
                        observer.unobserve(entry.target);
                    }
                });
            }, observerOptions);

            counters.forEach(counter => {
                observer.observe(counter);
            });
        </script>
    </body>
</html>