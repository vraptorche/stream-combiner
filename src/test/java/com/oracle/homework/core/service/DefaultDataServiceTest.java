package com.oracle.homework.core.service;

import com.oracle.homework.core.domain.Data;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.assertj.core.api.Assertions.assertThat;


public class DefaultDataServiceTest {

    private DataService service;

    @Before
    public void setUp() throws Exception {
        service = new DefaultDataService();
    }

    @Test
    public void fromXml() throws Exception {
        Data data = service.fromXml("<data><timestamp>123456789</timestamp><amount>1234.567890</amount></data>");
        assertThat(data).isNotNull();
        assertThat(data.timestamp()).isEqualTo(123456789);
        assertThat(data.amount()).isEqualTo(1234.567890);
    }

    @Test
    public void toJson() throws Exception {
        Data data = Data.of(123456789L, 1234.567890);
        String json = service.toJson(data);
        String expected = "{ \"data\": { \"timestamp\":123456789, \"amount\":1234.56789 }}";
        JSONAssert.assertEquals(json,expected,false);
    }

}