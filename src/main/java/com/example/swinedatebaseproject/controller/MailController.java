package com.example.swinedatebaseproject.controller;

import com.example.swinedatebaseproject.domain.MailRequest;
import com.example.swinedatebaseproject.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/mail")
public class MailController {
    @Autowired
    private MailService mailService;


    // 邮箱地址验证的正则表达式
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);




    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@ModelAttribute MailRequest mailRequest)
            throws MessagingException, IOException {
        File attachmentFile = null;
        try {
            String to = mailRequest.getTo(); // 获取收件人地址
            String subject = mailRequest.getSubject(); // 获取邮件主题
            String text = mailRequest.getText(); // 获取邮件内容
            MultipartFile attachment = mailRequest.getAttachment(); // 获取附件
//            System.out.println(attachment.getBytes().length);
            Matcher matcher = pattern.matcher(to);
            if (!matcher.matches()) {
                return ResponseEntity.badRequest().body("Invalid email address");
            } // 返回错误响应
            // 如果有附件，则将MultipartFile转换为File

            /**方法一：
             使用MultipartFile的transferTo()方法将文件内容保存到本地文件中，然后将本地文件封装成File对象。权限不够，会生成空文件
             方法二：
             使用MultipartFile的getBytes()方法获取文件内容的字节数组，
             然后使用FileOutputStream类将字节数组写入本地文件，最后将本地文件封装成File对象。
             * */
            attachmentFile = null;
            if (attachment != null && !attachment.isEmpty()) {
                attachmentFile = new File(StringUtils.cleanPath(Objects.requireNonNull(attachment.getOriginalFilename())));
//                attachmentFile = new File(StringUtils.cleanPath(attachment.getOriginalFilename()));
//                String fileName = attachment.getOriginalFilename();
//                attachmentFile = File.createTempFile(fileName, null);
//                attachment.transferTo(attachmentFile);
                byte[] bytes = attachment.getBytes();
                FileOutputStream outputStream = new FileOutputStream(attachmentFile);
                outputStream.write(bytes);
                outputStream.close();
//                File newFile = new File(attachmentFile.getParent(), fileName);
//                attachmentFile.renameTo(newFile);
            }
            System.out.println("调用接口");
            mailService.sendEmail(to, subject, text, attachmentFile); // 调用MailService接口发送邮件
            return ResponseEntity.ok("Mail sent successfully");// 返回成功响应}
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage()); // 返回验证错误响应
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mail sending failed: " + ex.getMessage()); // 返回错误响应
        } finally {
            // 删除附件文件
            if (attachmentFile != null && attachmentFile.exists()) {
                attachmentFile.delete();
            }
        }
    }


//            <----------------------------------------- private methods---------------------------------》
// 如果有附件，则将MultipartFile转换为File
    private File convertToFile(MultipartFile attachment) throws IOException {
        if (attachment != null && !attachment.isEmpty()) {
//
//            String fileName = attachment.getOriginalFilename();
//            File file = File.createTempFile(fileName, null);
//            不知道为什么下面这行代码转化文件长度为0 ，难道用测试工具上传文件没有权限吗？
            File file = new File(attachment.getOriginalFilename());
            attachment.transferTo(file);
            System.out.println(file.length());
            if (file.length() > 512 * 1024 * 1024) {
                throw new IllegalArgumentException("Attachment size exceeds 512 MB limit");
            }
            return file;
        } else {
            return null;
        }
    }

}
