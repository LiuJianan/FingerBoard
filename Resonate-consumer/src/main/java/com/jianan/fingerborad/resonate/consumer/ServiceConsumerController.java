package com.jianan.fingerborad.resonate.consumer;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by jianan.liu on 17/2/19.
 */
@RestController
@RequestMapping
public class ServiceConsumerController {

    @Resource
    private IAppClientService appClientService;

    @RequestMapping("/addClient")
    public String addClient() {
        return appClientService.add("a", "b");
    }
}
