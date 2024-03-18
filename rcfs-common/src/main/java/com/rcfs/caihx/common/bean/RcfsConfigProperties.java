package com.rcfs.caihx.common.bean;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = RcfsConfigProperties.PREFIX)
public class RcfsConfigProperties {
    private static final Logger log = LoggerFactory.getLogger(RcfsConfigProperties.class);
    public static final String PREFIX = "spring.rcfs.config";
    public static final String RCFS_NAME = "Refresh configuration file system";

    private boolean enable = true;
    private String mode = "http"; // mix/http/watch
    String resources = System.getProperty("user.dir")+"/src/main/resources";
    private WatchProperties watch = new WatchProperties();
    private ClientProperties client = new ClientProperties();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public WatchProperties getWatch() {
        return watch;
    }

    public void setWatch(WatchProperties watch) {
        this.watch = watch;
    }

    public ClientProperties getClient() {
        return client;
    }

    public void setClient(ClientProperties client) {
        this.client = client;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}
