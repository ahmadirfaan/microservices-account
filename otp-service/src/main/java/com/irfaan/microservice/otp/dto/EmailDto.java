package com.irfaan.microservice.otp.dto;

import lombok.Data;

/**
 * @author Ahmad Irfaan
 * @date 8/9/2021 2:23 PM
 * microservices-account
 */

@Data
public class EmailDto {
   private String to;
   private String subject;
   private String body;
}
