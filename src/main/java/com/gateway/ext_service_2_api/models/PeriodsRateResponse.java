package com.gateway.ext_service_2_api.models;

import java.util.List;

public class PeriodsRateResponse {

    private int period;
    private List<CurrentRatesResponse> allRatesForTimestamp;

    public PeriodsRateResponse(int period, List<CurrentRatesResponse> allRatesForTimestamp) {
        this.period = period;
        this.allRatesForTimestamp = allRatesForTimestamp;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public List<CurrentRatesResponse> getAllRatesForTimestamp() {
        return allRatesForTimestamp;
    }

    public void setAllRatesForTimestamp(List<CurrentRatesResponse> allRatesForTimestamp) {
        this.allRatesForTimestamp = allRatesForTimestamp;
    }
}
