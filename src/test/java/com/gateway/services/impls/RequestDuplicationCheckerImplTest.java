package com.gateway.services.impls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestDuplicationCheckerImplTest {

    private RequestDuplicationCheckerImpl requestDuplicationChecker;

    @BeforeEach
    public void setup() {
        requestDuplicationChecker = new RequestDuplicationCheckerImpl();
    }

    @Test
    public void testIsDuplicate() {
        String transactionId = "12345";
        assertFalse(requestDuplicationChecker.isDuplicate(transactionId));
        requestDuplicationChecker.addIdToDataSet(transactionId);
        assertTrue(requestDuplicationChecker.isDuplicate(transactionId));
    }

    @Test
    public void testAddIdToDataSet() {
        String transactionId = "12345";
        assertTrue(requestDuplicationChecker.addIdToDataSet(transactionId));
        assertFalse(requestDuplicationChecker.addIdToDataSet(transactionId));
    }
}