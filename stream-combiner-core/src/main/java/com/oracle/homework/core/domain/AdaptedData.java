package com.oracle.homework.core.domain;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "data")
//@XmlAccessorType(XmlAccessType.FIELD)
public class AdaptedData {

    private BigDecimal amount;
    private Long timestamp;

    public AdaptedData() {
    }

    public AdaptedData(long timestamp, BigDecimal amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    @XmlElement
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @XmlElement
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
