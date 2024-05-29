package com.gateway.statistics_collector;

import com.gateway.Constants;
import com.gateway.statistics_collector.models.RequestStatisticData;
import com.gateway.statistics_collector.models.StatisticDataDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private final StatisticsCollectorService statisticsCollector;

    public StatisticsController(StatisticsCollectorService statisticsCollector) {
        this.statisticsCollector = statisticsCollector;
    }

    @GetMapping("/statistics/{service_id}/{time_interval}")
    public ResponseEntity<List<StatisticDataDTO>> getStatistics(@PathVariable String service_id, @PathVariable long time_interval) {
        long timeIntervalInMs = time_interval * Constants.MILISECONDS_IN_HOUR;
        if (timeIntervalInMs > Constants.MILISECONDS_IN_FIVE_DAYS){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<RequestStatisticData> records = statisticsCollector.getAllRecordsInTimeStamp(time_interval);
        List<StatisticDataDTO> statistics = records.stream()
                .filter(record -> record.getServiceName().equals(service_id))
                .collect(Collectors.groupingBy(RequestStatisticData::getServiceName))
                .entrySet()
                .stream()
                .map(entry -> new StatisticDataDTO(entry.getKey(), entry.getValue().size()))
                .toList();

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }


}
