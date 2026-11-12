package com.mudassir.mobile.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * Loads config/{env}.properties. "mvn test -Dsuite=lambdatest" picks lambdatest.properties
 * automatically; every other suite defaults to local. Any key can be overridden with -Dkey=value.
 */
public final class ConfigManager {

    private static final String ENV = System.getProperty("env",
            "lambdatest".equals(System.getProperty("suite")) ? "lambdatest" : "local");
    private static final Properties PROPS = load();

    private ConfigManager() {
    }

    private static Properties load() {
        String path = "config/" + ENV + ".properties";
        Properties props = new Properties();
        try (InputStream in = ConfigManager.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Config file not found on classpath: " + path);
            }
            props.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return props;
    }

    public static String get(String key) {
        String value = System.getProperty(key, PROPS.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config key '" + key + "' for env '" + ENV + "'");
        }
        return value.trim();
    }

    /** Secrets are only ever read from environment variables, never from files. */
    public static String secret(String envVar) {
        String value = System.getenv(envVar);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Environment variable " + envVar + " is not set");
        }
        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean isCloud() {
        return "lambdatest".equalsIgnoreCase(get("run.target"));
    }

    public static int explicitWaitSeconds() {
        return getInt("explicit.wait.seconds");
    }

    public static int retryCount() {
        return getInt("retry.count");
    }
}
