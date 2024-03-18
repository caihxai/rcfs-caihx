package com.rcfs.caihx.common.specific;

import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.PropertySource;

import java.util.List;
import java.util.Map;

public interface RcfsEnvironmentPostProcessor extends EnvironmentPostProcessor {

    public String getRegex();

    public void setRegex(String regex);


    public String getDefaultProfile();

    public void setDefaultProfile(String defaultProfile);

    public String getExtend();

    public void setExtend(String extend);

    public String getResourcesPath();

    public void setResourcesPath(String resourcesPath);

    public List<String> getActiveProfiles();

    public void setActiveProfiles(List<String> activeProfiles);

    public List<String> getProfilesList();

    public void setProfilesList(List<String> profilesList);

    public Map<String, PropertySource<?>> getPropertySourceMap();

    public void setPropertySourceMap(Map<String, PropertySource<?>> propertySourceMap);
}
