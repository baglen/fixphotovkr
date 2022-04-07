package ru.arlabs.taskservice.mail.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.arlabs.taskservice.mail.MailService;

/**
 * @author Jeb
 */
@Service
public class JavaMailService implements MailService {
    private final MailSender mailSender;
    private final String from;

    @Autowired
    public JavaMailService(MailSender mailSender, @Value("${spring.mail.username}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @Override
    public void send(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setFrom(from);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
