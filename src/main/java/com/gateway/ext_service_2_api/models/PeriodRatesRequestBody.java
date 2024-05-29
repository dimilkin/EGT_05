package com.gateway.ext_service_2_api.models;

public class PeriodRatesRequestBody extends CurrentRatesRequestBody{

    private int period;

    public PeriodRatesRequestBody() {
    }

    public PeriodRatesRequestBody(String requestId, long timestamp, String client, String currency, int period) {
        super(requestId, timestamp, client, currency);
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
