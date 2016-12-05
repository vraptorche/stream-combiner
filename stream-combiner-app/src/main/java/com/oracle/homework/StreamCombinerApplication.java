package com.oracle.homework;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class StreamCombinerApplication {

    @Autowired
    private Verticle streamClientVerticle;

    public static void main(String[] args) {
        SpringApplication.run(StreamCombinerApplication.class, args);
    }

    @PostConstruct
    void deployVeticle() {
        Vertx.vertx().deployVerticle(streamClientVerticle);
    }
}
