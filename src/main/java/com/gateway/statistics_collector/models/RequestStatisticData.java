package com.gateway.statistics_collector.models;

import jakarta.persistence.*;

@Entity
@Table(name = "request_statistic_data")
public class RequestStatisticData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String requestId;

    @Column(nullable = false)
    private long timeStamp;

    public RequestStatisticData() {
    }

    public RequestStatisticData(String serviceName, String requestId, long timeStamp) {
        this.serviceName = serviceName;
        this.requestId = requestId;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
