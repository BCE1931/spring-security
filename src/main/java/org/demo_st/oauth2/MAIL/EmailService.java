package org.demo_st.oauth2.MAIL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public boolean send(String email , String type , Long otp){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom(fromEmail);

            if (Objects.equals(type, "signup")) {
                message.setSubject("Your OTP for Account Registration");

                message.setText(
                        "Hello,\n\n" +
                                "Thank you for registering with us.\n\n" +
                                "Your One-Time Password (OTP) for account verification is:\n\n" +
                                otp + "\n\n" +
                                "This OTP is valid for the next 5 minutes. Please do not share it with anyone.\n\n" +
                                "If you did not request this, please ignore this email.\n\n" +
                                "Best regards,\n" +
                                "Support Team"
                );

            } else if (Objects.equals(type, "pwdreset")) {
                message.setSubject("OTP for Password Reset Request");

                message.setText(
                        "Hello,\n\n" +
                                "We received a request to reset your account password.\n\n" +
                                "Your One-Time Password (OTP) to proceed is:\n\n" +
                                otp + "\n\n" +
                                "This OTP is valid for the next 5 minutes. Please do not share it with anyone.\n\n" +
                                "If you did not request a password reset, please secure your account immediately.\n\n" +
                                "Best regards,\n" +
                                "Support Team"
                );
            }

            mailSender.send(message);
            return true;

        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
