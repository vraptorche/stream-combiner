package com.oracle.homework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(StreamGeneratorConfig.class)
public class TestConfig {

    @Bean
    public DataGenerator dataGenerator() {
        return new DefaultDataGenerator();
    }
}
