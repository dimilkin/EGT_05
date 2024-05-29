package com.gateway.ext_service_1_api.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConsumerId {

    @XmlAttribute(name = "consumer", required = true)
    private long consumer;

    @XmlElement(name = "currency", required = true)
    private String currency;

    public long getId() {
        return consumer;
    }

    public void setId(long id) {
        this.consumer = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
