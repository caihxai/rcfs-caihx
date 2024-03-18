package com.caihx.rcfs.watch.config;

import com.caihx.rcfs.watch.client.ClientServer;
import com.caihx.rcfs.watch.task.NioWatchTask;
import com.rcfs.caihx.common.bean.ClientProperties;
import com.rcfs.caihx.common.bean.RcfsConfigProperties;
import com.rcfs.caihx.common.specific.RcfsEnvironmentPostProcessor;
import com.rcfs.caihx.common.specific.RcfsWatchConfig;
import com.rcfs.caihx.common.specific.RcfsWatchMessageQueue;
import com.rcfs.caihx.common.specific.RcfsWatchTask;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RcfsHttpEnableConfig implements RcfsWatchConfig {

    private RcfsEnvironmentPostProcessor environmentPostProcessor;
    private RcfsWatchMessageQueue watchProducerQueue;
    private RcfsConfigProperties rcfsConfigProperties;

    private RcfsWatchTask rcfsWatchTask;

    public RcfsHttpEnableConfig(RcfsEnvironmentPostProcessor environmentPostProcessor
            , RcfsWatchMessageQueue watchProducerQueue
            , RcfsConfigProperties rcfsConfigProperties, RcfsWatchTask rcfsWatchTask){
        this.environmentPostProcessor = environmentPostProcessor;
        this.watchProducerQueue = watchProducerQueue;
        this.rcfsConfigProperties = rcfsConfigProperties;
        this.rcfsWatchTask = rcfsWatchTask;
    }

    @Override
    public Object config() {
        return null;
    }

    @PostConstruct
    @Override
    public void run() {
        ClientProperties client = rcfsConfigProperties.getClient();
        Integer port = Integer.parseInt(client.getPort());
        String path = client.getPath();
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(1);
        cachedThreadPool.execute(new ClientServer(port,path,rcfsWatchTask));
    }
}
