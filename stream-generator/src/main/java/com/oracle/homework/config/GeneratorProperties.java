package com.oracle.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "stream-generator")
public class GeneratorProperties {

    Map<String, HostAddress> hosts;

    public Map<String, HostAddress> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, HostAddress> hosts) {
        this.hosts = hosts;
    }
}
