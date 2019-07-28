package com.fahdisa.sdpclient.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Fault implements Serializable {

    private String faultCode;
    private String description;

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> bodyMap){
        System.out.println(bodyMap);
        Map<String, Object> faultMap = (Map<String, Object>)bodyMap.get("Fault");
        this.faultCode = String.valueOf(faultMap.get("faultcode"));
        this.description = String.valueOf(faultMap.get("faultstring"));
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fault)) return false;
        Fault fault = (Fault) o;
        return Objects.equals(getFaultCode(), fault.getFaultCode())
                && Objects.equals(getDescription(), fault.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFaultCode(), getDescription());
    }

    @Override
    public String toString() {
        return "Fault{" + "faultCode='" + faultCode + '\'' + ", description='"
                + description + '\'' + '}';
    }
}
