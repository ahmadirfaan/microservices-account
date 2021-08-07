package com.irfaan.microservice.otp.db.repository;

import com.irfaan.microservice.otp.db.entity.TempOTP;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 10:13 AM
 * otp-service
 */
public interface TempOTPRepository extends CrudRepository<TempOTP, String> {
   TempOTP getFirstByEmail(String email);
}
