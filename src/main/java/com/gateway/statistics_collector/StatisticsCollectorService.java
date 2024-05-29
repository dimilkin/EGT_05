package com.gateway.statistics_collector;

import com.gateway.statistics_collector.models.RequestStatisticData;

import java.util.List;

public interface StatisticsCollectorService {
    void saveDataForRequest(RequestStatisticData data);

//    List<RequestStatisticData> getAllRecords();
    List<RequestStatisticData> getAllRecordsInTimeStamp(long timeStamp);
}
