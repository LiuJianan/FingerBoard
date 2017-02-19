package com.jianan.fingerboard.resonate.server.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author by jianan.liu on 17/2/19.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class AppServiceClient {

    @Bean
    RestTemplate restTemplate(){
        return  new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppServiceClient.class, args);
    }
}
