package com.oracle.homework;

import io.vertx.core.AbstractVerticle;
import org.springframework.stereotype.Component;

@Component
public class DataGeneratorVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(socket -> {
        }).listen(handler -> {
        });
    }
}
