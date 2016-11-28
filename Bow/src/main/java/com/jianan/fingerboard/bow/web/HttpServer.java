package com.jianan.fingerboard.bow.web;

import com.jianan.fingerboard.tuner.http.AsyncHttpUtil;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.BoundedThreadPool;
import org.mortbay.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by jianan.liu on 16/11/27.
 */
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(AsyncHttpUtil.class);

    protected final Server webServer;

    public HttpServer(String ip, int port) {
        webServer = new Server();
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setDaemon(true);
        threadPool.setMaxThreads(4);
        threadPool.setMinThreads(4);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost(ip);
        connector.setPort(port);
        webServer.setThreadPool(threadPool);
        webServer.addConnector(connector);
        AbstractHandler servletHandler = new DefaultServletHandler();
        webServer.setHandler(servletHandler);
    }

    public void start() {
        try {
            webServer.start();
            webServer.join();
        } catch (Exception e) {
            logger.error("http server start error", e);
        }

    }
}
