package com.rcfs.caihx.common.specific;

public interface RcfsApplicationPublishEvent<T> {

    boolean publisherEvent(T sources);
}
