package com.irfaan.microservicesaccount.services;


import com.irfaan.microservicesaccount.db.entity.Account;
import com.irfaan.microservicesaccount.db.entity.TempAccount;
import com.irfaan.microservicesaccount.db.repository.AccountRepository;
import com.irfaan.microservicesaccount.db.repository.TempAccountRepository;
import com.irfaan.microservicesaccount.dto.RegisterCheckDto;
import com.irfaan.microservicesaccount.dto.RegisterPasswordDto;
import com.irfaan.microservicesaccount.dto.RegisterVerificationDto;
import com.irfaan.microservicesaccount.feignclient.OTPClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 8:07 AM
 * microservices-account
 */

@Service
public class AccountService {

   private final AccountRepository accountRepository;
   private final TempAccountRepository tempAccountRepository;
   private final OTPClient otpClient;

   @Autowired
   public AccountService(AccountRepository accountRepository, TempAccountRepository tempAccountRepository, OTPClient otpClient) {
      this.accountRepository = accountRepository;
      this.tempAccountRepository = tempAccountRepository;
      this.otpClient = otpClient;
   }


   public ResponseEntity<?> registerCheck(RegisterCheckDto registerCheckDto) {
      String email = registerCheckDto.getEmail();

      // perlu cek data di postgres, apakah email yang didaftarkan sudah ada
      Account accountByEmail = accountRepository.getFirstByEmail(email);
      if(accountByEmail != null) return ResponseEntity.status(HttpStatus.CONFLICT).build();

      // perlu mengecek di redis apakah sudah pernah register atau belum
      TempAccount tempAccountByEmail = tempAccountRepository.getFirstByEmail(email);
      if(tempAccountByEmail != null) return ResponseEntity.ok().build();

      // save ke temp redis
      tempAccountByEmail = new TempAccount();
      tempAccountByEmail.setEmail(email);
      tempAccountByEmail.setValid(false);
      tempAccountRepository.save(tempAccountByEmail);

      // request OTP
      try {
         otpClient.requestOTP(registerCheckDto);
      } catch (FeignException.FeignClientException ex) {
         ResponseEntity.status(ex.status()).body(ex.contentUTF8());
      }
      return ResponseEntity.ok().build();
   }

    public String testLoadBalancer() {
      return otpClient.testLoadBalancer();
    }

    public ResponseEntity<?> verification(RegisterVerificationDto registerVerificationDto) {
      // check redis
       TempAccount tempAccountByEmail = tempAccountRepository.getFirstByEmail(registerVerificationDto.getEmail());
       if(tempAccountByEmail == null) {
          return ResponseEntity.notFound().build();
       }

       // verification otp
       try {
          otpClient.verificationOTP(registerVerificationDto);
       } catch (FeignException.FeignClientException ex) {
          ex.printStackTrace();
          return ResponseEntity.unprocessableEntity().build();
       }

       // update verification
       tempAccountByEmail.setValid(true);
       tempAccountRepository.save(tempAccountByEmail);

       return ResponseEntity.ok().build();
    }

   public ResponseEntity<?> registerPasswordDto(RegisterPasswordDto registerPasswordDto) {
      // check redis
      TempAccount tempAccount = tempAccountRepository.getFirstByEmail(registerPasswordDto.getEmail());
      if (tempAccount == null) return ResponseEntity.notFound().build();

      // verification valid
      if (!tempAccount.isValid()) return ResponseEntity.unprocessableEntity().build();

      // save to postgres
      Account account = new Account();
      account.setEmail(registerPasswordDto.getEmail());
      account.setPassword(registerPasswordDto.getPassword());
      try {
         accountRepository.save(account);
      } catch (Exception ex) {
         ex.printStackTrace();
         return ResponseEntity.unprocessableEntity().build();
      }

      // delete tempaccount
      tempAccountRepository.delete(tempAccount);

      return ResponseEntity.ok().build();
   }
}
