package com.gateway.services.impls;

import com.gateway.Constants;
import com.gateway.ext_service_2_api.models.CurrentRatesResponse;
import com.gateway.ext_service_2_api.models.PeriodsRateResponse;
import com.gateway.rates_collector.models.ExchangeRatesDao;
import com.gateway.rates_collector.repo.ExchangeRatesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RatesInfoServiceImplTest {

    @InjectMocks
    private RatesInfoServiceImpl ratesInfoService;

    @Mock
    private ExchangeRatesRepo exchangeRatesRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCurrentRates() throws ExecutionException, InterruptedException {
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("CDK", 0.8);
        rates.put("BGN", 1.9);
        ExchangeRatesDao exchangeRatesDao = new ExchangeRatesDao();
        exchangeRatesDao.setBase("EUR");
        exchangeRatesDao.setId(1L);
        exchangeRatesDao.setTimestamp(System.currentTimeMillis());
        exchangeRatesDao.setDate("2024-05-01");
        exchangeRatesDao.setRates(rates);
        when(exchangeRatesRepo.findDistinctFirstByBaseOrderByTimestampDesc("EUR")).thenReturn(exchangeRatesDao);

        CompletableFuture<CurrentRatesResponse> response = ratesInfoService.getCurrentRates("EUR");

        assertEquals("EUR", response.get().getBase());
        assertEquals("2024-05-01", response.get().getDate());
    }

    @Test
    public void testGetHistoryRates() throws ExecutionException, InterruptedException {
        List<ExchangeRatesDao> daoRatesList = new ArrayList<>();

        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("CDK", 0.8);
        rates.put("BGN", 1.9);

        ExchangeRatesDao exchangeRatesDao1 = new ExchangeRatesDao();
        exchangeRatesDao1.setBase("EUR");
        exchangeRatesDao1.setId(1L);
        exchangeRatesDao1.setTimestamp(System.currentTimeMillis() - Constants.MILISECONDS_IN_HOUR * 2L);
        exchangeRatesDao1.setDate("2024-05-01");
        exchangeRatesDao1.setRates(rates);

        ExchangeRatesDao exchangeRatesDao2 = new ExchangeRatesDao();
        exchangeRatesDao2.setBase("EUR");
        exchangeRatesDao2.setId(1L);
        exchangeRatesDao2.setTimestamp(System.currentTimeMillis() - Constants.MILISECONDS_IN_HOUR * 12L);
        exchangeRatesDao2.setDate("2024-05-01");
        exchangeRatesDao2.setRates(rates);

        daoRatesList.add(exchangeRatesDao1);
        daoRatesList.add(exchangeRatesDao2);

        when(exchangeRatesRepo.getRecordsForTimeStamp(System.currentTimeMillis(), "EUR")).thenReturn(daoRatesList);
        CompletableFuture<PeriodsRateResponse> response = ratesInfoService.getHistoryRates("EUR", 0);

        PeriodsRateResponse periodsRateResponse = response.get();
        assertEquals(1, periodsRateResponse.getPeriod());
    }
}