package com.jianan.fingerborad.resonate.register.zk;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

/**
 * @author by jianan.liu on 17/4/12.
 */
@Component
public class LeaderSelectManager extends LeaderSelectorListenerAdapter implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(LeaderSelectManager.class);
    private static final String LEADER_ELECT_PATH = "/fingerborad/resonate/register/leader";
    private LeaderSelector leaderSelector;
    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private ZkClient zkClient;
    private AtomicBoolean releaseFlag = new AtomicBoolean(false);

    @PostConstruct
    private void init() {
        leaderSelector = new LeaderSelector(zkClient.getClient(), LEADER_ELECT_PATH, this);
        leaderSelector.autoRequeue();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        String serviceId = discoveryClient.getLocalServiceInstance().getServiceId();
        logger.info("take leadership {}", serviceId);
        while (!releaseFlag.get()){

        }
        logger.info("release leadership {}", serviceId);
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    public void start() {
        logger.info("start leader selector....");
        leaderSelector.start();
    }

    public void releaseLeaderShip(){
        releaseFlag.set(true);
    }
}
