package com.nitok.consulredisexample;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import javax.sql.DataSource;

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
        ServiceInstance redisInstance = getConsulServiceByName("redis");
        LoggerFactory.getLogger(AppConfig.class).info("redis node host " + redisInstance.getHost());
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisInstance.getHost());
        configuration.setPort(redisInstance.getPort());
        return new JedisConnectionFactory(configuration);
    }

    ServiceInstance getConsulServiceByName(String name) {
        return discoveryClient.getInstances(name)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("cannot find " + name + " consul service"));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

    @Value("#{systemEnvironment['SPRING_DATASOURCE_USERNAME']}")
    private String datasourceUsername;

    @Value("#{systemEnvironment['SPRING_DATASOURCE_PASSWORD']}")
    private String datasourcePassword;

    @Value("#{systemEnvironment['SPRING_DATASOURCE_DBNAME']}")
    private String datasourceDbName;

    @Bean
    public DataSource getDataSource() {
        ServiceInstance postgresNode = getConsulServiceByName("postgres");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://" + postgresNode.getHost() + ":" + postgresNode.getPort() + "/" + datasourceDbName);
        dataSourceBuilder.username(datasourceUsername);
        dataSourceBuilder.password(datasourcePassword);
        return dataSourceBuilder.build();
    }

}
