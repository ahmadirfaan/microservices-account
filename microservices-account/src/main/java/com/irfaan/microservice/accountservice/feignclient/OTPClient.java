package com.irfaan.microservice.accountservice.feignclient;

import com.irfaan.microservice.accountservice.dto.RegisterCheckDto;
import com.irfaan.microservice.accountservice.dto.RegisterVerificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Ahmad Irfaan
 * @date 8/7/2021 7:49 PM
 * microservices-account
 */

@FeignClient(value = "otp") //url dihilangkan karena kita tidak perlu mendaftarkan endpoint mana yang akan dituju
public interface OTPClient {
    @PostMapping("/request")
    ResponseEntity<?> requestOTP(@RequestBody RegisterCheckDto registerCheckDto);

    @GetMapping("/test-loadbalancer")
    String testLoadBalancer();

    @PostMapping("/verification")
    ResponseEntity<?> verificationOTP(@RequestBody RegisterVerificationDto registerVerificationDto);
}
