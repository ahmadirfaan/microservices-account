package com.irfaan.microservicesaccount.services;

import com.irfaan.microservicesaccount.db.entity.Account;
import com.irfaan.microservicesaccount.db.entity.TempAccount;
import com.irfaan.microservicesaccount.db.repository.AccountRepository;
import com.irfaan.microservicesaccount.db.repository.TempAccountRepository;
import com.irfaan.microservicesaccount.dto.RegisterCheckDto;
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

   @Autowired
   public AccountService(AccountRepository accountRepository, TempAccountRepository tempAccountRepository) {
      this.accountRepository = accountRepository;
      this.tempAccountRepository = tempAccountRepository;
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
      return ResponseEntity.ok().build();
   }
}
