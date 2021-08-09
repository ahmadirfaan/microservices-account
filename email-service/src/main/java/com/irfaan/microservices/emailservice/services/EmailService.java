package com.irfaan.microservices.emailservice.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Ahmad Irfaan
 * @date 8/9/2021 2:09 PM
 * microservices-account
 */

@Log4j2
@Service
public class EmailService {
   private final JavaMailSender javaMailSender;

   @Autowired
   public EmailService(JavaMailSender javaMailSender) {
      this.javaMailSender = javaMailSender;
   }

   void sendEmail(String to, String subject, String body) {
      log.debug("send to: {}, subject: {}, body: {}", to, subject, body);
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);
      javaMailSender.send(message);
   }
}
