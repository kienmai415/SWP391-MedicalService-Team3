<%-- 
    Document   : addaccount
    Created on : 2 thg 6, 2025, 03:07:42
    Author     : maiki
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Thêm tài khoản</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #2d5a27 0%, #4a7c59 50%, #6b8e23 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            .sidebar {
                background: rgba(45, 90, 39, 0.95);
                min-height: 100vh;
                box-shadow: 2px 0 10px rgba(0,0,0,0.1);
            }
            .sidebar .nav-link {
                color: #ffffff;
                padding: 15px 20px;
                border-radius: 8px;
                margin: 5px 10px;
                transition: all 0.3s ease;
                cursor: pointer;
            }
            .sidebar .nav-link:hover,
            .sidebar .nav-link.active {
                background: rgba(255,255,255,0.2);
                color: #ffffff;
                transform: translateX(5px);
            }
            .main-content {
                background: rgba(255,255,255,0.95);
                border-radius: 15px;
                box-shadow: 0 8px 32px rgba(0,0,0,0.1);
                margin: 20px;
                padding: 30px;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                transition: transform 0.3s ease;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .btn-success {
                background: linear-gradient(45deg, #28a745, #20c997);
                border: none;
                border-radius: 25px;
                padding: 10px 25px;
            }
            .btn-primary {
                background: linear-gradient(45deg, #007bff, #0056b3);
                border: none;
                border-radius: 25px;
            }
            .btn-warning {
                background: linear-gradient(45deg, #ffc107, #e0a800);
                border: none;
                border-radius: 25px;
            }
            .btn-danger {
                background: linear-gradient(45deg, #dc3545, #c82333);
                border: none;
                border-radius: 25px;
            }
            .table {
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            }
            .table thead {
                background: linear-gradient(45deg, #2d5a27, #4a7c59);
                color: white;
            }
            .form-control, .form-select {
                border-radius: 10px;
                border: 2px solid #e9ecef;
                transition: all 0.3s ease;
            }
            .form-control:focus, .form-select:focus {
                border-color: #4a7c59;
                box-shadow: 0 0 0 0.2rem rgba(74, 124, 89, 0.25);
            }
            .action-section {
                display: none;
            }
            .action-section.active {
                display: block;
            }
            .form-group {
                margin-bottom: 15px;
            }
            #roleFields {
                display: none;
            }
            .alert {
                margin-bottom: 15px;
                display: none; /* Ẩn mặc định, hiển thị bằng JavaScript */
            }
        </style>
    </head>
    <body>
        <script>
            function showFields() {
                var role = document.getElementById("role").value;
                var fields = document.getElementById("roleFields");
                fields.innerHTML = ""; // Xóa nội dung cũ

                if (role === "Manager" || role === "Receptionist") {
                    fields.innerHTML = `
                        <div class="form-group">
                            <label for="fullName" class="form-label">Họ và tên</label>
                            <input type="text" class="form-control" id="fullName" name="fullName" required>
                        </div>
                        <div class="form-group">
                            <label for="dob" class="form-label">Ngày sinh</label>
                            <input type="date" class="form-control" id="dob" name="dob" required>
                        </div>
                        <div class="form-group">
                            <label for="gender" class="form-label">Giới tính</label>
                            <select class="form-select" id="gender" name="gender" required>
                                <option value="Nam">Nam</option>
                                <option value="Nữ">Nữ</option>
                                <option value="Khác">Khác</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="address" class="form-label">Địa chỉ</label>
                            <input type="text" class="form-control" id="address" name="address" required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber" class="form-label">Số điện thoại</label>
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required>
                        </div>
                    `;
                } else if (role === "Doctor") {
                    fields.innerHTML = `
                        <div class="form-group">
                            <label for="fullName" class="form-label">Họ và tên</label>
                            <input type="text" class="form-control" id="fullName" name="fullName" required>
                        </div>
                        <div class="form-group">
                            <label for="dob" class="form-label">Ngày sinh</label>
                            <input type="date" class="form-control" id="dob" name="dob" required>
                        </div>
                        <div class="form-group">
                            <label for="gender" class="form-label">Giới tính</label>
                            <select class="form-select" id="gender" name="gender" required>
                                <option value="Nam">Nam</option>
                                <option value="Nữ">Nữ</option>
                                <option value="Khác">Khác</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="address" class="form-label">Địa chỉ</label>
                            <input type="text" class="form-control" id="address" name="address" required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber" class="form-label">Số điện thoại</label>
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required>
                        </div>
                        <div class="form-group">
                            <label for="specializationId" class="form-label">Chuyên khoa</label>
                            <select class="form-select" id="specializationId" name="specializationId" required>
            <c:forEach var="spec" items="${specializations}">
                                    <option value="${spec.id}">${spec.name}</option>
            </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="doctorLevelId" class="form-label">Trình độ</label>
                            <select class="form-select" id="doctorLevelId" name="doctorLevelId" required>
            <c:forEach var="level" items="${doctorLevels}">
                                    <option value="${level.id}">${level.name}</option>
            </c:forEach>
                            </select>
                        </div>
                    `;
                }
                fields.style.display = "block";
            }

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
                // Lấy showSection từ URL hoặc attribute
                const urlParams = new URLSearchParams(window.location.search);
                let showSection = urlParams.get('showSection') || '${showSection}';
                if (showSection) {
                    showSection(showSection);
                } else {
                    showSection('add-account');
                }
            };

            function showSection(sectionId) {
                console.log("showSection called with sectionId: " + sectionId);
                hideAllSections();
                document.getElementById(sectionId).classList.add('active');
                document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
                const link = document.querySelector(`[data-section="${sectionId}"]`);
                if (link) {
                    link.classList.add('active');
                }
                window.history.pushState({}, document.title, window.location.pathname + "?showSection=" + sectionId);
            }

            function hideAllSections() {
                console.log("hideAllSections called");
                document.querySelectorAll('.action-section').forEach(section => section.classList.remove('active'));
            }
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
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet?showSection=dashboard" data-section="dashboard" onclick="showSection('dashboard'); return false;">
                                <i class="fas fa-tachometer-alt me-2"></i> Trang chủ
                            </a>
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet?action=list&showSection=account-management" data-section="account-management" onclick="showSection('account-management'); return false;">
                                <i class="fas fa-users me-2"></i> Quản lý tài khoản
                            </a>
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet?showSection=statistics" data-section="statistics" onclick="showSection('statistics'); return false;">
                                <i class="fas fa-chart-bar me-2"></i> Thống kê
                            </a>
                        </nav>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10">
                    <div class="main-content">
                        <div id="add-account" class="action-section active">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h2><i class="fas fa-plus text-success"></i> Thêm tài khoản</h2>
                                <div>
                                    <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=list&showSection=account-management" class="btn btn-primary">
                                        <i class="fas fa-arrow-left"></i> Quay lại
                                    </a>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-body">
                                    <c:if test="${not empty message}">
                                        <div class="alert alert-${messageType}">
                                            ${message}
                                        </div>
                                    </c:if>
                                    <form action="<%= request.getContextPath()%>/AccountManagementServlet?action=add" method="post">
                                        <div class="form-group">
                                            <label for="email" class="form-label">Email</label>
                                            <input type="email" class="form-control" id="email" name="email" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="username" class="form-label">Tên người dùng</label>
                                            <input type="text" class="form-control" id="username" name="username" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="password" class="form-label">Mật khẩu</label>
                                            <input type="password" class="form-control" id="password" name="password" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="role" class="form-label">Vai trò</label>
                                            <select class="form-select" id="role" name="role" onchange="showFields()" required>
                                                <option value="">Chọn vai trò</option>
                                                <option value="Manager">Giám đốc</option>
                                                <option value="Receptionist">Lễ tân</option>
                                                <option value="Doctor">Bác sĩ</option>
                                            </select>
                                        </div>
                                        <div id="roleFields"></div>
                                        <button type="submit" class="btn btn-success mt-3">Thêm tài khoản</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>