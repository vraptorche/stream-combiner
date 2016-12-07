package com.oracle.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "stream-generator")
public class GeneratorProperties {

    private Map<String, HostAddress> hosts;

    private int maxItems;

    public Map<String, HostAddress> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, HostAddress> hosts) {
        this.hosts = hosts;
    }


    public int getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }
}
