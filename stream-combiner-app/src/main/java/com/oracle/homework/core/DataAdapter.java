package com.oracle.homework.core;


import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.Data;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DataAdapter extends XmlAdapter<AdaptedData, Data> {
    @Override
    public Data unmarshal(AdaptedData v) throws Exception {
        return Data.of(v.getTimestamp(), v.getAmount());
    }

    @Override
    public AdaptedData marshal(Data v) throws Exception {
        return new AdaptedData(v.timestamp(), v.amount());
    }
}
