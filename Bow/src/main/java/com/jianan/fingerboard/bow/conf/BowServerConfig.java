package com.jianan.fingerboard.bow.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author by jianan.liu on 16/11/27.
 */
public class BowServerConfig {
    private static final Logger logger = LoggerFactory.getLogger(BowServerConfig.class);

    private static Properties properties;

    static {
        try {
            properties.load(BowServerConfig.class.getResourceAsStream("/bow.properties"));
            logger.info("bow server config: {}", properties);
        } catch (Exception e) {
            logger.error("load bow.properties error", e);
        }
    }

    public static String get(String name, String def) {
        return properties.getProperty(name, def);
    }

    public static int get(String name, int def) {
        String v = properties.getProperty(name);
        if (v == null) {
            v = String.valueOf(def);
        }
        return Integer.parseInt(v);
    }

    public static boolean get(String name, boolean def) {
        String v = properties.getProperty(name);
        if (v == null) {
            v = String.valueOf(def);
        }
        return Boolean.parseBoolean(v);
    }
}
