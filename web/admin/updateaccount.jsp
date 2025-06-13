<%-- 
    Document   : updateaccount
    Created on : 2 thg 6, 2025, 16:28:52
    Author     : maiki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Cập nhật tài khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #2d5a27 0%, #4a7c59 50%, #6b8e23 100%);
                min-height: 100vh;
                font-family: 'Poppins', sans-serif;
                color: #333;
            }
            .sidebar {
                background: rgba(45, 90, 39, 0.95);
                min-height: 100vh;
                box-shadow: 4px 0 15px rgba(0, 0, 0, 0.2);
                transition: all 0.3s ease;
            }
            .sidebar .nav-link {
                color: #fff;
                padding: 15px 25px;
                border-radius: 10px;
                margin: 8px 15px;
                transition: all 0.3s ease;
                font-size: 16px;
            }
            .sidebar .nav-link:hover,
            .sidebar .nav-link.active {
                background: rgba(255, 255, 255, 0.3);
                color: #fff;
                transform: translateX(8px);
            }
            .main-content {
                background: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
                margin: 20px;
                padding: 30px;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            }
            .btn-success {
                background: linear-gradient(45deg, #28a745, #20c997);
                border: none;
                border-radius: 25px;
                padding: 12px 30px;
                font-weight: 500;
            }
            .btn-primary {
                background: linear-gradient(45deg, #007bff, #0056b3);
                border: none;
                border-radius: 25px;
                padding: 12px 30px;
                font-weight: 500;
            }
            .form-control, .form-select {
                border-radius: 10px;
                border: 2px solid #e9ecef;
                transition: all 0.3s ease;
            }
            .form-control:focus, .form-select:focus {
                border-color: #4a7c59;
                box-shadow: 0 0 0 0.25rem rgba(74, 124, 89, 0.25);
            }
            .alert {
                margin-bottom: 15px;
                display: none; /* Ẩn mặc định, hiển thị bằng JavaScript */
            }
        </style>
    </head>
    <body>
        <script>
            window.onload = function () {
                const messageType = '${messageType}';
                if (messageType && messageType !== 'null') {
                    const alert = document.querySelector('.alert');
                    if (alert) {
                        alert.style.display = 'block';
                        alert.className = 'alert alert-' + messageType;
                        alert.textContent = '${message}';
                        setTimeout(() => {
                            alert.style.display = 'none';
                        }, 3000);
                    }
                }
            };
        </script>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 col-lg-2 px-0">
                    <div class="sidebar">
                        <div class="text-center py-4">
                            <h4 class="text-white"><i class="fas fa-user-shield"></i> Admin Panel</h4>
                            <small class="text-light">Quản lý hệ thống</small>
                        </div>
                        <nav class="nav flex-column">
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet?showSection=dashboard" data-section="dashboard">
                                <i class="fas fa-tachometer-alt me-2"></i> Trang chủ
                            </a>
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet?action=list&showSection=account-management" data-section="account-management">
                                <i class="fas fa-users me-2"></i> Quản lý tài khoản
                            </a>
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet?showSection=statistics" data-section="statistics">
                                <i class="fas fa-chart-bar me-2"></i> Thống kê
                            </a>
                        </nav>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10">
                    <div class="main-content">
                        <div class="alert"></div>
                        <div class="card">
                            <div class="card-body">
                                <h2>Cập nhật tài khoản</h2>
                                <c:if test="${not empty message}">
                                    <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                                        ${message}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                </c:if>

                                <!-- Form tìm kiếm tài khoản theo email -->
                                <div class="mb-4">
                                    <form action="<%= request.getContextPath()%>/AccountManagementServlet" method="get" class="row g-3">
                                        <input type="hidden" name="showSection" value="update-account">
                                        <div class="col-md-8">
                                            <input type="email" class="form-control" id="searchEmail" name="email" placeholder="Nhập email để tìm tài khoản" value="${param.email}" required>
                                        </div>
                                        <div class="col-md-4">
                                            <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
                                        </div>
                                    </form>
                                </div>

                                <!-- Hiển thị form cập nhật nếu có dữ liệu -->
                                <c:if test="${not empty selectedAccount}">
                                    <form action="<%= request.getContextPath()%>/AccountManagementServlet" method="post">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="email" value="${selectedAccount.email}">
                                        <div class="row g-3">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="email" class="form-label">Email</label>
                                                    <input type="email" class="form-control" id="email" name="email" value="${selectedAccount.email}" required>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="username" class="form-label">Tên người dùng</label>
                                                    <c:set var="usernameValue">
                                                        <c:choose>
                                                            <c:when test="${selectedAccount['class'].simpleName == 'Patient'}">
                                                                ${selectedAccount.userName}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${selectedAccount.username}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:set>
                                                    <input type="text" class="form-control" id="username" name="username" value="${usernameValue}" required>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="password" class="form-label">Mật khẩu</label>
                                                    <input type="password" class="form-control" id="password" name="password" required>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="fullName" class="form-label">Họ và tên</label>
                                                    <input type="text" class="form-control" id="fullName" name="fullName" value="${selectedDetail.fullName}" required>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="dob" class="form-label">Ngày sinh</label>
                                                    <input type="date" class="form-control" id="dob" name="dob" value="<c:if test='${selectedDetail.dob != null}'>${selectedDetail.dob}</c:if>" required>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="gender" class="form-label">Giới tính</label>
                                                        <select class="form-select" id="gender" name="gender" required>
                                                            <option value="Nam" ${selectedDetail.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                                                        <option value="Nữ" ${selectedDetail.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                                        <option value="Khác" ${selectedDetail.gender == 'Khác' ? 'selected' : ''}>Khác</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="address" class="form-label">Địa chỉ</label>
                                                    <input type="text" class="form-control" id="address" name="address" value="${selectedDetail.address}" required>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="phoneNumber" class="form-label">Số điện thoại</label>
                                                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${selectedDetail.phoneNumber}" required>
                                                </div>
                                            </div>
                                            <c:if test="${role == 'Doctor'}">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="specializationId" class="form-label">Chuyên khoa</label>
                                                        <select class="form-select" id="specializationId" name="specializationId" required>
                                                            <c:forEach var="spec" items="${specializations}">
                                                                <option value="${spec.id}" ${spec.id == selectedDetail.specialization.id ? 'selected' : ''}>${spec.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="doctorLevelId" class="form-label">Trình độ</label>
                                                        <select class="form-select" id="doctorLevelId" name="doctorLevelId" required>
                                                            <c:forEach var="level" items="${doctorLevels}">
                                                                <option value="${level.id}" ${level.id == selectedDetail.doctorLevel.id ? 'selected' : ''}>${level.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <div class="col-12">
                                                <button type="submit" class="btn btn-success mt-3">Cập nhật tài khoản</button>
                                                <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=list&showSection=account-management" class="btn btn-primary mt-3 ms-2">Trở về</a>
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
                                <c:if test="${empty selectedAccount}">
                                    <div class="alert alert-warning">Không tìm thấy tài khoản với email: ${param.email}. Vui lòng thử lại!</div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>