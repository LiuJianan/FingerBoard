package com.jianan.fingerborad.resonate.consumer;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by jianan.liu on 17/2/19.
 */
@RestController
@RequestMapping("/client/")
public class ServiceConsumerController {

    @Resource
    private IAppClientService appClientService;

    @RequestMapping("/add")
    public String addClient(String a, String b) {
        return appClientService.add(a, b);
    }

    @RequestMapping("/query")
    public String query(@RequestParam("name") String name) {
        return appClientService.query(name);
    }
}
