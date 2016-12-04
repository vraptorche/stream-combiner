package com.oracle.homework;

import com.oracle.homework.config.GeneratorProperties;
import com.oracle.homework.config.StreamGeneratorConfig;
import io.vertx.core.Verticle;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(GeneratorProperties.class)
@Import(StreamGeneratorConfig.class)
public class StreamGeneratorAppConfig {

    private final GeneratorProperties properties;

    public StreamGeneratorAppConfig(GeneratorProperties properties) {
        this.properties = properties;
    }


    @Bean
    public Verticle dataGeneratorVerticle() {
        DataGeneratorVerticle generatorVerticle = new DataGeneratorVerticle();
        generatorVerticle.setAddresses(properties.getHosts());
        return generatorVerticle;
    }
}
