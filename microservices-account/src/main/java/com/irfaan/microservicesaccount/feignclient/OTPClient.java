package com.irfaan.microservicesaccount.feignclient;

import com.irfaan.microservicesaccount.dto.RegisterCheckDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Ahmad Irfaan
 * @date 8/7/2021 7:49 PM
 * microservices-account
 */

@FeignClient(value = "otp", url = "http://localhost:8020")
public interface OTPClient {
    @PostMapping("/request")
    public ResponseEntity<?> requestOTP(@RequestBody RegisterCheckDto registerCheckDto);
}
