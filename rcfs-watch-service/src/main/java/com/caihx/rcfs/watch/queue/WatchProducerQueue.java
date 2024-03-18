package com.caihx.rcfs.watch.queue;

import com.rcfs.caihx.common.bean.MessageQueue;
import com.rcfs.caihx.common.specific.RcfsWatchMessageQueue;
import org.springframework.stereotype.Service;

@Service("WatchProducerQueue")
public class WatchProducerQueue implements RcfsWatchMessageQueue {

    private MessageQueue<String> messageQueue;

    public WatchProducerQueue(MessageQueue<String> messageQueue){
        this.messageQueue = messageQueue;
    }

    @Override
    public void producer(String var1) {
        try {
            if (!messageQueue.contains(var1)){
                messageQueue.put(var1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void consumer() {

    }
}
