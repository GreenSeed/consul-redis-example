package com.nitok.consulredisexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulRedisExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulRedisExampleApplication.class, args);
    }

}
