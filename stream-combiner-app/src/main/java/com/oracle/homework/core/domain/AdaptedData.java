package com.oracle.homework.core.domain;

import javax.xml.bind.annotation.*;

//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class AdaptedData {

    private Double amount;
    private Long timestamp;

    public AdaptedData() {
    }

    public AdaptedData(long timestamp, double amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    @XmlElement
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
