package com.oracle.homework;

import com.oracle.homework.config.StreamGeneratorConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StreamGeneratorConfig.class)
public class DefaultDataGeneratorTest {

    @Autowired
    private DataGenerator dataGenerator;

    @Test
    public void generateDataChunk() throws Exception {
        String chunk = dataGenerator.generateDataChunk(new Date().getTime(), 100);
        assertThat(chunk).isNotNull();
    }
}