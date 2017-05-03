package com.jianan.fingerborad.resonate.register.zk;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.shaded.com.google.common.base.Preconditions;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
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
        zkClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                zkClient.close();
            }
        });
        CountDownLatch countDownLatch = new CountDownLatch(1);
        waitConnected(countDownLatch);
    }

    /**
     * check path if exit
     * 
     * @param path
     * @return
     */
    public boolean exit(String path) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(path), "path empty");
            return zkClient.checkExists().forPath(path) != null;
        } catch (Exception e) {
            logger.error("exit zk path error. path: {}", path, e);
            return false;
        }
    }

    /**
     * create path
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean create(String path) throws Exception {
        return create(path, true);
    }

    /**
     * create path
     * 
     * @param path
     * @param persistent
     * @return
     * @throws Exception
     */
    public boolean create(String path, boolean persistent) throws Exception {
        CreateMode mode = persistent ? CreateMode.PERSISTENT : CreateMode.EPHEMERAL;
        return create(path, mode);
    }

    /**
     * create path with mode
     * 
     * @param path
     * @param mode
     * @return
     * @throws Exception
     */
    public boolean create(String path, CreateMode mode) throws Exception {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(path), "path empty");
            zkClient.create().creatingParentsIfNeeded().withMode(mode).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(path);
            return true;
        } catch (KeeperException.NodeExistsException ne) {
            logger.warn("path has exit, path: {}", path);
            return true;
        } catch (Exception e) {
            logger.error("exit zk path error. path: {}", path, e);
            throw e;
        }
    }

    /**
     * get data from path
     * 
     * @param path
     * @return
     */
    public String get(String path) {
        try {
            byte[] bytes = zkClient.getData().forPath(path);
            String str = new String(bytes, Charset.forName("utf-8"));
            return str;
        } catch (Exception e) {
            logger.error("get from path error, path: {}", path, e);
            return null;
        }
    }

    public CuratorFramework getClient() {
        return zkClient;
    }

    private void waitConnected(CountDownLatch countDownLatch) {
        zkClient.getConnectionStateListenable().addListener(new ConnectedListener(countDownLatch));
    }

    /**
     * listener for connected
     */
    public static class ConnectedListener implements ConnectionStateListener {
        private CountDownLatch latch;

        public ConnectedListener(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            if (newState == ConnectionState.CONNECTED) {
                latch.countDown();
                logger.info("zk client connected OK");
            } else if (newState == ConnectionState.SUSPENDED) {
                logger.info("zk SUSPENDED");
            } else if (newState == ConnectionState.LOST) {
                logger.info("zk LOST");
            } else if (newState == ConnectionState.RECONNECTED) {
                logger.info("zk RECONNECTED");
            }
        }
    }
}
