package com.oracle.homework;

import com.oracle.homework.core.DataAdapter;
import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.DataWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Configuration
public class StreamGeneratorConfig {

    @Bean
    public DataAdapter dataAdapter() {
        return new DataAdapter();
    }

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(DataWrapper.class, AdaptedData.class);
    }

    @Bean
    public Marshaller marshaller(JAXBContext jaxbContext) throws JAXBException {
        return jaxbContext.createMarshaller();
    }

    @Bean
    public DataGenerator dataGenerator() {
        return new DefaultDataGenerator();
    }
}
