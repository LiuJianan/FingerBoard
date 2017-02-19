package com.jianan.fingerborad.resonate.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author by jianan.liu on 17/2/19.
 */
@EnableEurekaServer
@SpringBootApplication
@EnableFeignClients
public class AppRegisterServer {
    public static void main(String[] args) {
        SpringApplication.run(AppRegisterServer.class, args);
    }
}
