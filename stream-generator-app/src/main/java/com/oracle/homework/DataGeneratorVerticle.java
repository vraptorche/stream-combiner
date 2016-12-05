package com.oracle.homework;

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


public class DataGeneratorVerticle extends AbstractVerticle {

    private Map<String, HostAddress> addresses;
    @Autowired
    private DataGenerator dataGenerator;

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
        server.connectHandler(new Handler<NetSocket>() {
            @Override
            public void handle(NetSocket netSocket) {
                netSocket.handler(buffer -> {
                    vertx.setPeriodic(750, event -> {
                        String dataChunk = dataGenerator.generateDataChunk(new Date().getTime(), 100);
                        Buffer outBuffer = Buffer.buffer();
                        outBuffer.appendString(dataChunk);
                        netSocket.write(outBuffer);
                    });
                });
            }
        });
        server.listen();
    }

    public void setAddresses(Map<String, HostAddress> addresses) {
        this.addresses = addresses;
    }

}
