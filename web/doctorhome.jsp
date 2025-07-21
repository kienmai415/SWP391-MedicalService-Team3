<!DOCTYPE html>
<html lang="vi">
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hệ thống Quản lý Bác sĩ</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <style>
            :root {
                --primary-green: #059669;
                --light-green: #10b981;
                --dark-green: #047857;
                --bg-green: #ecfdf5;
                --accent-green: #d1fae5;
            }

            body {
                background: linear-gradient(135deg, #ecfdf5 0%, #d1fae5 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .navbar {
                background: linear-gradient(135deg, var(--primary-green), var(--dark-green)) !important;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            .card {
                background: rgba(255, 255, 255, 0.9);
                backdrop-filter: blur(10px);
                border: 1px solid rgba(16, 185, 129, 0.2);
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
                transition: transform 0.2s, box-shadow 0.2s;
            }

            .card:hover {
                transform: translateY(-2px);
                box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
            }

            .stat-card {
                border-left: 4px solid var(--primary-green);
            }

            .quick-action-btn {
                background: rgba(255, 255, 255, 0.9);
                border: 2px solid var(--light-green);
                color: var(--dark-green);
                transition: all 0.3s;
                height: 120px;
            }

            .quick-action-btn:hover {
                background: var(--accent-green);
                transform: translateY(-3px);
                box-shadow: 0 6px 12px rgba(16, 185, 129, 0.2);
            }

            .quick-action-icon {
                width: 50px;
                height: 50px;
                background: var(--primary-green);
                color: white;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                margin: 0 auto 10px;
            }

            .appointment-item {
                background: rgba(16, 185, 129, 0.1);
                border-left: 4px solid var(--light-green);
                transition: all 0.2s;
            }

            .appointment-item:hover {
                background: rgba(16, 185, 129, 0.15);
            }

            .badge-waiting {
                background-color: #f59e0b;
            }

            .badge-completed {
                background-color: var(--primary-green);
            }

            .badge-confirmed {
                background-color: #3b82f6;
            }

            .test-result-item {
                background: rgba(34, 197, 94, 0.1);
                border-left: 4px solid #22c55e;
            }

            .activity-item {
                border-left: 3px solid var(--light-green);
                background: rgba(16, 185, 129, 0.05);
            }

            .search-box {
                background: rgba(255, 255, 255, 0.2);
                border: 1px solid rgba(255, 255, 255, 0.3);
                color: white;
            }

            .search-box::placeholder {
                color: rgba(255, 255, 255, 0.8);
            }

            .btn-primary {
                background-color: var(--primary-green);
                border-color: var(--primary-green);
            }

            .btn-primary:hover {
                background-color: var(--dark-green);
                border-color: var(--dark-green);
            }

            .text-primary {
                color: var(--primary-green) !important;
            }
        </style>
    </head>
    <body>
        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark">
            <div class="container">
                <a class="navbar-brand d-flex align-items-center" href="#">
                    <div class="bg-white p-2 rounded me-3">
                        <i class="fas fa-stethoscope text-success fs-4"></i>
                    </div>
                    <div>
                        <h4 class="mb-0">Hệ thống Quản lý Bác sĩ</h4>
                        <small class="text-light">Chào mừng, Bác Sĩ ${doctor.fullName}</small>

                    </div>
                </a>

                <div class="d-flex align-items-center">
                    <div class="position-relative me-3">
                        <input type="text" class="form-control search-box" placeholder="Tìm kiếm bệnh nhân...">
                        <i class="fas fa-search position-absolute top-50 end-0 translate-middle-y me-3"></i>
                    </div>
                    <button class="btn btn-outline-light me-2">
                        <i class="fas fa-bell"></i>
                    </button>
                    <button class="btn btn-outline-light me-3">
                        <i class="fas fa-cog"></i>
                    </button>
                    <div class="bg-success rounded-circle p-2">
                        <i class="fas fa-user text-white"></i>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container mt-4">
            <!-- Statistics Cards -->
            <div class="row mb-4">
                <div class="col-md-3 mb-3">
                    <div class="card stat-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1">Hôm nay</h6>
                                    <h2 class="text-primary mb-0">12</h2>
                                    <small class="text-muted">Cuộc hẹn</small>
                                </div>
                                <i class="fas fa-calendar-alt fa-2x text-primary"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 mb-3">
                    <div class="card stat-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1">Đang chờ</h6>
                                    <h2 class="text-primary mb-0">5</h2>
                                    <small class="text-muted">Bệnh nhân</small>
                                </div>
                                <i class="fas fa-user-clock fa-2x text-primary"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 mb-3">
                    <div class="card stat-card">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="text-muted mb-1">Kết quả mới</h6>
                                    <h2 class="text-primary mb-0">8</h2>
                                    <small class="text-muted">Xét nghiệm</small>
                                </div>
                                <i class="fas fa-flask fa-2x text-primary"></i>
                            </div>
                        </div>
                    </div>
 
            </div>

            <!-- Quick Actions -->
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="text-primary mb-0">Thao tác nhanh</h5>
                    <small class="text-muted">Các chức năng thường dùng trong ngày</small>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="prescribeMedicine()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-prescription-bottle-alt"></i>
                                </div>
                                <strong>Kê đơn thuốc</strong><br>

                            </button>
                        </div>
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="viewAppointments()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-calendar-check"></i>
                                </div>
                                <strong>Xem lịch hẹn</strong><br>
                            </button>
                        </div>
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="requestTest()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-vial"></i>
                                </div>
                                <strong>Yêu cầu xét nghiệm</strong><br>

                            </button>
                        </div>
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="viewPatients()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-users"></i>
                                </div>
                                <strong>Hồ sơ bệnh nhân</strong><br>

                            </button>
                        </div>
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="enterDiagnosis()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-stethoscope"></i>
                                </div>
                                <strong>Kết quả chẩn đoán</strong><br>

                            </button>
                        </div>
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="viewFeedback()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-comment-medical"></i>
                                </div>
                                <strong>Xem đánh giá của bệnh nhân</strong><br>

                            </button>
                        </div>
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="manageSchedule()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-clock"></i>
                                </div>
                                <strong>Lịch làm việc</strong><br>

                            </button>
                        </div>
                        <div class="col-md-3 col-sm-6 mb-3">
                            <button class="btn quick-action-btn w-100 text-center" onclick="updateStatus()">
                                <div class="quick-action-icon">
                                    <i class="fas fa-edit"></i>
                                </div>
                                <strong>Cập nhật trạng thái</strong><br>

                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <!-- Today's Appointments -->
                <div class="col-lg-6 mb-4">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="text-primary mb-0">Lịch hẹn hôm nay</h5>
                                <small class="text-muted">Danh sách cuộc hẹn trong ngày</small>
                            </div>
                            <button class="btn btn-primary btn-sm" onclick="addAppointment()">
                                <i class="fas fa-plus me-1"></i>Thêm hẹn
                            </button>
                        </div>
                        <div class="card-body">
                            <div class="appointment-item p-3 rounded mb-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex align-items-center">
                                        <div class="bg-primary text-white rounded p-2 me-3">
                                            <i class="fas fa-clock"></i>
                                        </div>
                                        <div>
                                            <strong>Nguyễn Văn A</strong><br>
                                            <small class="text-muted">Khám tổng quát - 09:00</small>
                                        </div>
                                    </div>
                                    <div>
                                        <span class="badge badge-waiting me-2">Đang chờ</span>
                                        <button class="btn btn-outline-primary btn-sm" onclick="viewAppointmentDetail('1')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <div class="appointment-item p-3 rounded mb-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex align-items-center">
                                        <div class="bg-primary text-white rounded p-2 me-3">
                                            <i class="fas fa-clock"></i>
                                        </div>
                                        <div>
                                            <strong>Trần Thị B</strong><br>
                                            <small class="text-muted">Tái khám - 10:30</small>
                                        </div>
                                    </div>
                                    <div>
                                        <span class="badge badge-completed me-2">Đã hoàn thành</span>
                                        <button class="btn btn-outline-primary btn-sm" onclick="viewAppointmentDetail('2')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <div class="appointment-item p-3 rounded mb-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex align-items-center">
                                        <div class="bg-primary text-white rounded p-2 me-3">
                                            <i class="fas fa-clock"></i>
                                        </div>
                                        <div>
                                            <strong>Lê Văn C</strong><br>
                                            <small class="text-muted">Khám chuyên khoa - 14:00</small>
                                        </div>
                                    </div>
                                    <div>
                                        <span class="badge badge-confirmed me-2">Đã xác nhận</span>
                                        <button class="btn btn-outline-primary btn-sm" onclick="viewAppointmentDetail('3')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Test Results -->
                <div class="col-lg-6 mb-4">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="text-primary mb-0">Kết quả xét nghiệm</h5>
                                <small class="text-muted">Kết quả mới nhất cần xem xét</small>
                            </div>
                            <button class="btn btn-outline-primary btn-sm" onclick="viewAllTests()">
                                Xem tất cả
                            </button>
                        </div>
                        <div class="card-body">
                            <div class="test-result-item p-3 rounded mb-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex align-items-center">
                                        <div class="bg-success text-white rounded p-2 me-3">
                                            <i class="fas fa-vial"></i>
                                        </div>
                                        <div>
                                            <strong>Nguyễn Văn A</strong><br>
                                            <small class="text-muted">Xét nghiệm máu - 25/05/2025</small>
                                        </div>
                                    </div>
                                    <div>
                                        <span class="badge bg-success me-2">Có kết quả</span>
                                        <button class="btn btn-outline-success btn-sm" onclick="viewTestResult('1')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <div class="test-result-item p-3 rounded mb-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex align-items-center">
                                        <div class="bg-success text-white rounded p-2 me-3">
                                            <i class="fas fa-x-ray"></i>
                                        </div>
                                        <div>
                                            <strong>Trần Thị B</strong><br>
                                            <small class="text-muted">X-quang phổi - 24/05/2025</small>
                                        </div>
                                    </div>
                                    <div>
                                        <span class="badge bg-warning me-2">Đang xử lý</span>
                                        <button class="btn btn-outline-success btn-sm" onclick="viewTestResult('2')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <div class="test-result-item p-3 rounded mb-3">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="d-flex align-items-center">
                                        <div class="bg-success text-white rounded p-2 me-3">
                                            <i class="fas fa-heartbeat"></i>
                                        </div>
                                        <div>
                                            <strong>Lê Văn C</strong><br>
                                            <small class="text-muted">Siêu âm tim - 23/05/2025</small>
                                        </div>
                                    </div>
                                    <div>
                                        <span class="badge bg-success me-2">Có kết quả</span>
                                        <button class="btn btn-outline-success btn-sm" onclick="viewTestResult('3')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Recent Activities -->
            <div class="card">
                <div class="card-header">
                    <h5 class="text-primary mb-0">Hoạt động gần đây</h5>
                    <small class="text-muted">Lịch sử hoạt động trong hệ thống</small>
                </div>
                <div class="card-body">
                    <div class="activity-item p-3 rounded mb-3">
                        <div class="d-flex align-items-center">
                            <div class="bg-primary text-white rounded-circle p-2 me-3">
                                <i class="fas fa-prescription-bottle-alt"></i>
                            </div>
                            <div class="flex-grow-1">
                                <strong>Đã kê đơn thuốc cho bệnh nhân Nguyễn Văn A</strong><br>
                                <small class="text-muted">5 phút trước</small>
                            </div>
                        </div>
                    </div>

                    <div class="activity-item p-3 rounded mb-3">
                        <div class="d-flex align-items-center">
                            <div class="bg-success text-white rounded-circle p-2 me-3">
                                <i class="fas fa-user-check"></i>
                            </div>
                            <div class="flex-grow-1">
                                <strong>Cập nhật trạng thái khám cho bệnh nhân Trần Thị B</strong><br>
                                <small class="text-muted">15 phút trước</small>
                            </div>
                        </div>
                    </div>

                    <div class="activity-item p-3 rounded mb-3">
                        <div class="d-flex align-items-center">
                            <div class="bg-info text-white rounded-circle p-2 me-3">
                                <i class="fas fa-vial"></i>
                            </div>
                            <div class="flex-grow-1">
                                <strong>Tạo yêu cầu xét nghiệm máu cho bệnh nhân Lê Văn C</strong><br>
                                <small class="text-muted">30 phút trước</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                            // JavaScript functions for handling clicks
                                            function prescribeMedicine() {
                                                alert('Chuyển đến trang kê đơn thuốc');
                                            }

                                            function viewAppointments() {
                                                window.location.href = './AppointmentScheduleServlet';
                                            }

                                            function requestTest() {
                                                alert('Chuyển đến trang tạo yêu cầu xét nghiệm');
                                            }

                                            function viewPatients() {
                                                alert('Chuyển đến trang quản lý hồ sơ bệnh nhân');
                                            }

                                            function enterDiagnosis() {
                                                alert('Chuyển đến trang nhập kết quả chẩn đoán');
                                            }

                                            function viewFeedback() {
                                                alert('Chuyển đến trang xem phản hồi bệnh nhân');
                                            }

                                            function manageSchedule() {
                                                alert('Chuyển đến trang quản lý lịch làm việc');
                                            }

                                            function updateStatus() {
                                                alert('Chuyển đến trang cập nhật trạng thái khám');
                                            }

                                            function addAppointment() {
                                                alert('Mở form thêm cuộc hẹn mới');
                                            }

                                            function viewAppointmentDetail(id) {
                                                alert('Xem chi tiết cuộc hẹn ID: ' + id);
                                            }

                                            function viewTestResult(id) {
                                                alert('Xem kết quả xét nghiệm ID: ' + id);
                                            }

                                            function viewAllTests() {
                                                alert('Chuyển đến trang xem tất cả kết quả xét nghiệm');
                                            }


        </script>
    </body>
</html>