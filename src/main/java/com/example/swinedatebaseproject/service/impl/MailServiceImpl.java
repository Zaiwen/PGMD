package com.example.swinedatebaseproject.service.impl;

import com.example.swinedatebaseproject.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.core.io.*;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
//    接收邮件
    @Value("${spring.mail.username}")
    private String sendMailer;


    @Override
    public void sendEmail(String to, String subject, String text, File attachment)
            throws IllegalArgumentException, IOException, MessagingException {
        System.out.println(attachment);
        // 验证附件是否符合要求
        if (attachment != null && !attachment.exists()) {
            throw new IllegalArgumentException("Attachment not found");
        }
        if (attachment != null && attachment.length() > 512 * 1024 * 1024) {
            throw new IllegalArgumentException("Attachment exceeds maximum size");
        }
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(sendMailer);
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
        // 创建MimeMessageHelper对象，并设置邮件内容
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
        messageHelper.setFrom(sendMailer);
        messageHelper.setTo("1875762624@qq.com"); // 设置收件人地址
//        messageHelper.setTo(to);
        messageHelper.setSubject(subject); // 设置邮件主题
        messageHelper.setText(text+"\n"+to); // 设置邮件内容

//        / 如果有附件，则添加附件
        if (attachment != null) {
            String attachmentName = StringUtils.cleanPath(attachment.getName());
            byte[] attachmentBytes = Files.readAllBytes(attachment.toPath());
            Resource attachmentResource = new ByteArrayResource(attachmentBytes);
            messageHelper.addAttachment(attachmentName, attachmentResource);
        }
        // 发送邮件
        mailSender.send(messageHelper.getMimeMessage());
//        mailSender.send(message);
        if (attachment != null) {
            attachment.delete();

        }
    }
}
