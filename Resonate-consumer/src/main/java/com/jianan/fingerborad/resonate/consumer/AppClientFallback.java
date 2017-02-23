package com.jianan.fingerborad.resonate.consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author by jianan.liu on 17/2/19.
 */
@Component
public class AppClientFallback implements IAppClientService {
    @Override
    public String add(@RequestParam(value = "a") String a, @RequestParam(value = "b") String b) {
        return "ERROR!";
    }
}
