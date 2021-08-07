package com.irfaan.microservicesaccount.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Ahmad Irfaan
 * @date 7/19/2021 10:26 AM
 * microservices-account
 */

@Configuration
@EnableJpaAuditing
@EnableFeignClients("com.irfaan.microservicesaccount.feignclient")
public class AppConfig {
}
