package com.rcfs.caihx.common.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = WatchProperties.PREFIX)
@ConditionalOnProperty(name = "spring.rcfs.config.enable",matchIfMissing = true)
@Configuration
public class WatchProperties {

    public static final String PREFIX = "spring.rcfs.config.watch";
    private boolean enable = false;
    private long time = 3000;
    private List<String> file;


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }
}
