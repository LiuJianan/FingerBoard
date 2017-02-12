package com.jianan.fingerborad.resonate;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author by jianan.liu on 17/2/12.
 */
@EnableEurekaServer
@SpringBootApplication
public class ServerRegister {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerRegister.class).web(true).run(args);
    }
}
