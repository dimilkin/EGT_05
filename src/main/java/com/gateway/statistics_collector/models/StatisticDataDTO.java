package com.gateway.statistics_collector.models;

public class StatisticDataDTO {

    private String serviceName;
    private long numberOfRequests;

    public StatisticDataDTO() {
    }

    public StatisticDataDTO(String serviceName, long numberOfRequests) {
        this.serviceName = serviceName;
        this.numberOfRequests = numberOfRequests;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(long numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }
}
