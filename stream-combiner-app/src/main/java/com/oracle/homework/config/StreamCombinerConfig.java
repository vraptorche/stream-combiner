package com.oracle.homework.config;

import com.oracle.homework.StreamClientVerticle;
import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.DataWrapper;
import com.oracle.homework.core.service.DataService;
import com.oracle.homework.core.service.DefaultDataService;
import com.oracle.homework.service.DefaultStreamCombiner;
import com.oracle.homework.service.StreamCombiner;
import io.vertx.core.Verticle;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Configuration
@EnableConfigurationProperties(StreamCombinerProperties.class)
public class StreamCombinerConfig {

    private StreamCombinerProperties properties;

    public StreamCombinerConfig(StreamCombinerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(AdaptedData.class, DataWrapper.class);
    }

    @Bean
    public StreamCombiner streamCombiner() {
        return new DefaultStreamCombiner();
    }

    @Bean
    public DataService dataService() {
        return new DefaultDataService();
    }

    @Bean
    public Verticle streamClientVerticle() {
        StreamClientVerticle verticle = new StreamClientVerticle();
        verticle.setAddresses(properties.getHosts());
        return verticle;
    }
}
