package com.irfaan.microservice.otp.controllers;

import com.irfaan.microservice.otp.dto.RegisterCheckDto;
import com.irfaan.microservice.otp.services.OTPService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

   private final Environment environment;

   @Autowired
   public OTPController(OTPService otpService, Environment environment) {
      this.otpService = otpService;
      this.environment = environment;
   }

   @PostMapping("/request")
   public ResponseEntity<?> requestOTP(@RequestBody RegisterCheckDto registerCheckDto) {
      log.debug("request OTP {}", registerCheckDto);
      otpService.requestOTP(registerCheckDto);
      return ResponseEntity.ok().build();
   }

   @GetMapping("/test-loadbalancer")
   public String testLoadBalancer() {
      String port = environment.getProperty("local.server.port");
      log.debug("port: {}", port);
      return "oke with port: " + port;
   }

   @PostMapping("/verification")
   public ResponseEntity<?> verificationOTP(@RequestBody ) {

   }

}
