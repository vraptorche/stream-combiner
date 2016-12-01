package com.oracle.homework;


import com.oracle.homework.core.DataAdapter;
import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.joining;

public class DefaultDataGenerator implements DataGenerator {

    @Autowired
    private DataAdapter dataAdapter;
    @Autowired
    private Marshaller marshaller;

    public String generateDataChunk(long seed, int count) {
        long next = seed;
        nextTimestamp(next);
        String chunk = LongStream.range(0, count).map(t -> this.nextTimestamp(next))
                .mapToObj(i -> Data.of(i, randomAmount()))
                .sorted(Comparator.comparing(Data::timestamp))
                .map(dataAdapter::marshal)
                .map(this::toXml).collect(joining("\n"));
        return chunk;
    }

    private BigDecimal randomAmount() {
        BigDecimal result = new BigDecimal(16 - Math.random() * 32);
        result = result.setScale(4, BigDecimal.ROUND_HALF_EVEN);
        return result;
    }

    private long nextTimestamp(long next) {
        long rnd = ((long) (Math.random() * 32));
        next += rnd;
        return next;
    }

    private String toXml(AdaptedData data) {
        try {
            StringWriter writer = new StringWriter();
            marshaller.marshal(data, writer);
            String result = writer.toString();
            result = result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
            return result;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
