package com.irfaan.microservice.emailservice.config;

import com.irfaan.microservice.emailservice.services.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author Ahmad Irfaan
 * @date 7/19/2021 10:28 AM
 * microservices-account
 */

@Configuration
@EnableRedisRepositories
public class RedisListenerConfig {
    @Value("${spring.redis.host:localhost}") //mengambil nilai redishost di application.yaml
    private String redisHost;
    @Value("${spring.redis.port:6380}")
    private Integer redisPort;
    @Value("${spring.redis.channel-name:emailSender}")
    private String redisChannelName;

    private final RedisMessageSubscriber redisMessageSubscriber;

    @Autowired
    public RedisListenerConfig(RedisMessageSubscriber redisMessageSubscriber) {
        this.redisMessageSubscriber = redisMessageSubscriber;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisconfig =
                new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(redisconfig);
    }

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic(redisChannelName);
    }

    @Bean
    public MessageListener messageListenerAdapter() {
        return new MessageListenerAdapter(redisMessageSubscriber);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), channelTopic());
        return container;
    }
}
