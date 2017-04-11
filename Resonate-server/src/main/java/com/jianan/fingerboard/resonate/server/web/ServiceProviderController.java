package com.jianan.fingerboard.resonate.server.web;

import com.jianan.fingerboard.resonate.server.bean.User;
import com.jianan.fingerboard.resonate.server.service.ProviderService;
import com.jianan.fingerboard.tuner.json.JsonUtil;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by jianan.liu on 17/2/19.
 */
@RestController
public class ServiceProviderController {

    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private ProviderService providerService;

    @RequestMapping("/add")
    public String add(@RequestParam String a, @RequestParam String b) {
        String host = discoveryClient.getLocalServiceInstance().getHost();
        return a + b + " " + host;
    }

    @RequestMapping("/query")
    public String query(@RequestParam String name) {
        User user = providerService.query(name);
        return JsonUtil.serialize(user);
    }
}
