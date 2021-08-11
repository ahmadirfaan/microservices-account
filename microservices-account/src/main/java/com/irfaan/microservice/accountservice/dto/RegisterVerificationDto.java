package com.irfaan.microservice.accountservice.dto;

import lombok.Data;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 10:20 AM
 * microservices-account
 */

@Data
public class RegisterVerificationDto {
    private String email;
    private String otp;
}
