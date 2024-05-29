package com.gateway.ext_service_1_api.models;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.FIELD)
public class Command {

    @XmlAttribute( required = true, name = "id")
    private String id;

    @XmlElement( required = false, name = "get")
    private ConsumerId get;

    @XmlElement( required = false, name = "history")
    private History history;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConsumerId getGet() {
        return get;
    }

    public void setGet(ConsumerId get) {
        this.get = get;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
