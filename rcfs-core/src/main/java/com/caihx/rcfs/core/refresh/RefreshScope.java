package com.caihx.rcfs.core.refresh;

import com.rcfs.caihx.common.specific.RcfsScope;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class RefreshScope implements RcfsScope {

    private ConcurrentMap<String, Object> beans = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Runnable> destructionCallbacks = new ConcurrentHashMap<>();

    public RefreshScope() {
    }


    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        // If the bean is already created, return it
        if (beans.containsKey(name)) {
            return beans.get(name);
        }

        // Otherwise, create the bean and save it in the map
        Object bean = objectFactory.getObject();
        beans.put(name, bean);
        return bean;
    }

    @Override
    public Object remove(String name) {
        destructionCallbacks.remove(name);
        return beans.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        destructionCallbacks.put(name, callback);
    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void refreshAll() {
        // Call all destruction callbacks
        for (Runnable destructionCallback : destructionCallbacks.values()) {
            destructionCallback.run();
        }
        // Clear the maps
        beans.clear();
        destructionCallbacks.clear();
    }
}
