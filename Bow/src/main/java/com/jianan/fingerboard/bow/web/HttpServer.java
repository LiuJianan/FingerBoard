package com.jianan.fingerboard.bow.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import com.alibaba.druid.support.http.StatViewServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.http.WebStatFilter;
import com.jianan.fingerboard.tuner.http.AsyncHttpUtil;

/**
 * @author by jianan.liu on 16/11/27.
 */
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    protected final Server webServer;

    public HttpServer(String ip, int port) {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setDaemon(true);
        threadPool.setMaxThreads(4);
        threadPool.setMinThreads(4);
        webServer = new Server(port);

        HttpConfiguration config = new HttpConfiguration();
        ServerConnector connector = new ServerConnector(webServer,new HttpConnectionFactory(config));
        connector.setReuseAddress(true);
        connector.setIdleTimeout(30000);
        connector.setHost(ip);
        webServer.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/data");
        context.addFilter(WebStatFilter.class, "/*", EnumSet.of(DispatcherType.INCLUDE,DispatcherType.REQUEST));
        context.addServlet(new ServletHolder(new BowDataServlet()), "/query");
        context.addServlet(new ServletHolder(new StatViewServlet()), "/druid/*");
        webServer.setHandler(context);
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
