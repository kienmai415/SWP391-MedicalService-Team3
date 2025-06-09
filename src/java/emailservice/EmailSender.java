package emailService;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private final String from = "duongtuana87236@gmail.com";
    private final String password = "iwhp bduh nptc zfac";

    public boolean sendEmail(String to, String name, String newPass) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject("Đặt Lại Mật Khẩu", "UTF-8");
            
            String htmlContent = "<html><body>"
                    + "<p>Xin chào " + name + ",</p>"
                    + "<p>Bạn đã yêu cầu đặt lại mật khẩu. Đây là mật khẩu mới của bạn:</p>"
                    + "<p><strong>" + newPass + "</strong></p>"
                    + "<p>Vui lòng đăng nhập bằng mật khẩu này và đổi mật khẩu ngay sau khi đăng nhập.</p>"
                    + "<p>Cảm ơn bạn đã sử dụng dịch vụ WED của chúng tôi!</p>"
                    + "</body></html>";
            
            msg.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;
        } catch (MessagingException e) {
            System.out.println("Gửi email thất bại: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
