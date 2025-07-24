<%-- 
    Document   : vnpay_return
    Created on : Jul 22, 2025, 4:11:35 AM
    Author     : MinhQuang
--%>

<%@ page import="java.util.*, com.vnpay.common.Config, dal.AppointmentScheduleDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Kết quả thanh toán VNPay</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f4f6f8;
                margin: 0;
                padding: 40px;
                text-align: center;
            }

            .result-box {
                max-width: 500px;
                margin: 0 auto;
                background: white;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }

            h2 {
                color: #2c3e50;
            }
            .success {
                color: #27ae60;
            }
            .fail {
                color: #e74c3c;
            }
            .warning {
                color: #f39c12;
            }

            .info {
                margin-top: 20px;
                font-size: 16px;
            }

            .info p {
                margin: 6px 0;
            }

            .btn-back {
                margin-top: 25px;
            }

            .btn-back button {
                padding: 10px 20px;
                background-color: #3498db;
                color: white;
                border: none;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
            }

            .btn-back button:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <div class="result-box">
            <%
                Map<String, String> fields = new HashMap<>();
                Enumeration<String> paramNames = request.getParameterNames();
                String vnp_SecureHash = "";

                while (paramNames.hasMoreElements()) {
    String paramName = paramNames.nextElement();
    String paramValue = request.getParameter(paramName);

    // Bỏ qua cả SecureHash và SecureHashType khi tính lại chữ ký
    if (!"vnp_SecureHash".equals(paramName) && !"vnp_SecureHashType".equals(paramName)) {
        fields.put(paramName, paramValue);
    }

    // Riêng vnp_SecureHash vẫn lấy để đối chiếu
    if ("vnp_SecureHash".equals(paramName)) {
        vnp_SecureHash = paramValue;
    }
}

                String signValue = Config.hashAllFields(fields);

                if (vnp_SecureHash.equals(signValue)) {
                    String responseCode = fields.get("vnp_ResponseCode");
                    if ("00".equals(responseCode)) {
                        // ✅ Sau khi thanh toán thành công, lưu lịch hẹn vào DB
                        Integer patientId = (Integer) session.getAttribute("patientId");
                        String doctorIdStr = (String) session.getAttribute("doctorId");
                        String date = (String) session.getAttribute("appointmentDate");
                        String time = (String) session.getAttribute("appointmentTime");
                        String symptom = (String) session.getAttribute("symptom");

                        boolean inserted = false;

                        if (patientId != null && doctorIdStr != null && date != null && time != null) {
                            try {
                                int doctorId = Integer.parseInt(doctorIdStr);
                                AppointmentScheduleDAO dao = new AppointmentScheduleDAO();
                                dao.insertAppointmentAfterPayment(patientId, doctorId, date, time, symptom);
                                inserted = true;

                                // Xoá session
                                session.removeAttribute("doctorId");
                                session.removeAttribute("appointmentDate");
                                session.removeAttribute("appointmentTime");
                                session.removeAttribute("symptom");

                            } catch (Exception e) {
                                inserted = false;
                            }
                        }

                        if (inserted) {
            %>
            <h2 class="success">✅ Thanh toán thành công!</h2>
            <div class="info">
                <p><strong>Mã giao dịch:</strong> <%= fields.get("vnp_TxnRef") %></p>
                <p><strong>Mã ngân hàng:</strong> <%= fields.get("vnp_BankCode") %></p>
                <p><strong>Số tiền:</strong> <%= Integer.parseInt(fields.get("vnp_Amount")) / 100 %> VND</p>
                <p><strong>Thời gian:</strong> <%= fields.get("vnp_PayDate") %></p>
            </div>
            <%
                        } else {
            %>
            <h2 class="fail">❌ Không thể lưu lịch hẹn!</h2>
            <div class="info">
                <p>Dữ liệu không hợp lệ hoặc thiếu trong session.</p>
            </div>
            <%
                        }
                    } else {
            %>
            <h2 class="warning">⚠️ Thanh toán thất bại!</h2>
            <div class="info">
                <p><strong>Mã lỗi:</strong> <%= fields.get("vnp_ResponseCode") %></p>
                <p>Vui lòng thử lại hoặc liên hệ hỗ trợ.</p>
            </div>
            <%
                    }
                } else {
            %>
            <h2 class="fail">❌ Xác minh chữ ký thất bại!</h2>
            <div class="info">
                <p>Chữ ký không hợp lệ. Giao dịch có thể bị giả mạo.</p>
            </div>
            <%
                }
            %>

            <!-- ✅ Nút quay lại lịch sử hẹn khám -->
            <div class="btn-back">
                <form action="PatientServlet" method="get">
                    <input type="hidden" name="action" value="history" />
                    <button type="submit">Quay lại lịch sử hẹn khám</button>
                </form>
            </div>
        </div>
    </body>
</html>
