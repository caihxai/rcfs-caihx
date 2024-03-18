package com.caihx.rcfs.core.listener;

import com.caihx.rcfs.core.publish.EnvironmentUpdatePublishEvent;
import com.rcfs.caihx.common.bean.LogBean;
import com.rcfs.caihx.common.event.ContextEnvironmentRefreshedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentUpdaterListener implements ApplicationListener<ContextEnvironmentRefreshedEvent> {

    public static final Logger log = LoggerFactory.getLogger(EnvironmentUpdaterListener.class);
    public static final String resourceName = "Config resource 'class path resource [%s]' via location 'optional:classpath:/' rcfs update %s";
    @Override
    public void onApplicationEvent(ContextEnvironmentRefreshedEvent event) {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) event.getApplicationContext().getEnvironment();
        Map<String,PropertySource<?>> propertySourceMap = event.getPropertySourceMap();
        Map<String, Object> properties;
        for (Map.Entry<String, PropertySource<?>> sourceEntry:propertySourceMap.entrySet()){
            properties = new HashMap<>();
            PropertySource<?> source = sourceEntry.getValue();
            if (source instanceof EnumerablePropertySource){
                EnumerablePropertySource<?> enumerableSource = (EnumerablePropertySource<?>) source;
                for (String name : enumerableSource.getPropertyNames()) {
                    properties.put(name,source.getProperty(name));
                }
                environment.getPropertySources().addFirst(new MapPropertySource(String.format(resourceName,sourceEntry.getKey(),String.valueOf(System.currentTimeMillis())), properties));
                log.info(String.format(LogBean.ENVIRONMENT_UPDATE_LOG,sourceEntry.getKey()));
            }
        }

    }
}
