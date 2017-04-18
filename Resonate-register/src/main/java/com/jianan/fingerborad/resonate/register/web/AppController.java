package com.jianan.fingerborad.resonate.register.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jianan.fingerborad.resonate.register.zk.LeaderSelectManager;

import javax.annotation.Resource;

/**
 * @author by jianan.liu on 17/2/11.
 */
@Configuration
@EnableAutoConfiguration
@RestController
public class AppController {
    @Resource
    private LeaderSelectManager leaderSelectManager;

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello/{myName}")
    public String index(@PathVariable String myName) {
        return "Hello " + myName + "!!!";
    }

    @RequestMapping("/leader/start")
    public Object startLeaderSelect() {
        leaderSelectManager.start();
        return "OK";
    }
}
