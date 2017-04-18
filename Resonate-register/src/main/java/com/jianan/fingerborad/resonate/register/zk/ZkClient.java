package com.jianan.fingerborad.resonate.register.zk;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * ZK client instance
 *
 * @author by jianan.liu on 17/4/12.
 */
@Component
@Configuration
public class ZkClient {
    private static final Logger logger = LoggerFactory.getLogger(ZkClient.class);

    private CuratorFramework zkClient;

    @Value("${zookeeper.address}")
    private String address;

    @PostConstruct
    void init() {
        logger.info("zookeeper.address {}", address);
        zkClient = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(500, 3));
    }

    public CuratorFramework getClient() {
        return zkClient;
    }

}
