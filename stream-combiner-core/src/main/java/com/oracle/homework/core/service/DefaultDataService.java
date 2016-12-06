package com.oracle.homework.core.service;


import com.oracle.homework.core.DataAdapter;
import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.Data;
import com.oracle.homework.core.domain.DataWrapper;
import org.eclipse.persistence.jaxb.JAXBContextProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public class DefaultDataService implements DataService {

    private final DataAdapter dataAdapter = new DataAdapter();

    @Override
    public Data fromXml(String payload) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Data.class, AdaptedData.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        AdaptedData data = unmarshaller.unmarshal(new StreamSource(new StringReader(payload)), AdaptedData.class).getValue();
        Data result = dataAdapter.unmarshal(data);
        return result;
    }

    @Override
    public String toJson(Data data) throws Exception {
        JAXBContext context = JAXBContext.newInstance(DataWrapper.class, AdaptedData.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(JAXBContextProperties.JSON_INCLUDE_ROOT, true);
        AdaptedData adaptedData = dataAdapter.marshal(data);
        StringWriter writer = new StringWriter();
        marshaller.marshal(new DataWrapper(adaptedData), writer);
        String aString = writer.toString();
        return aString;
    }
}
