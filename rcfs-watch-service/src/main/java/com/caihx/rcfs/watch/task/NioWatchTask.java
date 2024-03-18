package com.caihx.rcfs.watch.task;

import com.caihx.rcfs.watch.queue.WatchProducerQueue;
import com.rcfs.caihx.common.bean.LogBean;
import com.rcfs.caihx.common.bean.MessageQueue;
import com.rcfs.caihx.common.bean.RcfsConfigProperties;
import com.rcfs.caihx.common.bean.WatchProperties;
import com.rcfs.caihx.common.specific.RcfsEnvironmentPostProcessor;
import com.rcfs.caihx.common.specific.RcfsWatchMessageQueue;
import com.rcfs.caihx.common.specific.RcfsWatchTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NioWatchTask implements RcfsWatchTask, Runnable {

    private Set<String> messageQueue = Collections.synchronizedSet(new LinkedHashSet<>());
    private RcfsWatchMessageQueue watchProducerQueue;
    private RcfsEnvironmentPostProcessor environmentPostProcessor;
    private RcfsConfigProperties rcfsConfigProperties;
    public static final Logger logger = LoggerFactory.getLogger(NioWatchTask.class);

    public NioWatchTask(RcfsWatchMessageQueue watchProducerQueue,RcfsEnvironmentPostProcessor environmentPostProcessor,RcfsConfigProperties rcfsConfigProperties){
        this.watchProducerQueue = watchProducerQueue;
        this.environmentPostProcessor = environmentPostProcessor;
        this.rcfsConfigProperties = rcfsConfigProperties;
    }

    @Override
    public void run() {
        logger.info(LogBean.WATCH_LOG+rcfsConfigProperties.getResources());
        watchTask();
    }

    @Override
    public void watchTask() {
        WatchService service = null;
        try {
            service = FileSystems.getDefault().newWatchService();
            Paths.get(rcfsConfigProperties.getResources()).register(service, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchProperties watch = rcfsConfigProperties.getWatch();
            List<String> file = watch.getFile();
            while (true) {
                WatchKey key = null;
                key = service.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    // todo
                    List<String> profilesList = environmentPostProcessor.getProfilesList();
                    if (profilesList.contains(event.context().toString())&&((file.contains(event.context().toString()))||file.isEmpty())){
                        logger.info("Rcfs Message - File triggered modification event:["+event.context().toString()+"]");
                        String mode = rcfsConfigProperties.getMode();
                        if ("watch".equals(mode)){
                            watchProducerQueue.producer(event.context().toString());
                        }
                        else if ("http".equals(mode)){
                            messageQueue.add(event.context().toString());
                        }else {
                            watchProducerQueue.producer(event.context().toString());
                        }
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execHttp() {
        for (String meg:messageQueue) {
            messageQueue.remove(meg);
            watchProducerQueue.producer(meg);
        }
    }
}
