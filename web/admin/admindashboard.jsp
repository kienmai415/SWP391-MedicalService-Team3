<%-- 
    Document   : admindashboard
    Created on : 28 thg 5, 2025, 23:43:22
    Author     : maiki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Quản lý hệ thống</title>
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
            .alert {
                margin-bottom: 15px;
                display: none; /* Ẩn mặc định, hiển thị bằng JavaScript */
            }
        </style>
    </head>
    <body>
        <script>
            function showSection(sectionId) {
                console.log("showSection called with sectionId: " + sectionId);
                hideAllSections();
                document.getElementById(sectionId).classList.add('active');
                document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
                const link = document.querySelector(`[data-section="${sectionId}"]`);
                if (link) {
                    link.classList.add('active');
                }
                // Cập nhật URL với showSection
                window.history.pushState({}, document.title, window.location.pathname + "?showSection=" + sectionId);
            }

            function hideAllSections() {
                console.log("hideAllSections called");
                document.querySelectorAll('.action-section').forEach(section => section.classList.remove('active'));
            }

            window.onload = function () {
                console.log("window.onload called");
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
                console.log("showSection value from URL or attribute: " + showSection);
                if (showSection) {
                    showSection(showSection);
                } else {
                    showSection('dashboard'); // Mặc định là dashboard
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
                        <!-- Messages -->
                        <c:if test="${not empty message}">
                            <div class="alert">
                                ${message}
                            </div>
                        </c:if>

                        <!-- Dashboard -->
                        <div id="dashboard" class="action-section ${showSection == 'dashboard' ? 'active' : ''}">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h2><i class="fas fa-tachometer-alt text-success"></i> Trang chủ</h2>
                                <span class="badge bg-success fs-6">Admin</span>
                            </div>
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Chào mừng đến với Admin Dashboard</h5>
                                    <p>Vui lòng chọn chức năng từ thanh bên để quản lý tài khoản hoặc xem thống kê.</p>
                                </div>
                            </div>
                        </div>

                        <!-- Account Management -->
                        <div id="account-management" class="action-section ${showSection == 'account-management' ? 'active' : ''}">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h2><i class="fas fa-users text-primary"></i> Quản lý tài khoản</h2>
                                <div>
                                    <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=add&showSection=account-management" class="btn btn-success me-2">
                                        <i class="fas fa-plus"></i> Thêm tài khoản
                                    </a>
                                    <a href="<%= request.getContextPath()%>/AccountManagementServlet?showSection=dashboard" class="btn btn-primary">
                                        <i class="fas fa-home"></i> Trở về trang chủ
                                    </a>
                                </div>
                            </div>
                            <div class="card mb-3">
                                <div class="card-body">
                                    <form action="<%= request.getContextPath()%>/AccountManagementServlet" method="get">
                                        <input type="hidden" name="action" value="search">
                                        <input type="hidden" name="showSection" value="account-management">
                                        <div class="row mb-3">
                                            <div class="col-md-12">
                                                <label class="form-label">Tìm kiếm theo email</label>
                                                <input type="text" class="form-control" name="email" value="${filterEmail}" placeholder="Nhập email">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label class="form-label">Trạng thái</label>
                                                <select class="form-select" name="status">
                                                    <option value="all" ${empty filterStatus || filterStatus == 'all' ? 'selected' : ''}>Tất cả trạng thái</option>
                                                    <option value="activated" ${filterStatus == 'activated' ? 'selected' : ''}>Hoạt động</option>
                                                    <option value="deactivated" ${filterStatus == 'deactivated' ? 'selected' : ''}>Vô hiệu hóa</option>
                                                </select>
                                            </div>
                                            <div class="col-md-4">
                                                <label class="form-label">Vai trò</label>
                                                <select class="form-select" name="role">
                                                    <option value="all" ${empty filterRole || filterRole == 'all' ? 'selected' : ''}>Tất cả vai trò</option>
                                                    <option value="2" ${filterRole == '2' ? 'selected' : ''}>Bệnh nhân</option>
                                                    <option value="4" ${filterRole == '4' ? 'selected' : ''}>Bác sĩ</option>
                                                    <option value="3" ${filterRole == '3' ? 'selected' : ''}>Quản lý</option>
                                                    <option value="5" ${filterRole == '5' ? 'selected' : ''}>Lễ tân</option>
                                                </select>
                                            </div>
                                            <div class="col-md-4 d-flex align-items-end">
                                                <button class="btn btn-primary w-100" type="submit">
                                                    <i class="fas fa-filter"></i> Lọc
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-body">
                                    <h5>Danh sách tài khoản</h5>
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Tên người dùng</th>
                                                    <th>Email</th>
                                                    <th>Trạng thái</th>
                                                    <th>Thao tác</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="account" items="${accounts}">
                                                    <tr>
                                                        <td>${account.id}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${account['class'].simpleName == 'Patient'}">
                                                                    ${account.userName}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${account.username}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${account.email}</td>
                                                        <td>
                                                            <span class="badge ${account.status ? 'bg-success' : 'bg-danger'}">
                                                                ${account.status ? 'Hoạt động' : 'Vô hiệu hóa'}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=view&id=${account.id}&showSection=account-detail&filterEmail=${filterEmail}&filterRole=${filterRole}&filterStatus=${filterStatus}" class="btn btn-sm btn-info">
                                                                <i class="fas fa-eye"></i> Xem chi tiết
                                                            </a>
                                                            <c:choose>
                                                                <c:when test="${account.status}">
                                                                    <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=deactivate&id=${account.id}&showSection=account-management&filterEmail=${filterEmail}&filterRole=${filterRole}&filterStatus=${filterStatus}" class="btn btn-sm btn-danger">
                                                                        <i class="fas fa-trash"></i> Vô hiệu hóa
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=activate&id=${account.id}&showSection=account-management&filterEmail=${filterEmail}&filterRole=${filterRole}&filterStatus=${filterStatus}" class="btn btn-sm btn-success">
                                                                        <i class="fas fa-check"></i> Kích hoạt
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${empty accounts}">
                                                    <tr>
                                                        <td colspan="5" class="text-center">Không có tài khoản nào.</td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Account Detail -->
                        <div id="account-detail" class="action-section ${showSection == 'account-detail' ? 'active' : ''}">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h2><i class="fas fa-eye text-info"></i> Chi tiết tài khoản</h2>
                                <div>
                                    <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=list&showSection=account-management&filterEmail=${filterEmail}&filterRole=${filterRole}&filterStatus=${filterStatus}" class="btn btn-primary me-2">
                                        <i class="fas fa-arrow-left"></i> Quay lại
                                    </a>
                                </div>
                            </div>
                            <c:if test="${not empty selectedAccount}">
                                <div class="card">
                                    <div class="card-body">
                                        <h5>Thông tin chi tiết</h5>
                                        <table class="table table-bordered">
                                            <tr><th>ID</th><td>${selectedAccount.id}</td></tr>
                                            <tr><th>Tên người dùng</th><td>
                                                    <c:choose>
                                                        <c:when test="${selectedAccount['class'].simpleName == 'Patient'}">
                                                            ${selectedAccount.userName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${selectedAccount.username}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td></tr>
                                            <tr><th>Email</th><td>${selectedAccount.email}</td></tr>
                                                    <c:if test="${selectedDetail != null}">
                                                        <c:choose>
                                                            <c:when test="${selectedDetail['class'].simpleName == 'Doctor'}">
                                                        <tr><th>Họ tên</th><td>${selectedDetail.fullName}</td></tr>
                                                        <tr><th>Địa chỉ</th><td>${selectedDetail.address}</td></tr>
                                                        <tr><th>Ngày sinh</th><td>${selectedDetail.dob}</td></tr>
                                                        <tr><th>Giới tính</th><td>${selectedDetail.gender}</td></tr>
                                                        <tr><th>Số điện thoại</th><td>${selectedDetail.phoneNumber}</td></tr>
                                                        <tr><th>Chuyên khoa</th><td>${selectedDetail.specialization.name}</td></tr>
                                                        <tr><th>Trình độ</th><td>${selectedDetail.doctorLevel.name}</td></tr>
                                                        </c:when>
                                                        <c:when test="${selectedDetail['class'].simpleName == 'User'}">
                                                        <tr><th>Họ tên</th><td>${selectedDetail.fullName}</td></tr>
                                                        <tr><th>Địa chỉ</th><td>${selectedDetail.address}</td></tr>
                                                        <tr><th>Ngày sinh</th><td>${selectedDetail.dob}</td></tr>
                                                        <tr><th>Giới tính</th><td>${selectedDetail.gender}</td></tr>
                                                        <tr><th>Số điện thoại</th><td>${selectedDetail.phoneNumber}</td></tr>
                                                            </c:when>
                                                            <c:when test="${selectedDetail['class'].simpleName == 'Patient'}">
                                                        <tr><th>Họ tên</th><td>${selectedDetail.fullName}</td></tr>
                                                        <tr><th>Địa chỉ</th><td>${selectedDetail.address}</td></tr>
                                                        <tr><th>Ngày sinh</th><td>${selectedDetail.dob}</td></tr>
                                                        <tr><th>Giới tính</th><td>${selectedDetail.gender}</td></tr>
                                                        <tr><th>Số điện thoại</th><td>${selectedDetail.phoneNumber}</td></tr>
                                                        <tr><th>Số CCCD</th><td>${selectedDetail.identityNumber}</td></tr>
                                                        <tr><th>Số bảo hiểm</th><td>${selectedDetail.insuranceNumber}</td></tr>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:if>
                                            <tr>
                                                <th>Trạng thái</th>
                                                <td>
                                                    <span class="badge ${selectedAccount.status ? 'bg-success' : 'bg-danger'}">
                                                        ${selectedAccount.status ? 'Hoạt động' : 'Vô hiệu hóa'}
                                                    </span>
                                                </td>
                                            </tr>
                                            <c:if test="${selectedAccount.role != 'Patient'}">
                                                <tr>
                                                    <th>Thao tác</th>
                                                    <td>
                                                        <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=update&id=${selectedAccount.id}&showSection=account-detail&filterEmail=${filterEmail}&filterRole=${filterRole}&filterStatus=${filterStatus}" class="btn btn-warning">
                                                            <i class="fas fa-edit"></i> Cập nhật tài khoản
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </table>
                                    </div>
                                </div>
                            </c:if>
                        </div>

                        <!-- Statistics -->
                        <div id="statistics" class="action-section ${showSection == 'statistics' ? 'active' : ''}">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <h2><i class="fas fa-chart-bar text-info"></i> Thống kê</h2>
                                <a href="<%= request.getContextPath()%>/AccountManagementServlet?showSection=dashboard" class="btn btn-primary">
                                    <i class="fas fa-home"></i> Trở về trang chủ
                                </a>
                            </div>
                            <div class="card">
                                <div class="card-body">
                                    <h5>Thống kê hệ thống</h5>
                                    <p>Chức năng thống kê đang được phát triển. Các thống kê sẽ bao gồm:</p>
                                    <ul>
                                        <li>Doanh thu</li>
                                        <li>Số lượng người sử dụng hệ thống</li>
                                        <li>Thống kê nhân viên</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>