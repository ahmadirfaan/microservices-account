package com.irfaan.microservice.otp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author Ahmad Irfaan
 * @date 8/6/2021 10:15 AM
 * otp-service
 */


@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Value("${spring.redis.host:localhost}") //mengambil nilai redishost di application.yaml
    private String redisHost;
    @Value("${spring.redis.port:6380}")
    private Integer redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisconfig =
                new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(redisconfig);
    }

    @Bean
    public RedisTemplate<?,?> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<String>(String.class));
        return template;
    }
}
