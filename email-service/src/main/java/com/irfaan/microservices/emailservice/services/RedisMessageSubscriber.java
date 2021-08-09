package com.irfaan.microservices.emailservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irfaan.microservices.emailservice.dto.EmailDto;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @author Ahmad Irfaan
 * @date 8/9/2021 2:21 PM
 * microservices-account
 */

@Service
public class RedisMessageSubscriber implements MessageListener {

   private final EmailService emailService;
   private ObjectMapper objectMapper = new ObjectMapper();

   public RedisMessageSubscriber(EmailService emailService) {
      this.emailService = emailService;
   }

   @Override
   public void onMessage(Message message, byte[] pattern) {
      String msg = message.toString();
      try {
         EmailDto emailDto = objectMapper.readValue(msg, EmailDto.class);
         emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
      } catch (JsonProcessingException e) {
         e.printStackTrace();
      }
   }
}
