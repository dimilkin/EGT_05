package com.gateway.ext_service_2_api.models;

import java.util.Map;

public class CurrentRatesResponse {
    private long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;

    public CurrentRatesResponse() {
    }

    public CurrentRatesResponse(long timestamp, String base, String date, Map<String, Double> rates) {
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
