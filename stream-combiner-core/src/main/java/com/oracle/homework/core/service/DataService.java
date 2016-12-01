package com.oracle.homework.core.service;


import com.oracle.homework.core.domain.Data;

import javax.xml.bind.JAXBException;

public interface DataService {
    Data fromXml(String payload) throws Exception;

    String toJson(Data data) throws Exception;
}
