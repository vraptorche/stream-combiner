package com.oracle.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "stream-generator")
public class GeneratorProperties {

    Map<String, GeneratorAddress> hosts;

    public Map<String, GeneratorAddress> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, GeneratorAddress> hosts) {
        this.hosts = hosts;
    }
}
