package com.irfaan.microservice.otp.services;

import com.irfaan.microservice.otp.db.entity.TempOTP;
import com.irfaan.microservice.otp.db.repository.TempOTPRepository;
import com.irfaan.microservice.otp.dto.RegisterCheckDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 10:26 AM
 * otp-service
 */

@Log4j2
@Service
public class OTPService {

    private final TempOTPRepository tempOTPRepository;

    @Autowired
    public OTPService(TempOTPRepository tempOTPRepository) {
        this.tempOTPRepository = tempOTPRepository;
    }

    public void requestOTP(RegisterCheckDto registerCheckDto){
        String email = registerCheckDto.getEmail();
        //Check OTP Redis
        TempOTP tempOTPByEmail = tempOTPRepository.getFirstByEmail(email);
        if (tempOTPByEmail != null) {
            tempOTPRepository.delete(tempOTPByEmail);
        }
        //Generate Random Number / OTP
        String randomOTP = generateOTP();
        log.debug("random otp {}", randomOTP);
        //Save ke Redis
        TempOTP tempOTP = new TempOTP();
        tempOTP.setEmail(email);
        tempOTP.setOtp(randomOTP);
        tempOTPRepository.save(tempOTP);

        // Send to message broker
    }

    public void sendEmail(String to, String body) {
        log.debug("to: {}, body: {}", to, body);
    }

    private String generateOTP() {
        return new DecimalFormat("0000").format(new Random().nextInt(9999));
    }
}
