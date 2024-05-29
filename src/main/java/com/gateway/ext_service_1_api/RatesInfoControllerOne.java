package com.gateway.ext_service_1_api;

import com.gateway.exceptions.RequestDuplicationException;
import com.gateway.ext_service_1_api.models.Command;
import com.gateway.ext_service_1_api.models.CurrentRatesXmlResponse;
import com.gateway.ext_service_1_api.models.PastRatesResponse;
import com.gateway.ext_service_2_api.models.CurrentRatesResponse;
import com.gateway.ext_service_2_api.models.PeriodsRateResponse;
import com.gateway.services.RatesInfoService;
import com.gateway.services.RequestDuplicationChecker;
import com.gateway.statistics_collector.StatisticsCollectorService;
import com.gateway.statistics_collector.models.RequestStatisticData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.gateway.Constants.SERVICE_ONE;


@RestController
public class RatesInfoControllerOne {

    private final RequestDuplicationChecker requestDuplicationChecker;
    private final StatisticsCollectorService statisticsCollector;
    private final RatesInfoService ratesInfoServiceTwo;

    public RatesInfoControllerOne(RequestDuplicationChecker requestDuplicationChecker, StatisticsCollectorService statisticsCollector, RatesInfoService ratesInfoServiceTwo) {
        this.requestDuplicationChecker = requestDuplicationChecker;
        this.statisticsCollector = statisticsCollector;
        this.ratesInfoServiceTwo = ratesInfoServiceTwo;
    }

    @PostMapping(value = "/xml_api/command", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<?>> processCurrentRateRequest(@RequestBody Command request) {

        addRequestIdToDuplicationSet(request);
        saveRequestStatisticData(SERVICE_ONE, request.getId());
        if (request.getGet() == null) {
            CompletableFuture<PeriodsRateResponse> future = ratesInfoServiceTwo.getHistoryRates(request.getHistory().getCurrency(), Integer.parseInt(request.getHistory().getPeriod()));
            return future.thenApply(this::marshalPastRatesResponse);
        }
        CompletableFuture<CurrentRatesResponse> future = ratesInfoServiceTwo.getCurrentRates(request.getGet().getCurrency());
        return future.thenApply(this::marshalSingleResultResponse);
    }

    private void addRequestIdToDuplicationSet(Command request) {
        if (requestDuplicationChecker.isDuplicate(request.getId())) {
            throw new RequestDuplicationException("Request with id " + request.getId() + " is already processed");
        }
        requestDuplicationChecker.addIdToDataSet(request.getId());
    }

    private void saveRequestStatisticData(String serviceName, String requestId) {
        statisticsCollector.saveDataForRequest(new RequestStatisticData(serviceName, requestId, System.currentTimeMillis()));
    }

    private ResponseEntity<?> marshalSingleResultResponse(CurrentRatesResponse currentResponse) {
        CurrentRatesXmlResponse currentRatesXmlResponse = mapToXmlEntity(currentResponse);
        return new ResponseEntity<>(currentRatesXmlResponse, HttpStatus.OK);
    }

    private ResponseEntity<?> marshalPastRatesResponse(PeriodsRateResponse periodsRateResponse) {
        PastRatesResponse pastRatesResponse = new PastRatesResponse();
        List<CurrentRatesXmlResponse> fetxhedRates = periodsRateResponse.getAllRatesForTimestamp()
                .stream()
                .map(this::mapToXmlEntity)
                .toList();
        pastRatesResponse.setPastRates(fetxhedRates);
        return new ResponseEntity<>(pastRatesResponse, HttpStatus.OK);
    }

    private CurrentRatesXmlResponse mapToXmlEntity(CurrentRatesResponse currentResponse) {
        CurrentRatesXmlResponse currentRatesXmlResponse = new CurrentRatesXmlResponse();
        currentRatesXmlResponse.setBase(currentResponse.getBase());
        currentRatesXmlResponse.setDate(currentResponse.getDate());
        currentRatesXmlResponse.setRates(currentResponse.getRates());
        return currentRatesXmlResponse;
    }
}
