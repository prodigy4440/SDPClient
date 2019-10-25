package com.fahdisa.sdpclient.model.sms.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryStatus implements Serializable {

    private String receiver;

    private String status;

    public DeliveryStatus() {
    }

    @JsonProperty("Header")
    public void unpackHeader(Map<String, Object> header) {
    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> body) {
        Map<String, Object> notifySmsDeliveryReceipt = (Map)body.get("getSmsDeliveryStatusResponse");
        Map<String, Object> deliveryStatusMap = (Map<String, Object>) notifySmsDeliveryReceipt.get("result");
        receiver = (String)deliveryStatusMap.get("address");
        receiver = receiver.replace("tel:","");
        status = (String)deliveryStatusMap.get("deliveryStatus");
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryStatus that = (DeliveryStatus) o;
        return Objects.equals(receiver, that.receiver) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, status);
    }

    @Override
    public String toString() {
        return "DeliveryStatus{" +
                "receiver='" + receiver + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
