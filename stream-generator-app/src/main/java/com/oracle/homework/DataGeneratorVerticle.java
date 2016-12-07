package com.oracle.homework;

import com.oracle.homework.config.GeneratorProperties;
import com.oracle.homework.config.HostAddress;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


public class DataGeneratorVerticle extends AbstractVerticle {

    private Map<String, HostAddress> addresses;
    @Autowired
    private DataGenerator dataGenerator;
    @Autowired
    GeneratorProperties properties;
    private final Random random = new Random();

    @Override
    public void start() throws Exception {
        Optional.ofNullable(this.addresses)
                .map(m -> m)
                .orElseThrow(RuntimeException::new)
                .forEach((k, v) -> {
                    createserver(v);
                });
    }

    private void createserver(HostAddress v) {
        NetServerOptions options = new NetServerOptions()
                .setLogActivity(true)
                .setPort(v.getPort())
                .setHost(v.getHost());
        NetServer server = vertx.createNetServer(options).connectHandler(socket -> {
        });
        server.connectHandler(netSocket -> netSocket.handler(buffer -> {
            vertx.setPeriodic(750, event -> {
                String dataChunk = dataGenerator.generateDataChunk(new Date().getTime(), random.nextInt(properties.getMaxItems()));
                buffer.appendString(dataChunk);
                buffer.appendString("\n");
                netSocket.write(buffer);
                if (netSocket.writeQueueFull()) {
                    netSocket.pause();
                    netSocket.drainHandler(done -> netSocket.resume());
                }
            });
        }));
        server.listen();
    }

    public void setAddresses(Map<String, HostAddress> addresses) {
        this.addresses = addresses;
    }

}
