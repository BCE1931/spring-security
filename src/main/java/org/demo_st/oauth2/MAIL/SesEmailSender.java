package org.demo_st.oauth2.MAIL;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class SesEmailSender {

    private final SesClient sesClient;

    private static final String FROM_EMAIL = "noreply@revise.codes";

    public SesEmailSender(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public boolean send(String email, String type, Long otp) {

        try {
            String subject;
            String body;

            switch (type) {

                case "signup":
                    subject = "Your OTP for Account Registration";
                    body = buildSignupBody(otp);
                    break;

                case "pwdreset":
                    subject = "OTP for Password Reset Request";
                    body = buildPasswordResetBody(otp);
                    break;

                case "created_successfully":
                    subject = "Welcome to revise.codes 🎉";
                    body = buildAccountCreatedBody();
                    break;

                default:
                    return false;
            }

            SendEmailRequest request = SendEmailRequest.builder()
                    .source(FROM_EMAIL)
                    .destination(Destination.builder()
                            .toAddresses(email)
                            .build())
                    .message(Message.builder()
                            .subject(Content.builder()
                                    .data(subject)
                                    .charset("UTF-8")
                                    .build())
                            .body(Body.builder()
                                    .text(Content.builder()
                                            .data(body)
                                            .charset("UTF-8")
                                            .build())
                                    .build())
                            .build())
                    .build();

            sesClient.sendEmail(request);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ---------------- EMAIL TEMPLATES ---------------- */

    private String buildSignupBody(Long otp) {
        return """
                Hello,

                Thank you for registering with revise.codes.

                Your One-Time Password (OTP) for account verification is:

                %d

                This OTP is valid for the next 5 minutes.
                Please do not share it with anyone.

                Best regards,
                Revise.codes Support Team
                """.formatted(otp);
    }

    private String buildPasswordResetBody(Long otp) {
        return """
                Hello,

                We received a request to reset your account password.

                Your One-Time Password (OTP) to proceed is:

                %d

                This OTP is valid for the next 5 minutes.
                Please do not share it with anyone.

                Best regards,
                Revise.codes Support Team
                """.formatted(otp);
    }

    private String buildAccountCreatedBody() {
        return """
                Hello,

                🎉 Your account has been created successfully!

                Welcome to revise.codes — your personal space for learning,
                revising, and tracking your progress.

                You can now log in and start using the platform.

                If you have any questions, feel free to reach out to us.

                Best regards,
                Revise.codes Support Team
                """;
    }
}
