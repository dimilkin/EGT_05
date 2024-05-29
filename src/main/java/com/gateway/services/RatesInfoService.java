package com.gateway.services;

import com.gateway.ext_service_2_api.models.CurrentRatesRequestBody;
import com.gateway.ext_service_2_api.models.CurrentRatesResponse;
import com.gateway.ext_service_2_api.models.PeriodRatesRequestBody;
import com.gateway.ext_service_2_api.models.PeriodsRateResponse;

import java.util.concurrent.CompletableFuture;

public interface RatesInfoService {
    CompletableFuture<CurrentRatesResponse> getCurrentRates(String currency);
    CompletableFuture<PeriodsRateResponse> getHistoryRates(String currency, int period);
}
