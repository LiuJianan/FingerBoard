package com.jianan.fingerboard.bow.server;

import com.jianan.fingerboard.bow.web.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * master server thread
 * 
 * @author by jianan.liu on 16/11/27.
 */
public class MasterServer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerRunner.class);

    public MasterServer() {

    }

    public void run() {
        logger.info("start master");
        HttpServer httpServer = new HttpServer("127.0.0.1", 55522);
        httpServer.start();
    }
}
