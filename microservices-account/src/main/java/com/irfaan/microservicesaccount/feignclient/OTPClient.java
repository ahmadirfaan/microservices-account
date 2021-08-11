package com.irfaan.microservicesaccount.feignclient;

import com.irfaan.microservicesaccount.dto.RegisterCheckDto;
import com.irfaan.microservicesaccount.dto.RegisterVerificationDto;
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
    public ResponseEntity<?> requestOTP(@RequestBody RegisterCheckDto registerCheckDto);

    @GetMapping("/test-loadbalancer")
    public String testLoadBalancer();

    @PostMapping("/verification")
    public ResponseEntity<?> verificationOTP(@RequestBody RegisterVerificationDto registerVerificationDto);
}
