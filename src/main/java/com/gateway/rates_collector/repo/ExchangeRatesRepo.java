package com.gateway.rates_collector.repo;

import com.gateway.rates_collector.models.ExchangeRatesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRatesRepo extends JpaRepository<ExchangeRatesDao, Long> {
    ExchangeRatesDao findDistinctFirstByBaseOrderByTimestampDesc(String base);

    @Query("SELECT e FROM ExchangeRatesDao e WHERE e.timestamp >= :currentTimestamp AND e.base = :currency")
    List<ExchangeRatesDao> getRecordsForTimeStamp(@Param("currentTimestamp") Long currentTimestamp, @Param("currency") String currency);
}
