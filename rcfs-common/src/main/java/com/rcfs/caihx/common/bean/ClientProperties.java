package com.rcfs.caihx.common.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = ClientProperties.PREFIX)
@ConditionalOnProperty(name = "spring.rcfs.config.enable",matchIfMissing = true)
@Configuration
public class ClientProperties {

    public static final String PREFIX = "spring.rcfs.config.client";
    private String port = "7901";
    private String path = "/update";

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
