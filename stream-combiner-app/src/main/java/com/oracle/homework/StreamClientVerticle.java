package com.oracle.homework;


import com.oracle.homework.config.HostAddress;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.util.Map;
import java.util.Optional;

public class StreamClientVerticle extends AbstractVerticle {

    private Map<String, HostAddress> addresses;


    @Override
    public void start() throws Exception {
        Optional.ofNullable(addresses)
                .map(m -> m)
                .orElseThrow(RuntimeException::new)
                .forEach((k, v) -> createClient(v));
    }

    private void createClient(HostAddress address) {
        NetClientOptions options = new NetClientOptions()
                .setLogActivity(true);
        PublishSubject<Object> subject = PublishSubject.create();
        NetClient client = vertx.createNetClient(options);
        client.connect(address.getPort(), address.getHost(), res -> {
            if (res.succeeded()) {
                NetSocket socket = res.result();
                socket.handler(buffer -> {
                    String payload = buffer.toString("UTF-8");
                    subject.onNext(payload);
                });
                socket.closeHandler(event -> {
                    subject.onComplete();
                    socket.close();
                });
                socket.exceptionHandler(subject::onError);
                socket.write("\n");
            }
            subject.subscribe(System.out::println);
        });
    }

    public void setAddresses(Map<String, HostAddress> addresses) {
        this.addresses = addresses;
    }
}
