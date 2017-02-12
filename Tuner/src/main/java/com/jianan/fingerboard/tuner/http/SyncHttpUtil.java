package com.jianan.fingerboard.tuner.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jianan.fingerboard.tuner.json.JsonUtil;

/**
 * sync http client
 *
 * @author by jianan.liu on 16/11/20.
 */
public class SyncHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(SyncHttpUtil.class);

    private static final int SOCKET_TIMEOUT = 30000;
    private static final int CONNECT_TIMEOUT = 30000;
    private static final String APPLICATION_JSON = "application/json";
    private static final String TEXT_JSON = "text/json";
    private static final String UTF_CODE = "utf-8";

    private static HttpClient client;
    private static RequestConfig config;

    static {
        init();
    }

    /**
     * initialize http client and start
     */
    private static void init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
        config = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
        client = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config).build();
    }

    public static String get(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error("http get error for url: {}", url, e);
        }
        return null;
    }

    public static String post (String url, Object o){
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            String json = JsonUtil.serialize(o);
            StringEntity stringEntry = new StringEntity(json, UTF_CODE);
            stringEntry.setContentType(TEXT_JSON);
            post.setEntity(stringEntry);
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }catch (Exception e){
            logger.error("http post error for url: {} data: {}", url, o, e);
        }
        return null;
    }

    public static void main(String[] args) {
        String s = get("http://et.airchina.com.cn/FareFamily/domestic/zh_CN/FFDomestic1.shtml");
        System.out.println(s);
    }

}
