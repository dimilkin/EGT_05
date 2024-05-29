package com.gateway.services.impls;

import com.gateway.services.RequestDuplicationChecker;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestDuplicationCheckerImpl implements RequestDuplicationChecker {
    private Set<String> processedRequests = ConcurrentHashMap.newKeySet();

    @Override
    public boolean isDuplicate(String transactionId) {
        return processedRequests.contains(transactionId);
    }

    @Override
    public boolean addIdToDataSet (String transactionId) {
        return processedRequests.add(transactionId);
    }

}
