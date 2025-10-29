package com.example.swinedatebaseproject.service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;

public interface MailService {
    void sendEmail(String to, String subject, String text, File attachment)
            throws IllegalArgumentException, IOException, MessagingException;
}
