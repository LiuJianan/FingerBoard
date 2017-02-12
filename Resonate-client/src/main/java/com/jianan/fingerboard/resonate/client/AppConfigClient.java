package com.jianan.fingerboard.resonate.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by jianan.liu on 17/2/12.
 */
@SpringBootApplication
@RestController
@Configuration
@EnableAutoConfiguration
@RefreshScope
public class AppConfigClient {
    @Value("${configTestName:world}")
    String bar;

    @RequestMapping("/")
    String hello() {
        return "Hello " + bar + "!";
    }

    @Autowired
    void setEnviroment(Environment env) {
        System.out.println("my-config.appName from env: "
                + env.getProperty("configTestName"));
    }
    public static void main(String[] args) {
        Class[] classes = new Class[] {AppConfigClient.class};
        SpringApplication.run(classes, args);
    }
}
