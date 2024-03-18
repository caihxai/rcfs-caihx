package com.rcfs.caihx.common.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.core.env.PropertySource;

import java.util.List;
import java.util.Map;

public class ContextEnvironmentRefreshedEvent extends ApplicationContextEvent {

    private Map<String, PropertySource<?>> propertySourceMap;

    public ContextEnvironmentRefreshedEvent(ApplicationContext source, Map<String, PropertySource<?>> propertySourceMap) {
        super(source);
        this.propertySourceMap = propertySourceMap;
    }

    public Map<String, PropertySource<?>> getPropertySourceMap() {
        return propertySourceMap;
    }

}
