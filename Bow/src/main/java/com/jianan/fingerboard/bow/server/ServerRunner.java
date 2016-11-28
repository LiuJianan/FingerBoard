package com.jianan.fingerboard.bow.server;

import com.jianan.fingerboard.tuner.http.AsyncHttpUtil;
import org.apache.http.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/**
 * Main server start
 *
 * @author by jianan.liu on 16/11/27.
 */
public class ServerRunner {
    private static final Logger logger = LoggerFactory.getLogger(ServerRunner.class);

    /**
     * start runner for manager
     *
     * @param args
     */
    public static void main(String[] args) {
        init();
        startServer();
    }

    /**
     * init configuation
     */
    private static void init() {

    }

    /**
     * server start main thread
     */
    private static void startServer() {
        Executors.newSingleThreadExecutor().execute(new MasterServer());
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                try {
                    logger.info("## stop master server");
                } catch (Throwable e) {
                    logger.error("stop master serer error", e);
                } finally {
                    logger.info("## master server is down.");
                }
            }

        });
    }

}
