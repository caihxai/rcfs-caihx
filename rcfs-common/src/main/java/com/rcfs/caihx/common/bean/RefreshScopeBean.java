package com.rcfs.caihx.common.bean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RefreshScopeBean {

    private  ConcurrentMap<String, Object> beans = new ConcurrentHashMap<>();
    private  ConcurrentMap<String, Runnable> destructionCallbacks = new ConcurrentHashMap<>();

    public ConcurrentMap<String, Object> getBeans() {
        return beans;
    }

    public ConcurrentMap<String, Runnable> getDestructionCallbacks() {
        return destructionCallbacks;
    }

    public void setBeans(ConcurrentMap<String, Object> beans) {
        this.beans = beans;
    }

    public void setDestructionCallbacks(ConcurrentMap<String, Runnable> destructionCallbacks) {
        this.destructionCallbacks = destructionCallbacks;
    }
}
