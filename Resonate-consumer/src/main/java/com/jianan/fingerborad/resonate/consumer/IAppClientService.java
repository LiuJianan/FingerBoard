package com.jianan.fingerborad.resonate.consumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author by jianan.liu on 17/2/19.
 */
@FeignClient(value = "RESONATE-SERVER", fallback = AppClientFallback.class)
public interface IAppClientService {
    @RequestMapping(method = RequestMethod.GET, value = "/add")
    String add(@RequestParam(value = "a") String a, @RequestParam(value = "b") String b);
}
