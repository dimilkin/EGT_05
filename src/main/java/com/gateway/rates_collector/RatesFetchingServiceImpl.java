package com.gateway.rates_collector;

import com.gateway.rates_collector.models.ExchangeRateResponse;
import com.gateway.rates_collector.models.ExchangeRatesDao;
import com.gateway.rates_collector.repo.ExchangeRatesRepo;
import org.apache.log4j.helpers.LogLog;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@PropertySource("classpath:application.properties")
public class RatesFetchingServiceImpl {

    @Value("${secret.api.access.key}")
    private String apiKey;
    private final RestClient restClient;
    private final ExchangeRatesRepo exchangeRatesRepo;
    private final RabbitTemplate rabbitMqTemplate;


    public RatesFetchingServiceImpl(ExchangeRatesRepo exchangeRatesRepo, RabbitTemplate rabbitMqTemplate) {
        this.restClient = RestClient.create();;
        this.exchangeRatesRepo = exchangeRatesRepo;
        this.rabbitMqTemplate = rabbitMqTemplate;
    }

    @Async
    @Scheduled(fixedRate = 3600000)  //1hour in milliseconds
    public void fetchRatesData() {
        ExchangeRateResponse exchangeRateResponse = restClient.get()
                .uri("http://data.fixer.io/api/latest?access_key=" + apiKey)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(ExchangeRateResponse.class);

        if (null != exchangeRateResponse && exchangeRateResponse.isSuccess()) {
            ExchangeRatesDao exchangeRatesDao = new ExchangeRatesDao(System.currentTimeMillis(), exchangeRateResponse.getBase(), exchangeRateResponse.getDate(), exchangeRateResponse.getRates());
            exchangeRatesRepo.save(exchangeRatesDao);
            rabbitMqTemplate.convertAndSend(exchangeRateResponse);
        }else {
            LogLog.error("Failed to fetch rates data :" + LocalDateTime.now());
        }
    }
}
