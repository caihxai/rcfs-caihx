package com.rcfs.caihx.common.utils;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PropertiesUtils {

    public static Map<String, PropertySource<?>> getUpdateProperty(Map<String, PropertySource<?>> oldVar,Map<String, PropertySource<?>> newVar) {
        Map<String, PropertySource<?>> upVar = null;
        if (!newVar.isEmpty()) {
            upVar = new HashMap<>();
            for (Map.Entry<String, PropertySource<?>> entries : newVar.entrySet()) {
                String var1 = entries.getKey();
                if (oldVar.containsKey(var1)) {
                    PropertySource<?> propertySource = oldVar.get(var1);
                    PropertySource<?> source = entries.getValue();
                    EnumerablePropertySource<?> var3 = (EnumerablePropertySource<?>)propertySource;
                    EnumerablePropertySource<?> var4 = (EnumerablePropertySource<?>)source;
                    PropertySource<?> compare = compare(var1,var4, var3);
                    upVar.put(var1,compare);
                } else {
                    upVar.put(var1, entries.getValue());
                }
            }
            return upVar;
        } else {
            return newVar;
        }
    }

    public static PropertySource<?> compare(String var1,EnumerablePropertySource<?> newVar, EnumerablePropertySource<?> oldVar) {
        Map<String, Object> differences = new HashMap<>();
        for (String name : newVar.getPropertyNames()) {
            Object value1 = newVar.getProperty(name);
            Object value2 = oldVar.getProperty(name);
            if (value2 == null || !value1.equals(value2)) {
                differences.put(name, value1);
            }
        }

        return new MapPropertySource("differences", differences);
    }
}
