package com.example.servercurs.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("arinakornilovait16@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");


    }

    public void sendEmailWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment(file.getFilename(), file);

        mailSender.send(message);
    }

}
