package com.oracle.homework;


import com.oracle.homework.config.HostAddress;
import com.oracle.homework.core.domain.Data;
import com.oracle.homework.core.service.DataService;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class StreamClientVerticle extends AbstractVerticle {

    @Autowired
    private DataService dataService;

    private Map<String, HostAddress> addresses;

    private List<Subject<String>> subjects;


    @Override
    public void start() throws Exception {
        subjects = ofNullable(addresses)
                .map(m -> m.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .map(this::createClient)
                        .collect(toList()))
                .orElseThrow(RuntimeException::new);

        vertx.setTimer(1000, id -> {
            Observable.merge(subjects)
                .map(dataService::fromXml)
                    .window(250, TimeUnit.MILLISECONDS)
                .flatMap(d -> d)
                .groupBy(Data::timestamp)
                    .flatMap(g -> g.toFlowable(BackpressureStrategy.BUFFER).toObservable())
                    .window(250, TimeUnit.MILLISECONDS)
                    .flatMap(obs -> obs.reduce((left, right) -> Data.copyOf(left).withAmount(left.amount().add(right.amount()))).toObservable())
                    .map(dataService::toJson)
                    .subscribeOn(Schedulers.io())
                    .subscribe(System.out::println);
        });

    }

    private Subject<String> createClient(HostAddress address) {
        NetClientOptions options = new NetClientOptions()
                .setLogActivity(true);
        PublishSubject<String> subject = PublishSubject.create();
        NetClient client = vertx.createNetClient(options);
        client.connect(address.getPort(), address.getHost(), res -> {
            if (res.succeeded()) {
                NetSocket socket = res.result();
                final String[] remainder = {""};
                socket.handler(buffer -> {
                    String payload = buffer.toString("UTF-8");
                    if (StringUtils.hasText(remainder[0])) {
                        payload = remainder[0] + payload;
                        remainder[0] = "";
                    }
                    StringTokenizer tokenizer = new StringTokenizer(payload, "\n");
                    while (tokenizer.hasMoreElements()) {
                        String data = (String) tokenizer.nextElement();
                        if (data.startsWith("<data>") && data.endsWith("</data>")) {
                            subject.onNext(data);
                        } else {
                            remainder[0] = data;
                        }
                    }
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
