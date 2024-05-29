package com.gateway.rates_collector.models;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "exchange_rate_responses", indexes = @Index(name = "index_base", columnList = "base"))
public class ExchangeRatesDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long timestamp;

    @Column(nullable = false)
    private String base;

    @Column(nullable = false)
    private String date;

    @ElementCollection
    @CollectionTable(name = "exchange_rate_rates", joinColumns = @JoinColumn(name = "exchange_rate_response_id"))
    @MapKeyColumn(name = "currency")
    @Column(name = "rate")
    private Map<String, Double> rates;

    public ExchangeRatesDao() {
    }

    public ExchangeRatesDao(long timestamp, String base, String date, Map<String, Double> rates) {
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
