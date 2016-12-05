package com.oracle.homework.service;

import com.oracle.homework.DataGenerator;
import com.oracle.homework.config.StreamCombinerConfig;
import com.oracle.homework.TestConfig;
import com.oracle.homework.core.domain.Data;
import com.oracle.homework.core.service.DataService;
import io.reactivex.Observable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StreamCombinerConfig.class, TestConfig.class})
public class DefaultStreamCombinerTest {
    @Autowired
    private DataGenerator generator;
    @Autowired
    private StreamCombiner streamCombiner;
    @Autowired
    private DataService dataService;

    @Test
    public void combineChunks() throws Exception {
        long now = new Date().getTime();
        String chunkOne = generator.generateDataChunk(now, 135);
        String chunkTwo = generator.generateDataChunk(now, 55);
        String chunkThree = generator.generateDataChunk(now, 210);
        String chunkFour = generator.generateDataChunk(now, 73);
        Optional<Observable<String>> result =
                streamCombiner.combineChunks(chunkOne, chunkTwo, chunkThree, chunkFour);
        assertThat(result.isPresent()).isTrue();

        Observable<String> obs = result.map(o -> o).orElse(Observable.empty());
        obs.map(dataService::fromXml)
                .window(250, TimeUnit.MILLISECONDS)
                .flatMap(d -> d)
                .groupBy(Data::timestamp)
                .flatMap(g -> g.reduce((l, r) -> Data.copyOf(l).withAmount(l.amount().add(r.amount()))).toObservable())
                .sorted(Comparator.comparing(Data::timestamp))
                .map(dataService::toJson)
                .subscribe(System.out::println);
    }
}