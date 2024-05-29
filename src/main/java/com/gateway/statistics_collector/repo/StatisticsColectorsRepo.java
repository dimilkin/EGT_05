package com.gateway.statistics_collector.repo;

import com.gateway.rates_collector.models.ExchangeRatesDao;
import com.gateway.statistics_collector.models.RequestStatisticData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsColectorsRepo  extends JpaRepository<RequestStatisticData, Long> {

    @Query("SELECT e FROM RequestStatisticData e WHERE e.timeStamp >= :currentTimestamp AND e.timeStamp <= :setTimeStamp")
    List<RequestStatisticData> getRecordsForTimeStamp(@Param("currentTimestamp") Long currentTimestamp, @Param("setTimeStamp") Long setTimeStamp);
}
