<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    /* Đặt chiều rộng cố định cho từng cột để chia đều */
    th, td {
        white-space: nowrap;
        text-align: center;
        vertical-align: middle;
    }

    th:nth-child(1), td:nth-child(1) {
        width: 120px;
    } /* Bệnh nhân */
    th:nth-child(2), td:nth-child(2) {
        width: 150px;
    } /* Bác sĩ */
    th:nth-child(3), td:nth-child(3) {
        width: 140px;
    } /* SĐT bác sĩ */
    th:nth-child(4), td:nth-child(4) {
        width: 100px;
    } /* Chức vụ */
    th:nth-child(5), td:nth-child(5) {
        width: 100px;
    } /* Chuyên môn */
    th:nth-child(6), td:nth-child(6) {
        width: 110px;
    } /* Ngày khám */
    th:nth-child(7), td:nth-child(7) {
        width: 130px;
    } /* Khung giờ */
    th:nth-child(8), td:nth-child(8) {
        width: 110px;
    } /* Trạng thái */
    th:nth-child(9), td:nth-child(9) {
        width: 90px;
    }  /* Thao tác */

    /* Tùy chỉnh màu trạng thái */
    .status-success {
        color: green;
        font-weight: bold;
    }

    .status-warning {
        color: orange;
        font-weight: bold;
    }

    .status-primary {
        color: blue;
        font-weight: bold;
    }

    .status-danger {
        color: red;
        font-weight: bold;
    }

    .status-muted {
        color: gray;
        font-weight: bold;
    }
</style>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h4 class="mb-0">Lịch sử khám bệnh</h4>
    <a href="${pageContext.request.contextPath}/patient?action=schedule" class="btn-register">Đăng ký khám</a>
</div>

<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Bệnh nhân</th>
                        <th>Bác sĩ</th>
                        <th>Số điện thoại Bác sĩ</th>
                        <th>Chức vụ</th>
                        <th>Chuyên môn</th>
                        <th>Ngày khám</th>
                        <th>Khung giờ</th>
                        <th>Trạng thái</th>
                        <th class="action-column">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${historyList}" var="hl">
                        <tr>
                            <td>${hl.nameP}</td>
                            <td>${hl.nameD}</td>
                            <td>${hl.phoneD}</td>
                            <td>${hl.levelD}</td>
                            <td>${hl.specD}</td>
                            <td>${hl.appointDate}</td>
                            <td>${hl.appointTime}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${hl.status eq 'Đã khám'}">
                                        <span class="status-done">${hl.status}</span>
                                    </c:when>
                                    <c:when test="${hl.status eq 'Đang chờ xử lý'}">
                                        <span class="status-pending">${hl.status}</span>
                                    </c:when>
                                    <c:when test="${hl.status eq 'Đã xác nhận'}">
                                        <span class="status-primary">${hl.status}</span>
                                    </c:when>
                                    <c:when test="${statusDisplay eq 'Đã hủy'}">
                                        <span class="status-danger">${hl.status}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-muted">${hl.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết">
                                    <i class="fas fa-eye"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="d-flex justify-content-between align-items-center mt-3">
            <nav>
                <ul class="pagination mb-0">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage eq i ? 'active' : ''}">
                            <a class="page-link" href="lich-su-kham?page=${i}&records=${recordsPerPage}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
            <div>
                <select class="form-select form-select-sm" style="width: auto;" 
                        onchange="window.location.href = 'lich-su-kham?page=1&records=' + this.value">
                    <option value="10" ${recordsPerPage eq 10 ? 'selected' : ''}>10 / trang</option>
                    <option value="20" ${recordsPerPage eq 20 ? 'selected' : ''}>20 / trang</option>
                    <option value="50" ${recordsPerPage eq 50 ? 'selected' : ''}>50 / trang</option>
                </select>
            </div>
        </div>
    </div>
</div>
