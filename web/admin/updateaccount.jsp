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
        <div class="main-content">
            <!-- Messages -->
            <c:if test="${not empty message}">
                <div class="alert alert-${messageType}">
                    ${message}
                </div>
            </c:if>

            <!-- Update Account Form -->
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
                    <form action="<%= request.getContextPath()%>/AccountManagementServlet" method="POST" accept-charset="UTF-8" id="updateAccountForm">
                        <input type="hidden" name="action" value="updateAccount">
                        <input type="hidden" name="accountId" value="${selectedAccount.id}">
                        <input type="hidden" name="page" value="${currentPage}">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Vai trò <span class="text-danger">*</span></label>
                                <select class="form-select" name="role" required onchange="toggleFields(this)">
                                    <option value="">Chọn vai trò</option>
                                    <c:forEach var="role" items="${roles}">
                                        <option value="${role}" ${selectedAccount.role == role ? 'selected' : ''}>${role}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Tên người dùng ${selectedAccount.role != 'Patient' ? '<span class="text-danger">*</span>' : ''}</label>
                                <input type="text" class="form-control" name="username" value="${selectedAccount.role == 'Patient' ? selectedAccount.userName : selectedAccount.username}" 
                                       ${selectedAccount.role != 'Patient' ? 'required' : ''} pattern="[a-zA-Z0-9]{6,}" title="Tên người dùng phải ít nhất 6 ký tự, chỉ chứa chữ cái hoặc số">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Email <span class="text-danger">*</span></label>
                                <input type="email" class="form-control" name="email" value="${selectedAccount.email}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Họ tên <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="fullName" value="${selectedAccount.fullName}" required pattern="[\\p{L}]+( {1,4}[\\p{L}]+)*" title="Họ tên phải có ít nhất 8 ký tự, chỉ chứa chữ cái tiếng Việt, tối đa 4 khoảng trắng">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Số điện thoại <span class="text-danger">*</span></label>
                                <input type="tel" class="form-control" name="phoneNumber" value="${selectedAccount.phoneNumber}" required pattern="[0-9]{10}" title="Số điện thoại phải là 10 chữ số">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Ngày sinh <span class="text-danger">*</span></label>
                                <input type="date" class="form-control" name="dateOfBirth" value="${selectedAccount.dob}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Giới tính <span class="text-danger">*</span></label>
                                <select class="form-select" name="gender" required>
                                    <option value="">Chọn giới tính</option>
                                    <option value="Nam" ${selectedAccount.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                                    <option value="Nữ" ${selectedAccount.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Địa chỉ <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="address" value="${selectedAccount.address}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Ảnh đại diện (URL)</label>
                                <input type="text" class="form-control" name="imageURL" value="${selectedAccount.imageURL}">
                            </div>
                            <!-- Doctor-specific fields -->
                            <div class="col-md-6 mb-3 doctor-field" style="${selectedAccount.role == 'Doctor' ? 'display: block;' : 'display: none;'}">
                                <label class="form-label">Chuyên khoa <span class="text-danger">*</span></label>
                                <select class="form-select" name="specializationId" ${selectedAccount.role == 'Doctor' ? 'required' : ''}>
                                    <option value="">Chọn chuyên khoa</option>
                                    <c:forEach var="spec" items="${specializations}">
                                        <option value="${spec.id}" ${selectedAccount.specialization != null && selectedAccount.specialization.id == spec.id ? 'selected' : ''}>${spec.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3 doctor-field" style="${selectedAccount.role == 'Doctor' ? 'display: block;' : 'display: none;'}">
                                <label class="form-label">Trình độ <span class="text-danger">*</span></label>
                                <select class="form-select" name="doctorLevelId" ${selectedAccount.role == 'Doctor' ? 'required' : ''}>
                                    <option value="">Chọn trình độ</option>
                                    <c:forEach var="level" items="${doctorLevels}">
                                        <option value="${level.id}" ${selectedAccount.doctorLevel != null && selectedAccount.doctorLevel.id == level.id ? 'selected' : ''}>${level.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            function toggleFields(select) {
                console.log("toggleFields called with value: " + select.value);
                const doctorFields = document.querySelectorAll('.doctor-field');
                if (select.value === 'Doctor') {
                    doctorFields.forEach(field => {
                        field.style.display = 'block';
                        field.querySelector('select').setAttribute('required', 'true');
                    });
                } else {
                    doctorFields.forEach(field => {
                        field.style.display = 'none';
                        field.querySelector('select').removeAttribute('required');
                    });
                }
            }
        </script>
    </body>
</html>