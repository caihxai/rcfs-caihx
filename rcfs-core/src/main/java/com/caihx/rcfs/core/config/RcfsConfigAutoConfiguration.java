package com.caihx.rcfs.core.config;

import com.caihx.rcfs.core.config.env.RcfsEnvironmentPostProcessorImpl;
import com.caihx.rcfs.core.listener.EnvironmentUpdaterListener;
import com.caihx.rcfs.core.publish.EnvironmentUpdatePublishEvent;
import com.caihx.rcfs.watch.config.RcfsWatchEnableConfig;
import com.caihx.rcfs.watch.properties.PropertiesLoader;
import com.caihx.rcfs.watch.properties.PropertiesWatch;
import com.caihx.rcfs.watch.properties.YamlPropertiesLoader;
import com.caihx.rcfs.watch.properties.YamlPropertiesWatch;
import com.caihx.rcfs.watch.queue.WatchConsumerQueue;
import com.caihx.rcfs.watch.queue.WatchProducerQueue;
import com.caihx.rcfs.watch.task.NioWatchTask;
import com.rcfs.caihx.common.bean.MessageQueue;
import com.rcfs.caihx.common.bean.PropertiesBean;
import com.rcfs.caihx.common.bean.RcfsConfigProperties;
import com.rcfs.caihx.common.specific.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import java.util.Map;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "spring.rcfs.config.enable", matchIfMissing = true)
public class RcfsConfigAutoConfiguration {

    @Bean
    public RcfsConfigProperties rcfsConfigProperties() {
        return new RcfsConfigProperties();
    }

    @Bean
    public RcfsEnvironmentPostProcessor rcfsEnvironmentPostProcessor(ConfigurableEnvironment environment) {
        RcfsEnvironmentPostProcessorImpl rcfsEnvironmentPostProcessor = new RcfsEnvironmentPostProcessorImpl();
        rcfsEnvironmentPostProcessor.postProcessEnvironment(environment, null);
        return rcfsEnvironmentPostProcessor;
    }

    @Bean
    public MessageQueue<String> messageQueue() {
        return new MessageQueue<String>();
    }

    @Bean("WatchProducerQueue")
    public RcfsWatchMessageQueue watchProducerQueue(MessageQueue<String> messageQueue) {
        return new WatchProducerQueue(messageQueue);
    }

    @Bean("WatchConsumerQueue")
    public RcfsWatchMessageQueue watchConsumerQueue(ApplicationContext applicationContext, MessageQueue<String> messageQueue) {
        return new WatchConsumerQueue(applicationContext, messageQueue);
    }

    @Bean("YamlPropertiesLoader")
    public RcfsPropertiesLoader rcfsPropertiesLoader() {
        return new YamlPropertiesLoader();
    }

    @Bean("PropertiesLoader")
    public RcfsPropertiesLoader propertiesLoader() {
        return new PropertiesLoader();
    }

    @Bean
    public RcfsPropertiesWatch rcfsPropertiesWatch(RcfsEnvironmentPostProcessor environmentPostProcessor, ConfigurableApplicationContext applicationContext
            , @Qualifier("YamlPropertiesLoader") RcfsPropertiesLoader yamlPropertiesLoader
            , @Qualifier("PropertiesLoader") RcfsPropertiesLoader propertiesLoader
            , @Qualifier("EnvironmentUpdatePublishEvent") RcfsApplicationPublishEvent<Map<String, PropertySource<?>>> rcfsApplicationPublishEvent) {
        String extend = environmentPostProcessor.getExtend();
        RcfsPropertiesWatch rcfsPropertiesWatch = null;
        if (PropertiesBean.YAML.equals(extend)) {
            rcfsPropertiesWatch = new YamlPropertiesWatch(yamlPropertiesLoader, rcfsApplicationPublishEvent, environmentPostProcessor, applicationContext);
        }
        if (PropertiesBean.PROPERTIES.equals(extend)) {
            rcfsPropertiesWatch = new PropertiesWatch(propertiesLoader, rcfsApplicationPublishEvent, environmentPostProcessor, applicationContext);
        }
        return rcfsPropertiesWatch;
    }

    @Bean("EnvironmentUpdatePublishEvent")
    public RcfsApplicationPublishEvent<Map<String, PropertySource<?>>> rcfsApplicationPublishEvent(ApplicationEventPublisher publisher, ApplicationContext context) {
        return new EnvironmentUpdatePublishEvent(publisher, context);
    }

    @Bean
    public EnvironmentUpdaterListener environmentUpdaterListener() {
        return new EnvironmentUpdaterListener();
    }

    @Bean("NioWatchTask")
    public RcfsWatchTask nioWatchTask(@Qualifier("WatchProducerQueue") RcfsWatchMessageQueue watchProducerQueue,RcfsEnvironmentPostProcessor environmentPostProcessor,RcfsConfigProperties rcfsConfigProperties){
        return new NioWatchTask(watchProducerQueue,environmentPostProcessor,rcfsConfigProperties);
    }
}
