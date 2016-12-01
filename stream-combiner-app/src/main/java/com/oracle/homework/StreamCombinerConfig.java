package com.oracle.homework;

import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.DataWrapper;
import com.oracle.homework.core.service.DataService;
import com.oracle.homework.core.service.DefaultDataService;
import com.oracle.homework.service.DefaultStreamCombiner;
import com.oracle.homework.service.StreamCombiner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Configuration
public class StreamCombinerConfig {

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
}
