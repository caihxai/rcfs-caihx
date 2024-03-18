package com.caihx.rcfs.watch.config;


import com.caihx.rcfs.watch.task.NioWatchTask;
import com.rcfs.caihx.common.bean.RcfsConfigProperties;
import com.rcfs.caihx.common.specific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RcfsWatchEnableConfig implements RcfsWatchConfig {
    public static final Logger logger = LoggerFactory.getLogger(RcfsWatchEnableConfig.class);

    private RcfsEnvironmentPostProcessor environmentPostProcessor;
    private RcfsWatchMessageQueue watchProducerQueue;
    private RcfsConfigProperties rcfsConfigProperties;
    private RcfsWatchTask nioWatchTask;

//    public RcfsWatchEnableConfig(RcfsEnvironmentPostProcessor environmentPostProcessor
//            , RcfsWatchMessageQueue watchProducerQueue
//            , RcfsConfigProperties rcfsConfigProperties){
//        this.environmentPostProcessor = environmentPostProcessor;
//        this.watchProducerQueue = watchProducerQueue;
//        this.rcfsConfigProperties = rcfsConfigProperties;
//    }
    public RcfsWatchEnableConfig(RcfsWatchTask nioWatchTask){
        this.nioWatchTask = nioWatchTask;
    }

    @Override
    public RcfsPropertiesWatch config() {

        return null;
    }
    @PostConstruct
    @Override
    public void run() {
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(1);
        cachedThreadPool.execute((Runnable) nioWatchTask);
    }
}
