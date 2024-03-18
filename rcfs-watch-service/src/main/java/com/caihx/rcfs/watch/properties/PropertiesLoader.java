package com.caihx.rcfs.watch.properties;

import com.rcfs.caihx.common.specific.RcfsPropertiesLoader;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class PropertiesLoader implements RcfsPropertiesLoader {
    @Override
    public Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                return properties;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}
