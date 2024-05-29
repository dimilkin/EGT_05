package com.gateway.rates_collector;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener {

    private final RatesFetchingServiceImpl ratesFetchingService;

    public StartupApplicationListener(RatesFetchingServiceImpl ratesFetchingService) {
        this.ratesFetchingService = ratesFetchingService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ratesFetchingService.fetchRatesData();
    }
}
