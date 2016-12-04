package com.oracle.homework;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class StreamGeneratorApplication {

    @Autowired
    private Verticle generatorVerticle;

    public static void main(String[] args) {
        SpringApplication.run(StreamGeneratorApplication.class, args);
    }

    @PostConstruct
    void deployVerticle() {
        Vertx.vertx().deployVerticle(generatorVerticle);
    }
}
