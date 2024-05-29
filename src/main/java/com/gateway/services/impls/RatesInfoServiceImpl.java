package com.gateway.services.impls;

import com.gateway.ext_service_2_api.models.CurrentRatesResponse;
import com.gateway.ext_service_2_api.models.PeriodsRateResponse;
import com.gateway.rates_collector.models.ExchangeRatesDao;
import com.gateway.rates_collector.repo.ExchangeRatesRepo;
import com.gateway.services.RatesInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.gateway.Constants.MILISECONDS_IN_HOUR;

@Service
public class RatesInfoServiceImpl implements RatesInfoService {

    private final ExchangeRatesRepo exchangeRatesRepo;

    public RatesInfoServiceImpl(ExchangeRatesRepo exchangeRatesRepo) {
        this.exchangeRatesRepo = exchangeRatesRepo;
    }

    //TODO -> Implement Caching Layer in memory or Reddis

    @Override
    public CompletableFuture<CurrentRatesResponse> getCurrentRates(String currency) {
        ExchangeRatesDao exchangeRatesDao = exchangeRatesRepo.findDistinctFirstByBaseOrderByTimestampDesc(currency);
        if (exchangeRatesDao == null) {
            throw new EntityNotFoundException("No data found for currency: " + currency);
        }
        return CompletableFuture.completedFuture(new CurrentRatesResponse(exchangeRatesDao.getTimestamp(), exchangeRatesDao.getBase(), exchangeRatesDao.getDate(), exchangeRatesDao.getRates()));
    }

    @Override
    public CompletableFuture<PeriodsRateResponse> getHistoryRates(String currency, int period) {
        long nowTimeStamp = System.currentTimeMillis();
        long referenceTime = nowTimeStamp - (long) period * MILISECONDS_IN_HOUR;
        List<ExchangeRatesDao> daoRatesList = exchangeRatesRepo.getRecordsForTimeStamp(referenceTime, currency);
        if (daoRatesList.isEmpty()) {
            PeriodsRateResponse periodsRateResponse = new PeriodsRateResponse(period, new ArrayList<>());
            return CompletableFuture.completedFuture(periodsRateResponse);
        }

        List<CurrentRatesResponse> currentRatesResponseList = daoRatesList.stream()
                .map(exchengRateDao -> new CurrentRatesResponse(exchengRateDao.getTimestamp(), exchengRateDao.getBase(), exchengRateDao.getDate(), exchengRateDao.getRates()))
                .toList();
        PeriodsRateResponse periodsRateResponse = new PeriodsRateResponse(period, currentRatesResponseList);
        return CompletableFuture.completedFuture(periodsRateResponse);
    }
}
