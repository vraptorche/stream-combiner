package com.oracle.homework.core;

import com.oracle.homework.core.domain.AdaptedData;
import com.oracle.homework.core.domain.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


public class DataAdapterTest {


    private DataAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new DataAdapter();
    }

    @Test
    public void unmarshal() throws Exception {
        long time = new Date().getTime();
        AdaptedData adaptedData = new AdaptedData(time, 2.34);
        Data data = adapter.unmarshal(adaptedData);
        assertThat(data).isNotNull();
        assertThat(data.timestamp()).isEqualTo(time);
    }

    @Test
    public void marshal() throws Exception {
        long time = new Date().getTime();
        Data data = Data.of(time, 2.34);
        AdaptedData adaptedData = adapter.marshal(data);
        assertThat(adaptedData).isNotNull();
        assertThat(adaptedData.getTimestamp()).isEqualTo(time);
    }

}