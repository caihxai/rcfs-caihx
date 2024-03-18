package com.caihx.rcfs.watch.properties;

import com.rcfs.caihx.common.specific.RcfsPropertiesLoader;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Properties;

@Configuration
public class YamlPropertiesLoader implements RcfsPropertiesLoader {
    @Override
    public Properties loadProperties(String fileName) {
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setResources(new FileSystemResource(fileName));
        return yamlFactory.getObject();
    }
}
