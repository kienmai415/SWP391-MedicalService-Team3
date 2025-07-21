<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lịch khám của tôi</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
        <style>
            body {
                background-color: #f0fdf4;
            }
            .search-container {
                background-color: #059669;
                padding: 20px;
                border-radius: 12px 12px 0 0;
                color: white;
            }
            .search-box {
                border-radius: 8px;
                padding: 6px 12px;
                border: none;
                outline: none;
            }
            .btn-search {
                background-color: #047857;
                color: white;
                border-radius: 8px;
            }
            .btn-search:hover {
                background-color: #065f46;
            }
            .badge-pending {
                background-color: #34d399;
                color: #000;
            } /* Đã xác nhận */
            .badge-done {
                background-color: #6b7280;
                color: #fff;
            } /* Đã hoàn thành */
            .badge-no-show {
                background-color: #dc2626;
                color: white;
            } /* Không đến khám */
            .table {
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
            }
            .btn-action {
                border-radius: 50%;
                width: 32px;
                height: 32px;
                padding: 0;
            }
            .pagination .page-item.active .page-link {
                background-color: #059669;
                border-color: #059669;
            }
            .pagination .page-link {
                color: #059669;
            }
            .pagination .page-link:hover {
                background-color: #d1fae5;
            }
            .search-container {
                background-color: #059669;
                padding: 20px;
                border-radius: 12px 12px 0 0;
                color: white;
                justify-content: space-between;
            }

        </style>
    </head>
    <body>
        <div class="container my-4">
            <!-- Header + Search -->
            <div class="search-container d-flex justify-content-between align-items-center">
                <h4 class="mb-0">Lịch khám của tôi</h4>
                <a href="DoctorServlet" class="btn btn-search">
                    <i class="fas fa-arrow-left me-1"></i> Quay lại
                </a>

            </div>

            <!-- Table -->
            <div class="table-responsive">
                <table class="table table-hover table-bordered align-middle text-center">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Ngày</th>
                            <th>Thời gian</th>
                            <th>Bệnh nhân</th>
                            <th>Triệu chứng</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                            <th>Chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${appointmentList}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${item.appointDate}</td>
                                <td>${item.appointTime}</td>
                                <td><strong>${item.nameP}</strong></td>
                                <td>${item.symptom}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.status eq 'Đã xác nhận'}">
                                            <span class="badge badge-pending">Đã xác nhận</span>
                                        </c:when>
                                        <c:when test="${item.status eq 'Đã hoàn thành'}">
                                            <span class="badge badge-done">Đã hoàn thành</span>
                                        </c:when>
                                        <c:when test="${item.status eq 'Không đến khám'}">
                                            <span class="badge badge-no-show">Không đến khám</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">${item.status}</span>
                                        </c:otherwise>
                                    </c:choose>


                                </td>

                                <td>
                                    <c:if test="${item.status eq 'Đã xác nhận'}">
                                        <form action="AppointmentScheduleServlet" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="complete">
                                            <input type="hidden" name="appointmentId" value="${item.id}">
                                            <button type="submit" class="btn btn-success btn-sm btn-action" title="Đã đến khám">
                                                <i class="fas fa-check"></i>
                                            </button>
                                        </form>

                                        <form action="AppointmentScheduleServlet" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="noShow">
                                            <input type="hidden" name="appointmentId" value="${item.id}">
                                            <button type="submit" class="btn btn-danger btn-sm btn-action" title="Không đến khám">
                                                <i class="fas fa-xmark"></i>
                                            </button>
                                        </form>
                                    </c:if>


                                </td>

                                <td>
                                    <a href="AppointmentScheduleServlet?action=detail&appointmentId=${item.id}" class="btn btn-sm btn-outline-info">
                                        <i class="fas fa-info-circle"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Pagination -->
            <div class="d-flex justify-content-center">
                <ul class="pagination">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="AppointmentScheduleServlet?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>
