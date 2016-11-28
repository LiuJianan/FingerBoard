package com.jianan.fingerboard.tuner.http;

import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jianan.fingerboard.tuner.json.JsonUtil;

/**
 * Async Http client
 *
 * @author by jianan.liu on 16/11/20.
 */
public class AsyncHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(AsyncHttpUtil.class);

    private static final int SOCKET_TIMEOUT = 30000;
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int MAX_TOTAL = 200;
    private static final int MAX_HOST = 20;
    private static final String APPLICATION_JSON = "application/json";
    private static final String TEXT_JSON = "text/json";
    private static final String UTF_CODE = "utf-8";

    private static RequestConfig config;
    private static CloseableHttpAsyncClient httpclient;
    private static PoolingNHttpClientConnectionManager pool;
    static {
        init();
    }

    /**
     * initialize http client and start
     */
    private static void init() {
        try {
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
            pool = new PoolingNHttpClientConnectionManager(ioReactor);
            pool.setMaxTotal(MAX_TOTAL);
            pool.setDefaultMaxPerRoute(MAX_HOST);
            config = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
            httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(config).setConnectionManager(pool).build();
            httpclient.start();
            logger.info("start async client OK");
        } catch (Exception e) {
            logger.error("error init", e);
        }
    }

    public static void close() {
        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("close error", e);
            }
        }
    }

    public static String get(final String url) {
        HttpGet httpGet = new HttpGet(url);
        Future<HttpResponse> httpResponseFuture = httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            public void completed(HttpResponse httpResponse) {
                logger.info("ok url: {}", url);
            }

            public void failed(Exception e) {
                logger.error("fail url: {}", url);
            }

            public void cancelled() {
                logger.warn("cancel url: {}", url);
            }
        });
        return parse(httpResponseFuture);
    }

    public static String post(final String url, Object object) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            String json = JsonUtil.serialize(object);
            StringEntity entity = new StringEntity(json, UTF_CODE);
            entity.setContentType(TEXT_JSON);
            httpPost.setEntity(entity);
            Future<HttpResponse> httpResponseFuture = httpclient.execute(httpPost, new FutureCallback<HttpResponse>() {
                public void completed(HttpResponse httpResponse) {
                    logger.info("ok url: {}", url);
                }

                public void failed(Exception e) {
                    logger.error("fail url: {}", url);
                }

                public void cancelled() {
                    logger.warn("cancel url: {}", url);
                }
            });
            return parse(httpResponseFuture);
        } catch (Exception e) {
            logger.error("post error for url: {} object: {}", url, object);
            return null;
        }
    }

    private static String parse(Future<HttpResponse> httpResponseFuture) {
        try {
            HttpResponse httpResponse = httpResponseFuture.get();
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error("parse http response error", e);
        }
        return null;
    }
}
