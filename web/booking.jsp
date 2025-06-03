<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đặt lịch khám - MediGreen</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
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
                --white: #ffffff;
            }

            body {
                font-family: 'Poppins', sans-serif;
                color: var(--dark);
                line-height: 1.7;
                background-color: #f9f9f9;
            }

            .navbar {
                padding: 20px 0;
                background-color: var(--white);
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            }

            .navbar-brand {
                font-weight: 700;
                font-size: 1.8rem;
                color: var(--primary);
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
                transition: all 0.3s ease;
            }

            .navbar-nav .nav-link:hover,
            .navbar-nav .nav-link.active {
                color: var(--primary);
            }

            .appointment-section {
                padding: 120px 0 80px;
                background-color: var(--primary-very-light);
                position: relative;
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

            .btn-primary {
                background-color: var(--primary);
                border-color: var(--primary);
                color: var(--white);
                border-radius: 50px;
                padding: 12px 30px;
                font-weight: 500;
                transition: all 0.3s ease;
                box-shadow: 0 5px 15px rgba(46, 204, 113, 0.3);
            }

            .btn-primary:hover {
                background-color: var(--primary-dark);
                border-color: var(--primary-dark);
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(46, 204, 113, 0.4);
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

            .required {
                color: #e74c3c;
            }

            .time-group {
                display: flex;
                gap: 15px;
                align-items: center;
            }

            .time-separator {
                font-weight: 600;
                color: var(--primary);
                margin: 0 10px;
            }

            @media (max-width: 768px) {
                .appointment-form {
                    padding: 30px 20px;
                }
                
                .time-group {
                    flex-direction: column;
                    gap: 10px;
                }
                
                .time-separator {
                    display: none;
                }
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light fixed-top">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <i class="bi bi-heart-pulse-fill"></i><span>MediGreen</span>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="home">Trang chủ</a>
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
                            <a class="nav-link active" href="booking">Đặt lịch</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="contact">Liên hệ</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Appointment Section -->
        <section class="appointment-section">
            <div class="container">
                <div class="row g-4">
                    <div class="col-lg-6 mb-5 mb-lg-0">
                        <h2 class="mb-4">Đặt lịch khám ngay hôm nay</h2>
                        <p class="mb-4">Đặt lịch khám trực tuyến để tiết kiệm thời gian và được ưu tiên khi đến phòng khám. Chúng tôi sẽ liên hệ lại để xác nhận lịch hẹn của bạn.</p>

                        <div class="contact-info">
                            <div class="contact-icon">
                                <i class="bi bi-telephone"></i>
                            </div>
                            <div class="contact-text">
                                <h5>Gọi trực tiếp</h5>
                                <p>1900 1234</p>
                            </div>
                        </div>

                        <div class="contact-info">
                            <div class="contact-icon">
                                <i class="bi bi-envelope"></i>
                            </div>
                            <div class="contact-text">
                                <h5>Email</h5>
                                <p>info@medigreen.vn</p>
                            </div>
                        </div>

                        <div class="contact-info">
                            <div class="contact-icon">
                                <i class="bi bi-geo"></i>
                            </div>
                            <div class="contact-text">
                                <h5>Địa chỉ</h5>
                                <p>123 Đường Lê Lợi, Quận 1, TP. Hồ Chí Minh</p>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="appointment-form">
                            <h4 class="text-center mb-4">Thông tin đặt lịch khám</h4>

                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>

                            <c:if test="${not empty message}">
                                <div class="alert alert-success">${message}</div>
                            </c:if>

                            <form method="POST" action="${pageContext.request.contextPath}/booking" id="appointmentForm">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="name" class="form-label">Họ và tên <span class="required">*</span></label>
                                            <input type="text" class="form-control" id="name" name="name" placeholder="Nguyễn Văn A" required value="${param.name}">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="gender" class="form-label">Giới tính <span class="required">*</span></label>
                                            <select class="form-select" id="gender" name="gender" required>
                                                <option value="" ${empty param.gender ? 'selected' : ''} disabled>Chọn giới tính</option>
                                                <option value="male" ${param.gender == 'male' ? 'selected' : ''}>Nam</option>
                                                <option value="female" ${param.gender == 'female' ? 'selected' : ''}>Nữ</option>
                                                <option value="other" ${param.gender == 'other' ? 'selected' : ''}>Khác</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="phone" class="form-label">Số điện thoại <span class="required">*</span></label>
                                            <input type="tel" class="form-control" id="phone" name="phone" placeholder="0912345678" required pattern="\d{10}" title="Số điện thoại phải có 10 chữ số" value="${param.phone}">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="dob" class="form-label">Ngày sinh <span class="required">*</span></label>
                                            <input type="date" class="form-control" id="dob" name="dob" required value="${param.dob}">
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-group">
                                            <label for="address" class="form-label">Địa chỉ <span class="required">*</span></label>
                                            <input type="text" class="form-control" id="address" name="address" placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố" required value="${param.address}">
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-group">
                                            <label for="insurance_number" class="form-label">Số bảo hiểm y tế</label>
                                            <input type="text" class="form-control" id="insurance_number" name="insurance_number" placeholder="VN1234567890123" maxlength="15" value="${param.insurance_number}">
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-group">
                                            <label for="date" class="form-label">Ngày khám <span class="required">*</span></label>
                                            <input type="date" class="form-control" id="date" name="date" required value="${param.date}">
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-group">
                                            <label class="form-label">Thời gian khám <span class="required">*</span></label>
                                            <div class="time-group">
                                                <div class="flex-fill">
                                                    <label for="start_time" class="form-label small">Giờ bắt đầu</label>
                                                    <select class="form-select" id="start_time" name="start_time" required>
                                                        <option value="" ${empty param.start_time ? 'selected' : ''} disabled>Chọn giờ bắt đầu</option>
                                                        <option value="08:00" ${param.start_time == '08:00' ? 'selected' : ''}>08:00</option>
                                                        <option value="08:30" ${param.start_time == '08:30' ? 'selected' : ''}>08:30</option>
                                                        <option value="09:00" ${param.start_time == '09:00' ? 'selected' : ''}>09:00</option>
                                                        <option value="09:30" ${param.start_time == '09:30' ? 'selected' : ''}>09:30</option>
                                                        <option value="10:00" ${param.start_time == '10:00' ? 'selected' : ''}>10:00</option>
                                                        <option value="10:30" ${param.start_time == '10:30' ? 'selected' : ''}>10:30</option>
                                                        <option value="11:00" ${param.start_time == '11:00' ? 'selected' : ''}>11:00</option>
                                                        <option value="11:30" ${param.start_time == '11:30' ? 'selected' : ''}>11:30</option>
                                                        <option value="13:00" ${param.start_time == '13:00' ? 'selected' : ''}>13:00</option>
                                                        <option value="13:30" ${param.start_time == '13:30' ? 'selected' : ''}>13:30</option>
                                                        <option value="14:00" ${param.start_time == '14:00' ? 'selected' : ''}>14:00</option>
                                                        <option value="14:30" ${param.start_time == '14:30' ? 'selected' : ''}>14:30</option>
                                                        <option value="15:00" ${param.start_time == '15:00' ? 'selected' : ''}>15:00</option>
                                                        <option value="15:30" ${param.start_time == '15:30' ? 'selected' : ''}>15:30</option>
                                                        <option value="16:00" ${param.start_time == '16:00' ? 'selected' : ''}>16:00</option>
                                                        <option value="16:30" ${param.start_time == '16:30' ? 'selected' : ''}>16:30</option>
                                                    </select>
                                                </div>
                                                <div class="time-separator">đến</div>
                                                <div class="flex-fill">
                                                    <label for="end_time" class="form-label small">Giờ kết thúc</label>
                                                    <select class="form-select" id="end_time" name="end_time" required>
                                                        <option value="" ${empty param.end_time ? 'selected' : ''} disabled>Chọn giờ kết thúc</option>
                                                        <option value="08:30" ${param.end_time == '08:30' ? 'selected' : ''}>08:30</option>
                                                        <option value="09:00" ${param.end_time == '09:00' ? 'selected' : ''}>09:00</option>
                                                        <option value="09:30" ${param.end_time == '09:30' ? 'selected' : ''}>09:30</option>
                                                        <option value="10:00" ${param.end_time == '10:00' ? 'selected' : ''}>10:00</option>
                                                        <option value="10:30" ${param.end_time == '10:30' ? 'selected' : ''}>10:30</option>
                                                        <option value="11:00" ${param.end_time == '11:00' ? 'selected' : ''}>11:00</option>
                                                        <option value="11:30" ${param.end_time == '11:30' ? 'selected' : ''}>11:30</option>
                                                        <option value="12:00" ${param.end_time == '12:00' ? 'selected' : ''}>12:00</option>
                                                        <option value="13:30" ${param.end_time == '13:30' ? 'selected' : ''}>13:30</option>
                                                        <option value="14:00" ${param.end_time == '14:00' ? 'selected' : ''}>14:00</option>
                                                        <option value="14:30" ${param.end_time == '14:30' ? 'selected' : ''}>14:30</option>
                                                        <option value="15:00" ${param.end_time == '15:00' ? 'selected' : ''}>15:00</option>
                                                        <option value="15:30" ${param.end_time == '15:30' ? 'selected' : ''}>15:30</option>
                                                        <option value="16:00" ${param.end_time == '16:00' ? 'selected' : ''}>16:00</option>
                                                        <option value="16:30" ${param.end_time == '16:30' ? 'selected' : ''}>16:30</option>
                                                        <option value="17:00" ${param.end_time == '17:00' ? 'selected' : ''}>17:00</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-12 mt-4">
                                        <div class="d-grid">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="bi bi-calendar-plus me-2"></i>Đặt lịch khám
                                            </button>
                                        </div>
                                        <h5>${requestScope.error}</h5>
                                        <h5>${requestScope.message}</h5>
                                        <div class="text-center mt-3">
                                            <small class="text-muted">
                                                Lịch hẹn sẽ được xác nhận trong vòng 24h.
                                                Chúng tôi sẽ liên hệ qua số điện thoại bạn cung cấp.
                                            </small>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Set minimum date to today for appointment date
            document.getElementById('date').min = new Date().toISOString().split('T')[0];

            // Set maximum date for date of birth (today)
            document.getElementById('dob').max = new Date().toISOString().split('T')[0];

            // Set minimum date for date of birth (120 years ago)
            const minDate = new Date();
            minDate.setFullYear(minDate.getFullYear() - 120);
            document.getElementById('dob').min = minDate.toISOString().split('T')[0];

            // Phone number validation
            document.getElementById('phone').addEventListener('input', function () {
                let phone = this.value.replace(/\D/g, '');
                if (phone.length > 10) {
                    phone = phone.substring(0, 10);
                }
                this.value = phone;
            });

            // Insurance number formatting
            document.getElementById('insurance_number').addEventListener('input', function () {
                let value = this.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
                if (value.length > 15) {
                    value = value.substring(0, 15);
                }
                this.value = value;
            });

            // Time validation - ensure end time is after start time
            function validateTime() {
                const startTime = document.getElementById('start_time').value;
                const endTime = document.getElementById('end_time').value;
                
                if (startTime && endTime) {
                    const start = new Date('2000-01-01 ' + startTime);
                    const end = new Date('2000-01-01 ' + endTime);
                    
                    if (end <= start) {
                        alert('Giờ kết thúc phải sau giờ bắt đầu!');
                        document.getElementById('end_time').value = '';
                        return false;
                    }
                    
                    // Check if duration is at least 30 minutes
                    const diffMinutes = (end - start) / (1000 * 60);
                    if (diffMinutes < 30) {
                        alert('Thời gian khám tối thiểu là 30 phút!');
                        document.getElementById('end_time').value = '';
                        return false;
                    }
                }
                return true;
            }

            document.getElementById('start_time').addEventListener('change', validateTime);
            document.getElementById('end_time').addEventListener('change', validateTime);

            // Form validation
            document.getElementById('appointmentForm').addEventListener('submit', function (e) {
                const phone = document.getElementById('phone').value;
                const dob = document.getElementById('dob').value;
                const address = document.getElementById('address').value.trim();
                const gender = document.getElementById('gender').value;
                const date = document.getElementById('date').value;
                const startTime = document.getElementById('start_time').value;
                const endTime = document.getElementById('end_time').value;

                if (phone.length !== 10) {
                    e.preventDefault();
                    alert('Số điện thoại phải có 10 số!');
                    return;
                }
                
                if (!dob) {
                    e.preventDefault();
                    alert('Ngày sinh không được để trống!');
                    return;
                }
                
                if (!address) {
                    e.preventDefault();
                    alert('Địa chỉ không được để trống!');
                    return;
                }
                
                if (!gender) {
                    e.preventDefault();
                    alert('Vui lòng chọn giới tính!');
                    return;
                }
                
                if (!date) {
                    e.preventDefault();
                    alert('Ngày khám không được để trống!');
                    return;
                }
                
                if (!startTime || !endTime) {
                    e.preventDefault();
                    alert('Vui lòng chọn đầy đủ thời gian khám!');
                    return;
                }
                
                if (!validateTime()) {
                    e.preventDefault();
                    return;
                }
                
                const selectedDate = new Date(date);
                const today = new Date();
                today.setHours(0, 0, 0, 0);
                if (selectedDate < today) {
                    e.preventDefault();
                    alert('Vui lòng chọn ngày từ hôm nay trở đi!');
                }
            });
        </script>
    </body>
</html>