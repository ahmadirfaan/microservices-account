package com.irfaan.microservicesaccount.db.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

/**
 * @author Ahmad Irfaan
 * @date 7/19/2021 10:49 AM
 * microservices-account testing menggunakan PAT
 */

@Data
@RedisHash(value = "account", timeToLive = 3600)
public class TempAccount {
   @Id
   private String id;
   @Indexed //digunakan untuk bisa mencari pencarian di Redis
   private String email;
   private boolean valid = false;
}
