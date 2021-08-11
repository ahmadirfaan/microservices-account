package com.irfaan.microservicesaccount.controllers;

import com.irfaan.microservicesaccount.dto.RegisterCheckDto;
import com.irfaan.microservicesaccount.dto.RegisterPasswordDto;
import com.irfaan.microservicesaccount.dto.RegisterVerificationDto;
import com.irfaan.microservicesaccount.services.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 8:05 AM
 * microservices-account
 */

@Log4j2 //bawaan lombok untuk memasang logger
@RestController
public class AccountController {

   private final AccountService accountService;

   @Autowired
   public AccountController(AccountService accountService) {
      this.accountService = accountService;
   }

   @PostMapping("/check")
   public ResponseEntity<?> registerCheck(@RequestBody RegisterCheckDto registerCheckDto) {
      log.debug("register {}", registerCheckDto);
      return accountService.registerCheck(registerCheckDto);
   }

   @GetMapping("/load")
   public String testLoadBalancer() {
      return accountService.testLoadBalancer();
   }

   @PostMapping("/verification")
   public ResponseEntity<?> verification(@RequestBody RegisterVerificationDto registerVerificationDto) {
      return accountService.verification(registerVerificationDto);
   }

   @PostMapping("/password")
   public ResponseEntity<?> registerPassword(@RequestBody RegisterPasswordDto registerPasswordDto) {
      return accountService.registerPasswordDto(registerPasswordDto);
   }
}
