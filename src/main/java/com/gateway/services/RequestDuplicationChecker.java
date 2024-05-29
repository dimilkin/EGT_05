package com.gateway.services;

public interface RequestDuplicationChecker {
    boolean isDuplicate(String transactionId);
    boolean addIdToDataSet(String transactionId);
}
