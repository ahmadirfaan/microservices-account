package com.irfaan.microservice.otp.db.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 10:12 AM
 * otp-service
 */

@Data
@RedisHash(value = "otp", timeToLive = 300)
public class TempOTP {
    @Id
    private String id;
    private String otp;
    @Indexed
    private String email;

}
