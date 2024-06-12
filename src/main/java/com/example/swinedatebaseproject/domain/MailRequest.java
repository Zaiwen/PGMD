package com.example.swinedatebaseproject.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class MailRequest implements Serializable {
    private String to;
    private String subject;
    private String text;

    private MultipartFile attachment;
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

}
