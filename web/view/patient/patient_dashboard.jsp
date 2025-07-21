<%-- 
    Document   : patient_dashboard
    Created on : Jun 9, 2025, 10:15:32 PM
    Author     : BB-MT
--%>

<%@ page session="true" %>
<%
    if (session.getAttribute("p") == null) {
        response.sendRedirect(request.getContextPath()+"/login");
        return;
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý lịch hẹn</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                min-height: 100vh;
                position: relative;
            }

            .sidebar {
                width: 280px;
                background-color: #4CAF50;
                min-height: 100vh;
                color: #fff;
                position: fixed;
                left: 0;
                top: 0;
                z-index: 1000;
                transition: all 0.3s ease;
            }

            .sidebar a {
                color: #fff;
                text-decoration: none;
                display: block;
                padding: 12px 20px;
                transition: all 0.3s;
            }

            .sidebar a:hover {
                background-color: #45a049;
            }

            .content {
                margin-left: 280px;
                padding: 20px;
                transition: all 0.3s ease;
            }

            .user-profile {
                padding: 20px 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }
            .user-profile img {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                display: block;
            }

            .table-responsive {
                overflow-x: auto;
            }

            .table th {
                white-space: nowrap;
                background-color: #f8f9fa;
            }

            .table td {
                white-space: nowrap;
            }

            .card {
                box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
            }

            @media (max-width: 991.98px) {
                .sidebar {
                    width: 200px;
                }
                .content {
                    margin-left: 200px;
                }
            }

            @media (max-width: 767.98px) {
                .sidebar {
                    width: 100%;
                    height: auto;
                    position: relative;
                    min-height: auto;
                }
                .content {
                    margin-left: 0;
                }
                .user-profile img {
                    width: 80px;
                    height: 80px;
                    border-radius: 50%;      /* Làm tròn ảnh */
                    object-fit: cover;       /* Ảnh luôn lấp đầy khung, không méo */
                    display: block;
                }
                .table-responsive {
                    max-width: 100%;
                }
            }

            .status-done {
                color: #28a745;
                font-weight: 500;
            }

            .status-pending {
                color: #ffc107;
                font-weight: 500;
            }

            .status-cancelled {
                color: #dc3545;
                font-weight: 500;
            }

            .btn-register {
                background-color: #4CAF50;
                color: white;
                padding: 8px 20px;
                border-radius: 4px;
                text-decoration: none;
                transition: all 0.3s ease;
            }

            .btn-register:hover {
                background-color: #45a049;
                color: white;
                text-decoration: none;
            }

            .pagination {
                flex-wrap: wrap;
            }

            .form-select {
                min-width: 100px;
            }
        </style>
    </head>
    <body>

        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="p-3 border-bottom">
                    <h5 class="text-center mb-0">MediGreen</h5>
                </div>
                <div class="user-profile">
                    <c:choose>
                        <c:when test="${not empty p.imageURL}">
                            <img src="${p.imageURL}" class="rounded-circle mb-3" alt="Ảnh đại diện">
                        </c:when>
                        <c:otherwise>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLLwxrW2r_nddAIEOkyvcQIPwQey-_MFUzrQ&s" class="rounded-circle mb-3" alt="Ảnh đại diện">
                        </c:otherwise>
                    </c:choose>
                    <h6 class="mb-1">${p.fullName}</h6>

                </div>
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/PatientServlet?action=history" class="active"><i class="fas fa-calendar-alt me-2"></i> Lịch sử đặt khám</a>
                    <a href="${pageContext.request.contextPath}/PatientServlet?action=schedule" class="active"><i class="fas fa-calendar-alt me-2"></i> Đăng ký khám</a>
                    <a href="logout"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
                </div>
            </div>

            <!-- Main Content -->
            <div class="content">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="view/patient/patient_dashboard.jsp" class="text-decoration-none">Trang chủ</a></li>
                            <c:choose>
                                <c:when test="${param.action == 'history'}">
                                <li class="breadcrumb-item active">Lịch sử khám bệnh</li>
                                </c:when>
                                <c:when test="${param.action == 'schedule'}">
                                <li class="breadcrumb-item active">Đặt lịch khám bệnh</li>
                                </c:when>
                                <c:otherwise>
                                <li class="breadcrumb-item active">Bảng điều khiển</li>
                                </c:otherwise>
                            </c:choose>
                    </ol>
                </nav>

                <!-- Nội dung chính thay đổi dựa trên tham số -->
                <c:choose>
                    <c:when test="${param.action == 'history'}">
                        <c:if test="${not empty pageContent}">
                            <jsp:include page="${pageContent}" />
                        </c:if>
                    </c:when>
                    <c:when test="${param.action == 'schedule'}">
                        <c:if test="${not empty pageContent}">
                            <jsp:include page="${pageContent}" />
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">Chào mừng bạn đến bảng điều khiển bệnh nhân. Vui lòng chọn một chức năng từ menu bên trái.</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Font Awesome for icons -->
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

    </body>
</html>

