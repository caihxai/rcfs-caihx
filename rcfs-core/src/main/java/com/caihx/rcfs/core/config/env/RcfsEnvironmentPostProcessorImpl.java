package com.caihx.rcfs.core.config.env;

import com.rcfs.caihx.common.specific.RcfsEnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RcfsEnvironmentPostProcessorImpl implements RcfsEnvironmentPostProcessor {
    String regex = "\\[(.*?)\\]";
    String defaultProfile = "application";
    String extend = ".properties";
    String resourcesPath = "";
    List<String> activeProfiles = new ArrayList<>();
    List<String> profilesList = new ArrayList<>();
    Map<String, PropertySource<?>> propertySourceMap = new HashMap<>();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            if (environment.getActiveProfiles().length > 0) {
                activeProfiles = Arrays.asList(environment.getActiveProfiles());
            }
            URL resourceUrl = RcfsEnvironmentPostProcessorImpl.class.getClassLoader().getResource("");
            if (resourceUrl != null) {
                String resourcePath = resourceUrl.getPath();
                resourcesPath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8.name());
            }
            Pattern pattern = Pattern.compile(regex);
            MutablePropertySources propertySources = environment.getPropertySources();
            for (PropertySource<?> propertySource : propertySources) {
                if (propertySource instanceof EnumerablePropertySource) {
                    EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) propertySource;
                    String name = enumerablePropertySource.getName();
                    Matcher matcher = pattern.matcher(name);
                    if (matcher.find()) {
                        String fileName = matcher.group(1);
                        profilesList.add(fileName);
                        String[] ext = fileName.split("\\.");
                        extend = "."+ext[ext.length - 1];
                        propertySourceMap.put(fileName, propertySource);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(String defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public List<String> getActiveProfiles() {
        return activeProfiles;
    }

    public void setActiveProfiles(List<String> activeProfiles) {
        this.activeProfiles = activeProfiles;
    }

    public List<String> getProfilesList() {
        return profilesList;
    }

    public void setProfilesList(List<String> profilesList) {
        this.profilesList = profilesList;
    }

    public Map<String, PropertySource<?>> getPropertySourceMap() {
        return propertySourceMap;
    }

    public void setPropertySourceMap(Map<String, PropertySource<?>> propertySourceMap) {
        this.propertySourceMap = propertySourceMap;
    }
}
