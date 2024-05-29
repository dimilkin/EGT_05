package com.gateway.ext_service_1_api.models;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "history")
public class History {

    @XmlAttribute(name = "consumer", required = true)
    private String consumer;
    @XmlAttribute(name = "currency", required = true)
    private String currency;
    @XmlAttribute(name = "period", required = true)
    private String period;



    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
