package com.caihx.rcfs.watch.queue;

import com.caihx.rcfs.watch.config.RcfsWatchEnableConfig;
import com.rcfs.caihx.common.bean.MessageQueue;
import com.rcfs.caihx.common.bean.RcfsConfigProperties;
import com.rcfs.caihx.common.specific.RcfsPropertiesWatch;
import com.rcfs.caihx.common.specific.RcfsWatchMessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("WatchConsumerQueue")
public class WatchConsumerQueue implements RcfsWatchMessageQueue {

    public static final Logger logger = LoggerFactory.getLogger(WatchConsumerQueue.class);

    private MessageQueue<String> messageQueue;
    private ExecutorService executorService;
    private ApplicationContext applicationContext;



    public WatchConsumerQueue(ApplicationContext applicationContext,MessageQueue<String> messageQueue){
        this.messageQueue = messageQueue;
        this.applicationContext = applicationContext;
        this.executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::consumer);
    }

    @Override
    public void producer(String var1) {
    }

    @Override
    public void consumer() {
        while (true) {
            try {
                String filename = messageQueue.take();
                // 处理逻辑
                RcfsPropertiesWatch rcfsPropertiesWatch = applicationContext.getBean(RcfsPropertiesWatch.class);
                RcfsConfigProperties rcfsConfigProperties = applicationContext.getBean(RcfsConfigProperties.class);
                rcfsPropertiesWatch.watch(rcfsConfigProperties.getResources(),filename);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
