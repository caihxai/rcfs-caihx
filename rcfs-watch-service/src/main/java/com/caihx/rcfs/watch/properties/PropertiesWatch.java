package com.caihx.rcfs.watch.properties;

import com.rcfs.caihx.common.bean.LogBean;
import com.rcfs.caihx.common.bean.RefreshBean;
import com.rcfs.caihx.common.specific.*;
import com.rcfs.caihx.common.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesWatch implements RcfsPropertiesWatch {

    public static final Logger log = LoggerFactory.getLogger(PropertiesWatch.class);
    private RcfsPropertiesLoader propertiesLoader;
    private RcfsApplicationPublishEvent<Map<String, PropertySource<?>>> rcfsApplicationPublishEvent;
    private RcfsEnvironmentPostProcessor rcfsEnvironmentPostProcessor;
    private RcfsScope rcfsScope;

    public PropertiesWatch(RcfsPropertiesLoader propertiesLoader
            , RcfsApplicationPublishEvent<Map<String, PropertySource<?>>> rcfsApplicationPublishEvent
            , RcfsEnvironmentPostProcessor rcfsEnvironmentPostProcessor
            , ConfigurableApplicationContext applicationContext){
        this.propertiesLoader = propertiesLoader;
        this.rcfsApplicationPublishEvent = rcfsApplicationPublishEvent;
        this.rcfsEnvironmentPostProcessor = rcfsEnvironmentPostProcessor;
        this.rcfsScope = (RcfsScope)applicationContext.getBeanFactory().getRegisteredScope(RefreshBean.SCOPE_NAME);
    }
    @Override
    public void watch(String var1,String var2) {
        Properties properties = propertiesLoader.loadProperties(var1 + "/" + var2);
        Map<String, PropertySource<?>> propertySourceMap = new HashMap<>();
        PropertySource<?> propertySource = new PropertiesPropertySource(var1, properties);
        propertySourceMap.put(var2, propertySource);
        synchronized (RcfsEnvironmentPostProcessor.class) {
            Map<String, PropertySource<?>> var3 = rcfsEnvironmentPostProcessor.getPropertySourceMap();
            Map<String, PropertySource<?>> updateProperty = PropertiesUtils.getUpdateProperty(var3, propertySourceMap);
            rcfsScope.refreshAll();
            rcfsApplicationPublishEvent.publisherEvent(updateProperty);
            rcfsEnvironmentPostProcessor.setPropertySourceMap(propertySourceMap);
            log.info(LogBean.ENVIRONMENT_SUCCESS_LOG);
        }
    }
}
