package com.irfaan.microservice.otp.services;

import com.irfaan.microservice.otp.db.entity.TempOTP;
import com.irfaan.microservice.otp.db.repository.TempOTPRepository;
import com.irfaan.microservice.otp.dto.EmailDto;
import com.irfaan.microservice.otp.dto.RegisterCheckDto;
import com.irfaan.microservice.otp.dto.RegisterVerificationDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
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
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;

    @Autowired
    public OTPService(TempOTPRepository tempOTPRepository, RedisTemplate redisTemplate, ChannelTopic channelTopic) {
        this.tempOTPRepository = tempOTPRepository;
        this.redisTemplate = redisTemplate;
        this.channelTopic = channelTopic;
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
        sendEmail(email, "Kode Verifikasi Anda adalah " + randomOTP);
    }



    public void sendEmail(String to, String body) {
        log.debug("to: {}, body: {}", to, body);
        EmailDto emailDto = new EmailDto();
        emailDto.setTo(to);
        emailDto.setSubject("Kode verifikasi");
        emailDto.setBody(body);
        redisTemplate.convertAndSend(channelTopic.getTopic(), emailDto);
    }

    private String generateOTP() {
        return new DecimalFormat("0000").format(new Random().nextInt(9999));
    }

    public ResponseEntity<?> verificationOTP(RegisterVerificationDto registerVerificationDto) {
        // check by email
        TempOTP tempOTPByEmail = tempOTPRepository.getFirstByEmail(registerVerificationDto.getEmail());
        if (tempOTPByEmail == null) {
            return ResponseEntity.notFound().build();
        }
        // verification / validasi otp
        if (!tempOTPByEmail.getOtp().equals(registerVerificationDto.getOtp())) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }
}
