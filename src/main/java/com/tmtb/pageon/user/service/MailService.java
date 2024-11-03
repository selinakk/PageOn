package com.tmtb.pageon.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "Gue2011505@gmail.com";


    // 아이디를 이메일로 보내는 메소드 추가
    public void sendIdByEmail(String recipientEmail, String id) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, recipientEmail);
        message.setSubject("아이디 찾기 결과");

        String body = "<h3>요청하신 아이디는 다음과 같습니다:</h3>";
        body += "<p><strong>아이디: </strong>" + id + "</p>";
        body += "<p>감사합니다.</p>";
        message.setText(body, "UTF-8", "html");

        javaMailSender.send(message);
    }
    // 아이디를 이메일로 보내는 메소드 추가
    public void sendRegisterIdByEmail(String recipientEmail, String id) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, recipientEmail);
        message.setSubject("회원가입 결과");

        String body = "<h3>회원가입한 이메일에 대한 아이디입니다.:</h3>";
        body += "<p><strong>이메일: </strong>" + recipientEmail + "</p>";
        body += "<p><strong>아이디: </strong>" + id + "</p>";
        body += "<p>감사합니다.</p>";
        message.setText(body, "UTF-8", "html");

        javaMailSender.send(message);
    }

}


