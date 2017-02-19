package com.jianan.fingerborad.resonate.proxy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author by jianan.liu on 17/2/19.
 */
@EnableZuulProxy
@SpringBootApplication
public class ProxyApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ProxyApplication.class).web(true).run(args);
    }

    @Bean
    public ProxyFilter accessFilter() {
        return new ProxyFilter();
    }
}
