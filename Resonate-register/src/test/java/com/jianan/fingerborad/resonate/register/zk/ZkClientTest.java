package com.jianan.fingerborad.resonate.register.zk;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianan.fingerborad.resonate.register.AppRegisterServer;

/**
 * @author by jianan.liu on 17/5/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppRegisterServer.class)
public class ZkClientTest {
    String path = "/finderboard/resonate/test";

    @Autowired
    ZkClient zkClient;

    @Test
    public void test() throws Exception {
        try {
            if (zkClient.exit(path)) {
                System.out.println("path exit");
            }
            zkClient.create(path + "/t1");
        }catch (KeeperException.NodeExistsException e){
            System.out.println("node exit");
        }
    }

}