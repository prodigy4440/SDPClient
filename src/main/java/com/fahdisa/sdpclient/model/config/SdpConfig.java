package com.fahdisa.sdpclient.model.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SdpConfig implements Serializable {

    private String name;
    private String spId;
    private String spPassword;
    private String timestamp;
    private String oa;
    private String fa;
    private String serviceId;
    private String productId;
    private String ussdServiceActivationNumber;
    private String correlator;
    private String endpoint;

    public SdpConfig() {
    }

    public SdpConfig(String name, String spId, String spPassword, String timestamp,
                     String oa, String fa, String serviceId, String productId,
                     String ussdServiceActivationNumber, String correlator,
                     String endpoint) {
        this.name = name;
        this.spId = spId;
        this.spPassword = spPassword;
        this.timestamp = timestamp;
        this.oa = oa;
        this.fa = fa;
        this.serviceId = serviceId;
        this.productId = productId;
        this.ussdServiceActivationNumber = ussdServiceActivationNumber;
        this.correlator = correlator;
        this.endpoint = endpoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpPassword() {
        return spPassword;
    }

    public void setSpPassword(String spPassword) {
        this.spPassword = spPassword;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOa() {
        return oa;
    }

    public void setOa(String oa) {
        this.oa = oa;
    }

    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUssdServiceActivationNumber() {
        return ussdServiceActivationNumber;
    }

    public void setUssdServiceActivationNumber(String ussdServiceActivationNumber) {
        this.ussdServiceActivationNumber = ussdServiceActivationNumber;
    }

    public String getCorrelator() {
        return correlator;
    }

    public void setCorrelator(String correlator) {
        this.correlator = correlator;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "SdpConfig{" +
                "name='" + name + '\'' +
                ", spId='" + spId + '\'' +
                ", spPassword='" + spPassword + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", oa='" + oa + '\'' +
                ", fa='" + fa + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", productId='" + productId + '\'' +
                ", ussdServiceActivationNumber='" + ussdServiceActivationNumber + '\'' +
                ", correlator='" + correlator + '\'' +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
