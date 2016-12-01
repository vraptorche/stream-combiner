package com.oracle.homework.core.domain;

import com.oracle.homework.core.DataAdapter;
import org.immutables.value.Value;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

@Value.Immutable
@XmlRootElement(name = "data")
@XmlJavaTypeAdapter(value = DataAdapter.class, type = Data.class)
public interface AbstractData {
    @Value.Parameter
    Long timestamp();

    @Value.Parameter
    BigDecimal amount();
}
