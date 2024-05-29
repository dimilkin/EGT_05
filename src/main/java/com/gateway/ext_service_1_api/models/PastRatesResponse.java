package com.gateway.ext_service_1_api.models;

import com.gateway.ext_service_2_api.models.CurrentRatesResponse;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rates")
public class PastRatesResponse {

    @XmlElement(name = "past", required = true)
    List<CurrentRatesXmlResponse> pastRates;

    public List<CurrentRatesXmlResponse> getPastRates() {
        return pastRates;
    }

    public void setPastRates(List<CurrentRatesXmlResponse> pastRates) {
        this.pastRates = pastRates;
    }
}
