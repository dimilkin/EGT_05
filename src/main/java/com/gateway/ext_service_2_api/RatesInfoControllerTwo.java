package com.gateway.ext_service_2_api;


import com.gateway.exceptions.RequestDuplicationException;
import com.gateway.ext_service_2_api.models.CurrentRatesRequestBody;
import com.gateway.ext_service_2_api.models.CurrentRatesResponse;
import com.gateway.ext_service_2_api.models.PeriodRatesRequestBody;
import com.gateway.ext_service_2_api.models.PeriodsRateResponse;
import com.gateway.services.RatesInfoService;
import com.gateway.services.RequestDuplicationChecker;
import com.gateway.statistics_collector.StatisticsCollectorService;
import com.gateway.statistics_collector.models.RequestStatisticData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static com.gateway.Constants.SERVICE_TWO;

@RestController
public class RatesInfoControllerTwo {

    private final RequestDuplicationChecker requestDuplicationChecker;
    private final StatisticsCollectorService statisticsCollector;
    private final RatesInfoService ratesInfoServiceTwo;

    public RatesInfoControllerTwo(RequestDuplicationChecker requestDuplicationChecker, StatisticsCollectorService statisticsCollector, RatesInfoService ratesInfoServiceTwo) {
        this.requestDuplicationChecker = requestDuplicationChecker;
        this.statisticsCollector = statisticsCollector;
        this.ratesInfoServiceTwo = ratesInfoServiceTwo;
    }

    @PostMapping("/json_api/current")
    public CompletableFuture<ResponseEntity<CurrentRatesResponse>> getCurrentRates(@RequestBody CurrentRatesRequestBody currentRatesRequestBody) {
        addRequestIdToDuplicationSet(currentRatesRequestBody);
        saveRequestStatisticData(SERVICE_TWO, currentRatesRequestBody.getRequestId());
        CompletableFuture<CurrentRatesResponse> future = ratesInfoServiceTwo.getCurrentRates(currentRatesRequestBody.getCurrency());
        future.join();
        return future.thenApply(result -> new ResponseEntity<>(result, HttpStatus.OK));
    }

    @PostMapping("/json_api/history")
    public CompletableFuture<ResponseEntity<PeriodsRateResponse>> getHistoryRates(@RequestBody PeriodRatesRequestBody periodRatesRequestBody) {
        addRequestIdToDuplicationSet(periodRatesRequestBody);
        saveRequestStatisticData(SERVICE_TWO, periodRatesRequestBody.getRequestId());
        CompletableFuture<PeriodsRateResponse> future = ratesInfoServiceTwo.getHistoryRates(periodRatesRequestBody.getCurrency(), periodRatesRequestBody.getPeriod());
        future.join();
        return future.thenApply(result -> new ResponseEntity<>(result, HttpStatus.OK));
    }

    private <E extends CurrentRatesRequestBody> void addRequestIdToDuplicationSet(E request) {
        if (requestDuplicationChecker.isDuplicate(request.getRequestId())) {
            throw new RequestDuplicationException("Request with id " + request.getRequestId() + " is already processed");
        }
        requestDuplicationChecker.addIdToDataSet(request.getRequestId());
    }

    private void saveRequestStatisticData(String serviceName, String requestId) {
        statisticsCollector.saveDataForRequest(new RequestStatisticData(serviceName, requestId, System.currentTimeMillis()));
    }
}
