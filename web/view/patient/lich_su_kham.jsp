<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    th, td {
        white-space: nowrap;
        text-align: center;
        vertical-align: middle;
    }
    th:nth-child(1), td:nth-child(1) { width: 120px; }
    th:nth-child(2), td:nth-child(2) { width: 150px; }
    th:nth-child(3), td:nth-child(3) { width: 140px; }
    th:nth-child(4), td:nth-child(4) { width: 100px; }
    th:nth-child(5), td:nth-child(5) { width: 100px; }
    th:nth-child(6), td:nth-child(6) { width: 110px; }
    th:nth-child(7), td:nth-child(7) { width: 130px; }
    th:nth-child(8), td:nth-child(8) { width: 110px; }
    th:nth-child(9), td:nth-child(9) { width: 90px; }

    .status-success { color: green; font-weight: bold; }
    .status-warning { color: orange; font-weight: bold; }
    .status-primary { color: blue; font-weight: bold; }
    .status-danger { color: red; font-weight: bold; }
    .status-muted { color: gray; font-weight: bold; }
</style>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h4 class="mb-0">Lịch sử khám bệnh</h4>
    <a href="${pageContext.request.contextPath}/PatientServlet?action=schedule" class="btn-register">Đăng ký khám</a>
</div>

<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <c:choose>
                <c:when test="${not empty historyList}">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Triệu chứng</th>
                                <th>Bác sĩ</th>
                                <th>SĐT Bác sĩ</th>
                                <th>Trình độ</th>
                                <th>Chuyên môn</th>
                                <th>Ngày khám</th>
                                <th>Khung giờ</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${historyList}" var="hl">
                                <tr>
                                    <td>${hl.symptom}</td>
                                    <td>${hl.nameD}</td>
                                    <td>${hl.phoneD}</td>
                                    <td>${hl.levelD}</td>
                                    <td>${hl.specD}</td>
                                    <td>${hl.appointDate}</td>
                                    <td>${hl.appointTime}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${hl.status eq 'Đã hoàn thành'}">
                                                <span class="status-success">${hl.status}</span>
                                            </c:when>
                                            <c:when test="${hl.status eq 'Đang chờ xử lý'}">
                                                <span class="status-warning">${hl.status}</span>
                                            </c:when>
                                            <c:when test="${hl.status eq 'Đã xác nhận'}">
                                                <span class="status-primary">${hl.status}</span>
                                            </c:when>
                                            <c:when test="${hl.status eq 'Không đến khám'}">
                                                <span class="status-danger">${hl.status}</span>
                                            </c:when>
                                            <c:when test="${hl.status eq 'Đã hủy'}">
                                                <span class="status-muted">${hl.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${hl.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info text-center">
                        Không có lịch sử khám bệnh nào để hiển thị.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
