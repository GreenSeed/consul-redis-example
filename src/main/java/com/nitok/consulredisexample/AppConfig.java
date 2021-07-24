package com.nitok.consulredisexample;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.List;

@Configuration
public class AppConfig {

    /*
    из библиотеки для consul
    нам нужен чтобы спрашивать у consul где находится тот или иной сервис
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /*
    в JedisConnectionFactory надо установить параметры подключения к redis
    их мы возьмем из consul через discoveryClient
     */
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        ServiceInstance redisInstance = redisNode();
        LoggerFactory.getLogger(AppConfig.class).error("redis node host "+redisInstance.getHost());
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisInstance.getHost());
        configuration.setPort(redisInstance.getPort());
        return new JedisConnectionFactory(configuration);
    }

    ServiceInstance redisNode() {
        return discoveryClient.getInstances("redis")
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("cannot find redis node"));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

}
