package com.gateway.ext_service_1_api.models;

import jakarta.xml.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "response")
public class CurrentRatesXmlResponse {

    private String base;
    private String date;
    private Map<String, Double> rates;

    @XmlElement
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @XmlElement
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement
    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
