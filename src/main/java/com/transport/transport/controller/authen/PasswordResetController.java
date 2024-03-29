package com.transport.transport.controller.authen;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.request.authen.ForgotPasswordForm;
import com.transport.transport.service.AccountService;
import com.transport.transport.utils.Utility;
import io.swagger.annotations.Api;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = EndpointConstant.Authentication.AUTHENTICATION_ENDPOINT)
@Api( tags = "ResetPassword")
public class PasswordResetController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AccountService accountService;


    @PostMapping("/forgot_password")
    public String processForgotPassword(@Valid @RequestBody ForgotPasswordForm form, HttpServletRequest request) {
        String email = form.getEmail();
        String token = RandomString.make(30);
        String msg = null;
        try {
            accountService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            msg = "We have sent a reset password link to your email. Please check.";
        } catch (Exception e) {
            msg = "Error while sending email: " + e.getMessage();
            throw new NotFoundException("Could not find any customer with the email " + email);
        }
        return msg;
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Transport System");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }


    @PostMapping("/reset_password")
    public String processResetPassword(String token,
                                       String password,
                                       String confirmPassword) {
        Account account = accountService.getByResetPasswordToken(token);
        if (account == null) {
            return "Invalid Token";
        } else {
            if (password.equals(confirmPassword)) {
                accountService.updatePassword(account, password);
                return "You have successfully changed your password.";
            } else {
                return "Passwords do not match";
            }
        }
    }
}
