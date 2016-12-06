package com.oracle.homework;


import com.oracle.homework.config.HostAddress;
import com.oracle.homework.core.service.DataService;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class StreamClientVerticle extends AbstractVerticle {

    @Autowired
    private DataService dataService;

    private Map<String, HostAddress> addresses;

    private Observable<Object> combinerObservable = Observable.empty();

    @Override
    public void start() throws Exception {
        List<Subject<String>> subjects = ofNullable(addresses)
                .map(m -> m.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .map(this::createClient)
                        .collect(toList()))
                .orElseThrow(RuntimeException::new);
        PublishSubject.mergeDelayError(subjects)
                .map(c -> c.split("\n"))
                .map(Observable::fromArray)
                .flatMap(s -> s)
//                .doOnNext(s -> System.out.println("***" + s))
//                .map(dataService::fromXml)
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    private Subject<String> createClient(HostAddress address) {
        NetClientOptions options = new NetClientOptions()
                .setLogActivity(true);
        PublishSubject<String> subject = PublishSubject.create();
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
        });
        return subject;
    }

    public void setAddresses(Map<String, HostAddress> addresses) {
        this.addresses = addresses;
    }
}
