package com.caihx.rcfs.core.publish;


import com.caihx.rcfs.watch.config.RcfsWatchEnableConfig;
import com.rcfs.caihx.common.bean.LogBean;
import com.rcfs.caihx.common.event.ContextEnvironmentRefreshedEvent;
import com.rcfs.caihx.common.specific.RcfsApplicationPublishEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.PropertySource;

import java.util.Map;

public class EnvironmentUpdatePublishEvent implements RcfsApplicationPublishEvent<Map<String, PropertySource<?>>> {

    public static final Logger log = LoggerFactory.getLogger(EnvironmentUpdatePublishEvent.class);

    private final ApplicationEventPublisher publisher;
    private ApplicationContext context;
    public EnvironmentUpdatePublishEvent(ApplicationEventPublisher publisher,ApplicationContext context){
        this.publisher = publisher;
        this.context = context;
    }
    @Override
    public boolean publisherEvent(Map<String,PropertySource<?>> sources) {
        log.info(LogBean.PUBLISH_ENVIRONMENT_EVENT_LOG);
        publisher.publishEvent(new ContextEnvironmentRefreshedEvent(context,sources));
        return true;
    }
}
