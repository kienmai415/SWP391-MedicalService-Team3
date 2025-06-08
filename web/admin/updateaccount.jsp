<%-- 
    Document   : updateaccount
    Created on : 2 thg 6, 2025, 16:28:52
    Author     : maiki
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cập nhật tài khoản</title>
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
            .form-control, .form-select {
                border-radius: 10px;
                border: 2px solid #e9ecef;
                transition: all 0.3s ease;
            }
            .form-control:focus, .form-select:focus {
                border-color: #4a7c59;
                box-shadow: 0 0 0 0.2rem rgba(74, 124, 89, 0.25);
            }
        </style>
    </head>
    <body>
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
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet" data-section="dashboard">
                                <i class="fas fa-tachometer-alt me-2"></i> Trang chủ
                            </a>
                            <a class="nav-link active" href="<%= request.getContextPath()%>/AccountManagementServlet?action=list" data-section="account-management">
                                <i class="fas fa-users me-2"></i> Quản lý tài khoản
                            </a>
                            <a class="nav-link" href="<%= request.getContextPath()%>/AccountManagementServlet?action=statistics" data-section="statistics">
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
                            <div class="alert alert-${messageType}">
                                ${message}
                            </div>
                        </c:if>

                        <!-- Update Account -->
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h2><i class="fas fa-edit text-warning"></i> Cập nhật tài khoản</h2>
                            <div>
                                <a href="<%= request.getContextPath()%>/AccountManagementServlet?action=list&showSection=account-management&page=${currentPage}" class="btn btn-primary me-2">
                                    <i class="fas fa-arrow-left"></i> Quay lại
                                </a>
                                <a href="<%= request.getContextPath()%>/AccountManagementServlet?showSection=dashboard" class="btn btn-primary">
                                    <i class="fas fa-home"></i> Trở về trang chủ
                                </a>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <form action="<%= request.getContextPath()%>/AccountManagementServlet" method="POST" accept-charset="UTF-8">
                                    <input type="hidden" name="action" value="updateAccount">
                                    <input type="hidden" name="showSection" value="account-management">
                                    <input type="hidden" name="accountId" value="${selectedAccount.id}">
                                    <input type="hidden" name="roleId" value="${selectedAccount.roleId}">
                                    <input type="hidden" name="page" value="${currentPage}">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Vai trò</label>
                                            <c:forEach var="role" items="${roles}">
                                                <c:if test="${role.id == selectedAccount.roleId}">
                                                    <input type="text" class="form-control" value="${role.roleName}" readonly>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Tên người dùng <span class="text-danger">*</span></label>
                                            <input type="text" class="form-control" name="username" value="${selectedAccount.username}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Email <span class="text-danger">*</span></label>
                                            <input type="email" class="form-control" name="email" value="${selectedAccount.email}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Họ tên <span class="text-danger">*</span></label>
                                            <input type="text" class="form-control" name="fullName" value="${selectedDetail.fullName}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Số điện thoại <span class="text-danger">*</span></label>
                                            <input type="tel" class="form-control" name="phoneNumber" value="${selectedDetail.phoneNumber}" required pattern="[0-9]{10}">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Ngày sinh <span class="text-danger">*</span></label>
                                            <input type="date" class="form-control" name="dateOfBirth" value="${selectedDetail.dob != null ? selectedDetail.dob.toLocalDate() : ''}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Giới tính <span class="text-danger">*</span></label>
                                            <select class="form-select" name="gender" required>
                                                <option value="">Chọn giới tính</option>
                                                <option value="Nam" ${selectedDetail.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                                                <option value="Nữ" ${selectedDetail.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Địa chỉ</label>
                                            <input type="text" class="form-control" name="address" value="${selectedDetail.address}">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Ảnh đại diện (URL)</label>
                                            <input type="text" class="form-control" name="imageURL" value="${selectedDetail.imageURL}">
                                        </div>
                                        <!-- Doctor-specific fields -->
                                        <c:if test="${selectedAccount.roleId == doctorRoleId}">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Chuyên khoa <span class="text-danger">*</span></label>
                                                <select class="form-select" name="specializationId" required>
                                                    <option value="">Chọn chuyên khoa</option>
                                                    <c:forEach var="spec" items="${specializations}">
                                                        <option value="${spec.id}" ${spec.id == selectedDetail.specializationId ? 'selected' : ''}>${spec.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Trình độ <span class="text-danger">*</span></label>
                                                <select class="form-select" name="doctorLevelId" required>
                                                    <option value="">Chọn trình độ</option>
                                                    <c:forEach var="level" items="${doctorLevels}">
                                                        <option value="${level.id}" ${level.id == selectedDetail.doctorLevelId ? 'selected' : ''}>${level.levelName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </c:if>
                                    </div>
                                    <button type="submit" class="btn btn-success">
                                        <i class="fas fa-save"></i> Lưu thông tin
                                    </button>
                                    <button type="reset" class="btn btn-secondary ms-2">
                                        <i class="fas fa-undo"></i> Làm mới
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
