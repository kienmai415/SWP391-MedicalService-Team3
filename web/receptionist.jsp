<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hệ thống Lễ tân - Phòng khám</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #2d5a27 0%, #4a7c59 50%, #6b8e23 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            .main-container {
                background: rgba(255, 255, 255, 0.95);
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
                margin: 20px auto;
                padding: 0;
                overflow: hidden;
            }
            .sidebar {
                background: linear-gradient(180deg, #2d5a27 0%, #4a7c59 100%);
                min-height: 100vh;
                padding: 20px 0;
            }
            .sidebar .nav-link {
                color: white;
                padding: 15px 25px;
                margin: 5px 15px;
                border-radius: 10px;
                transition: all 0.3s ease;
            }
            .sidebar .nav-link:hover,
            .sidebar .nav-link.active {
                background: rgba(255, 255, 255, 0.2);
                color: white;
                transform: translateX(5px);
            }
            .content-area {
                padding: 30px;
                background: white;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
                transition: transform 0.3s ease;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .card-header {
                background: linear-gradient(135deg, #4a7c59, #6b8e23);
                color: white;
                border-radius: 15px 15px 0 0 !important;
                padding: 15px 20px;
            }
            .btn-primary {
                background: linear-gradient(135deg, #4a7c59, #6b8e23);
                border: none;
                border-radius: 10px;
                padding: 10px 20px;
                transition: all 0.3s ease;
            }
            .btn-primary:hover {
                background: linear-gradient(135deg, #2d5a27, #4a7c59);
                transform: translateY(-2px);
            }
            .btn-success {
                background: linear-gradient(135deg, #28a745, #20c997);
                border: none;
            }
            .btn-danger {
                background: linear-gradient(135deg, #dc3545, #e74c3c);
                border: none;
            }
            .stats-card {
                background: linear-gradient(135deg, #4a7c59, #6b8e23);
                color: white;
                border-radius: 15px;
                padding: 20px;
                text-align: center;
                margin-bottom: 20px;
            }
            .stats-number {
                font-size: 2.5rem;
                font-weight: bold;
                margin-bottom: 10px;
            }
            .table {
                border-radius: 10px;
                overflow: hidden;
            }
            .table thead {
                background: linear-gradient(135deg, #4a7c59, #6b8e23);
                color: white;
            }
            .badge {
                padding: 8px 12px;
                border-radius: 20px;
            }
            .header-title {
                color: #2d5a27;
                font-weight: bold;
                margin-bottom: 30px;
            }
            .logo {
                text-align: center;
                padding: 20px;
                color: white;
                border-bottom: 1px solid rgba(255, 255, 255, 0.2);
                margin-bottom: 20px;
            }
            .logo h4 {
                margin: 10px 0 0 0;
            }
            .notification-badge {
                position: absolute;
                top: 5px;
                right: 5px;
                background: #dc3545;
                color: white;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                font-size: 12px;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .tab-content {
                display: none;
            }
            .tab-content.active {
                display: block;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 col-lg-2 sidebar">
                    <div class="logo">
                        <i class="fas fa-hospital fa-3x"></i>
                        <h4>Phòng khám MediGreen</h4>
                        <p class="mb-0">Hệ thống Lễ tân</p>
                    </div>

                    <nav class="nav flex-column">
                        <a class="nav-link active" href="#" onclick="showTab('dashboard')">
                            <i class="fas fa-tachometer-alt me-2"></i> Tổng quan
                        </a>
                        <a class="nav-link" href="#" onclick="showTab('appointments')">
                            <i class="fas fa-calendar-check me-2"></i> Quản lý lịch hẹn
                            <span class="notification-badge">${book.size()}</span>
                        </a>
                        <a class="nav-link" href="#" onclick="showTab('feedback')">
                            <i class="fas fa-comments me-2"></i> Phản hồi bệnh nhân
                        </a>
                        <a class="nav-link" href="#" onclick="showTab('history')">
                            <i class="fas fa-history me-2"></i> Lịch sử
                        </a>
                        <a class="nav-link" href="#" onclick="showTab('shifts')">
                            <i class="fas fa-user-md me-2"></i> Ca làm bác sĩ
                        </a>
                        <a class="nav-link" href="#" onclick="showTab('support')">
                            <i class="fas fa-headset me-2"></i> Hỗ trợ khách hàng
                        </a>
                    </nav>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 content-area">
                    <!-- Dashboard Tab -->
                    <div id="dashboard" class="tab-content active">
                        <h2 class="header-title">
                            <i class="fas fa-tachometer-alt me-2"></i>Tổng quan hệ thống
                        </h2>

                        <c:if test="${not empty message}">
                            <div class="alert alert-success">${message}</div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <!-- Stats Cards -->
                        <div class="row">
                            <div class="col-md-3">
                                <div class="stats-card">
                                    <div class="stats-number">
                                        <c:out value="${book.stream().filter(b -> b.dateAppointment.toString() == '2025-05-27').count()}"/>
                                    </div>
                                    <div>Lịch hẹn hôm nay</div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="stats-card">
                                    <div class="stats-number">
                                        <c:out value="${book.stream().filter(b -> b.confirmationStatus == 'Pending').count()}"/>
                                    </div>
                                    <div>Chờ xác nhận</div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="stats-card">
                                    <div class="stats-number">0</div>
                                    <div>Phản hồi mới</div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="stats-card">
                                    <div class="stats-number">0</div>
                                    <div>Bác sĩ đang làm</div>
                                </div>
                            </div>
                        </div>

                        <!-- Quick Actions -->
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="mb-0"><i class="fas fa-clock me-2"></i>Lịch hẹn gần nhất</h5>
                                    </div>
                                    <div class="card-body">
                                        <c:choose>
                                            <c:when test="${not empty book}">
                                                <c:set var="nearestBooking" value="${book.stream().sorted((b1, b2) -> b1.dateAppointment.compareTo(b2.dateAppointment)).findFirst().orElse(null)}"/>
                                                <c:if test="${nearestBooking != null}">
                                                    <div class="d-flex justify-content-between align-items-center mb-2">
                                                        <strong>${nearestBooking.patientName}</strong>
                                                        <span class="badge bg-warning">
                                                            <fmt:formatDate value="${nearestBooking.startTime}" pattern="HH:mm"/>
                                                        </span>
                                                    </div>
                                                    <p class="mb-1">Bác sĩ: ${nearestBooking.doctorName}</p>
                                                    <small class="text-muted">SĐT: ${nearestBooking.patientId}</small>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="mb-0">Không có lịch hẹn nào.</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="mb-0"><i class="fas fa-bell me-2"></i>Thông báo mới</h5>
                                    </div>
                                    <div class="card-body">
                                        <c:if test="${book.stream().filter(b -> b.confirmationStatus == 'Pending').count() > 0}">
                                            <div class="alert alert-warning mb-0">
                                                <small>${book.stream().filter(b -> b.confirmationStatus == 'Pending').count()} lịch hẹn chờ xác nhận</small>
                                            </div>
                                        </c:if>
                                        <c:if test="${book.stream().filter(b -> b.confirmationStatus == 'Pending').count() == 0}">
                                            <p class="mb-0">Không có thông báo mới.</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Appointments Tab -->
                    <form action="receptionistBooking" method="get">
                        <div id="appointments" class="tab-content">
                            <h2 class="header-title">
                                <i class="fas fa-calendar-check me-2"></i>Quản lý lịch hẹn
                            </h2>

                            <c:if test="${not empty message}">
                                <div class="alert alert-success">${message}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>

                            <div class="card">
                                <div class="card-header">
                                    <h5 class="mb-0">Danh sách lịch hẹn</h5>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Ngày</th>
                                                    <th>Thời gian</th>
                                                    <th>Bệnh nhân</th>
                                                    <th>Bác sĩ</th>
                                                    <th>Trạng thái</th>
                                                    <th>Thao tác</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty book}">
                                                        <c:forEach items="${book}" var="i">
                                                            <tr>
                                                                <td>${i.id}</td>
                                                                <td><fmt:formatDate value="${i.dateAppointment}" pattern="dd/MM/yyyy"/></td>
                                                                <td>
                                                                    <fmt:formatDate value="${i.startTime}" pattern="HH:mm"/> - 
                                                                    <fmt:formatDate value="${i.endTime}" pattern="HH:mm"/>
                                                                </td>
                                                                <td>
                                                                    <strong>${i.patientName}</strong>
                                                                </td>
                                                                <td>${i.doctorName}</td>
                                                                <td>
                                                                    <span class="badge
                                                                          ${i.confirmationStatus == 'Pending' ? 'bg-warning' : 
                                                                            i.confirmationStatus == 'Đã xác nhận' ? 'bg-success' : 
                                                                            i.confirmationStatus == 'Đã hủy' ? 'bg-danger' : 'bg-secondary'}">
                                                                              ${i.confirmationStatus}
                                                                          </span>
                                                                    </td>
                                                                    <td>
                                                                        <c:if test="${i.confirmationStatus == 'Pending'}">
                                                                            <form action="receptionistBooking" method="post" style="display: inline;">
                                                                                <input type="hidden" name="action" value="confirm"/>
                                                                                <input type="hidden" name="id" value="${i.id}"/>
                                                                                <button type="submit" class="btn btn-success btn-sm me-1" title="Xác nhận">
                                                                                    <i class="fas fa-check"></i>
                                                                                </button>
                                                                            </form>
                                                                            <form action="receptionistBooking" method="post" style="display: inline;">
                                                                                <input type="hidden" name="action" value="cancel"/>
                                                                                <input type="hidden" name="id" value="${i.id}"/>
                                                                                <button type="submit" class="btn btn-danger btn-sm me-1" title="Hủy">
                                                                                    <i class="fas fa-times"></i>
                                                                                </button>
                                                                            </form>
                                                                        </c:if>
                                                                        <c:if test="${i.confirmationStatus != 'Pending'}">
                                                                            <span>—</span>
                                                                        </c:if>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <td colspan="7" class="text-center">Không có lịch hẹn nào.</td>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>

                        <!-- Feedback Tab -->
                        <div id="feedback" class="tab-content">
                            <h2 class="header-title">
                                <i class="fas fa-comments me-2"></i>Quản lý phản hồi bệnh nhân
                            </h2>
                            <div class="card">
                                <div class="card-body">
                                    <p>Chưa có dữ liệu phản hồi bệnh nhân.</p>
                                </div>
                            </div>
                        </div>

                        <!-- History Tab -->
                        <div id="history" class="tab-content">
                            <h2 class="header-title">
                                <i class="fas fa-history me-2"></i>Lịch sử hoạt động
                            </h2>
                            <div class="card">
                                <div class="card-body">
                                    <p>Chưa có dữ liệu lịch sử hoạt động.</p>
                                </div>
                            </div>
                        </div>

                        <!-- Shifts Tab -->
                        <div id="shifts" class="tab-content">
                            <h2 class="header-title">
                                <i class="fas fa-user-md me-2"></i>Quản lý ca làm bác sĩ
                            </h2>
                            <div class="card">
                                <div class="card-body">
                                    <p>Chưa có dữ liệu ca làm bác sĩ.</p>
                                </div>
                            </div>
                        </div>

                        <!-- Support Tab -->
                        <div id="support" class="tab-content">
                            <h2 class="header-title">
                                <i class="fas fa-headset me-2"></i>Hỗ trợ khách hàng
                            </h2>
                            <div class="card">
                                <div class="card-body">
                                    <p>Chưa có yêu cầu hỗ trợ khách hàng.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                                function showTab(tabName) {
                                    const tabs = document.querySelectorAll('.tab-content');
                                    tabs.forEach(tab => tab.classList.remove('active'));
                                    document.getElementById(tabName).classList.add('active');

                                    const navLinks = document.querySelectorAll('.nav-link');
                                    navLinks.forEach(link => link.classList.remove('active'));
                                    event.target.classList.add('active');
                                }
                </script>
        </body>
    </html>