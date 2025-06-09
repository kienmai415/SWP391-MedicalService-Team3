<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Thông tin khám bệnh</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .info-table {
                width: 80%;
                margin: auto;
                border: 1px solid #ccc;
                font-size: 16px;
            }

            .info-table td, .info-table th {
                padding: 8px 12px;
                vertical-align: middle;
            }

            .info-header {
                font-weight: bold;
                background-color: #f2f2f2;
            }

            .patient-photo {
                width: 120px;
                height: 120px;
                object-fit: cover;
                border: 1px solid #ccc;
            }

            .label {
                font-weight: bold;
                white-space: nowrap;
            }
        </style>
    </head>
    <body>
        <div class="container mt-3">
            <div class="mt-4 d-flex justify-content-start">
                <a href="./ListAppointment" class="btn btn-secondary">⬅ Quay lại danh sách</a>
            </div>
            <div class="d-flex justify-content-end mb-3">           
                <button class="btn btn-primary" onclick="toggleForm()">✏️ </button>
            </div>
            <h3 class="text-center mb-4">🧾 PHIẾU THÔNG TIN KHÁM BỆNH</h3>
            <table class="info-table table-bordered">
                <tr>
                    <td rowspan="4" style="text-align: center;">
                        <img src="img/${appointmentInfo.patient.image}" width="120" class="patient-photo" alt="Patient Image">

                    </td>
                    <td class="label">Mã BN:</td>
                    <td>${appointmentInfo.patient.id}</td>
                    <td class="label">Ngày khám:</td>
                    <td>${appointmentInfo.dateAppointment}</td>
                </tr>
                <tr>
                    <td class="label">Họ tên:</td>
                    <td><strong>${appointmentInfo.patient.name}</strong></td>
                    <td class="label">Giờ khám:</td>
                    <td>${appointmentInfo.startTime} - ${appointmentInfo.endTime}</td>
                </tr>
                <tr>
                    <td class="label">Ngày sinh:</td>
                    <td>${appointmentInfo.patient.dateOfBirth}</td>
                    <td class="label">Giới tính:</td>
                    <td>${appointmentInfo.patient.gender}</td>
                </tr>
                <tr>
                    <td class="label">Số điện thoại:</td>
                    <td>${appointmentInfo.patient.phoneNumber}</td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td class="label">Bác sĩ phụ trách:</td>
                    <td colspan="2">${appointmentInfo.doctor.name}</td>
                    <td class="label">SĐT Bác sĩ:</td>
                    <td>${appointmentInfo.doctor.phoneNumber}</td>
                </tr>
            </table>
            <div id="editForm" class="mt-4" style="display: none;">
                <h5 class="mb-3"></h5>
                <table class="table table-bordered">
                    <tr>
                        <th>Medical Record ID</th>
                        <td><input type="text" class="form-control" name="medical_record_id"></td>
                    </tr>
                    <tr>
                        <th>Weight (kg)</th>
                        <td><input type="number" class="form-control" name="weight"></td>
                    </tr>
                    <tr>
                        <th>Height (cm)</th>
                        <td><input type="number" class="form-control" name="height"></td>
                    </tr>
                    <tr>
                        <th>Body Temperature (°C)</th>
                        <td><input type="number" class="form-control" name="body_temperature"></td>
                    </tr>
                    <tr>
                        <th>Heartbeat (lần/phút)</th>
                        <td><input type="number" class="form-control" name="heartbeat"></td>
                    </tr>
                    <tr>
                        <th>Blood Pressure (mmHg)</th>
                        <td><input type="text" class="form-control" name="blood_pressure"></td>
                    </tr>
                    <tr>
                        <th>Detail Medical</th>
                        <td><textarea class="form-control" name="detail_medical" rows="2"></textarea></td>
                    </tr>
                    <tr>
                        <th>Diagnose</th>
                        <td><textarea class="form-control" name="diagnose" rows="2"></textarea></td>
                    </tr>
                    <tr>
                        <th>Solution</th>
                        <td><textarea class="form-control" name="solution" rows="2"></textarea></td>
                    </tr>
                </table>
            </div>
        </div>

    </div>

    <script>
        function toggleForm() {
            const form = document.getElementById("editForm");
            form.style.display = form.style.display === "none" ? "block" : "none";
        }
    </script>
</body>

</html>
