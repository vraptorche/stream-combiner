package com.oracle.homework;

import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.DataWrapper;
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
}
