package com.gateway.statistics_collector.impls;

import com.gateway.Constants;
import com.gateway.rates_collector.repo.ExchangeRatesRepo;
import com.gateway.statistics_collector.StatisticsCollectorService;
import com.gateway.statistics_collector.models.RequestStatisticData;
import com.gateway.statistics_collector.repo.StatisticsColectorsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsCollectorImpl implements StatisticsCollectorService {

    private final StatisticsColectorsRepo statisticsColectorsRepo;

    public StatisticsCollectorImpl(StatisticsColectorsRepo statisticsColectorsRepo) {
        this.statisticsColectorsRepo = statisticsColectorsRepo;
    }

    @Override
    public void saveDataForRequest(RequestStatisticData data) {
        statisticsColectorsRepo.save(data);
    }

    @Override
    public List<RequestStatisticData> getAllRecordsInTimeStamp(long providedTimeStamp) {
        long setTimeStamp = System.currentTimeMillis();
        long timeStampDifferance = setTimeStamp - providedTimeStamp;
        if (timeStampDifferance > Constants.TWENTY_FOUR_HOURS_TIMESTAMP){
            setTimeStamp = providedTimeStamp + Constants.TWENTY_FOUR_HOURS_TIMESTAMP;
        }
        List<RequestStatisticData> statisticDataList = statisticsColectorsRepo.getRecordsForTimeStamp(providedTimeStamp, setTimeStamp);
        return List.of();
    }
}
