package ru.arlabs.taskservice.mail;

/**
 * @author Jeb
 */
public interface MailService {
    void send(String email, String subject, String message);
}
