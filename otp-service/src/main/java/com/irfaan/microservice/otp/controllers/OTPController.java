package com.irfaan.microservice.otp.controllers;

import com.irfaan.microservice.otp.dto.RegisterCheckDto;
import com.irfaan.microservice.otp.services.OTPService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 10:18 AM
 * otp-service
 */

@Log4j2
@RestController
public class OTPController {


   private final OTPService otpService;

   @Autowired
   public OTPController(OTPService otpService) {
      this.otpService = otpService;
   }

   @PostMapping("/request")
   public ResponseEntity<?> requestOTP(@RequestBody RegisterCheckDto registerCheckDto) {
      log.debug("request OTP {}", registerCheckDto);
      otpService.requestOTP(registerCheckDto);
      return ResponseEntity.ok().build();
   }
}
