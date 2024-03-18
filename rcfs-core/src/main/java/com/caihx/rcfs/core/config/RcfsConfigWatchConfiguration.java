package com.caihx.rcfs.core.config;

import com.caihx.rcfs.watch.config.RcfsWatchEnableConfig;
import com.rcfs.caihx.common.bean.RcfsConfigProperties;
import com.rcfs.caihx.common.specific.RcfsEnvironmentPostProcessor;
import com.rcfs.caihx.common.specific.RcfsWatchConfig;
import com.rcfs.caihx.common.specific.RcfsWatchMessageQueue;
import com.rcfs.caihx.common.specific.RcfsWatchTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "spring.rcfs.config.mode", havingValue = "watch",matchIfMissing=false)
public class RcfsConfigWatchConfiguration {
//    @Bean("RcfsWatchEnableConfig")
//    public RcfsWatchConfig rcfsWatchEnableConfig(RcfsEnvironmentPostProcessor environmentPostProcessor
//            , @Qualifier("WatchProducerQueue") RcfsWatchMessageQueue producerQueue
//            , RcfsConfigProperties rcfsConfigProperties
//    ) {
//        return new RcfsWatchEnableConfig(environmentPostProcessor, producerQueue, rcfsConfigProperties);
//    }
@Bean("RcfsWatchEnableConfig")
public RcfsWatchConfig rcfsWatchEnableConfig(@Qualifier("NioWatchTask") RcfsWatchTask nioWatchTask) {
    return new RcfsWatchEnableConfig(nioWatchTask);
}
}
