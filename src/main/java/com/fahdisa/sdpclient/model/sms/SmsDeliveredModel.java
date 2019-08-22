package com.fahdisa.sdpclient.model.sms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsDeliveredModel implements Serializable {

    private String traceUniqueId;
    private String correlator;
    private String receiver;
    private String deliveryStatus;

    @JsonProperty("Header")
    public void unpackHeader(Map<String, Object> header) {
        Map<String, Object> notifySOAPHeader = (Map)header.get("NotifySOAPHeader");
        traceUniqueId = (String)notifySOAPHeader.get("traceUniqueID");
    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> body) {
        Map<String, Object> notifySmsDeliveryReceipt = (Map)body.get("notifySmsDeliveryReceipt");
        correlator = (String)notifySmsDeliveryReceipt.get("correlator");
        Map<String, Object> deliveryStatusMap = (Map<String, Object>) notifySmsDeliveryReceipt.get("deliveryStatus");
        receiver = (String)deliveryStatusMap.get("address");
        receiver = receiver.replace("tel:","");
        deliveryStatus = (String)deliveryStatusMap.get("deliveryStatus");
    }

    public String getTraceUniqueId() {
        return traceUniqueId;
    }

    public void setTraceUniqueId(String traceUniqueId) {
        this.traceUniqueId = traceUniqueId;
    }

    public String getCorrelator() {
        return correlator;
    }

    public void setCorrelator(String correlator) {
        this.correlator = correlator;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsDeliveredModel that = (SmsDeliveredModel) o;
        return Objects.equals(traceUniqueId, that.traceUniqueId) &&
                Objects.equals(correlator, that.correlator) &&
                Objects.equals(receiver, that.receiver) &&
                Objects.equals(deliveryStatus, that.deliveryStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traceUniqueId, correlator, receiver, deliveryStatus);
    }

    @Override
    public String toString() {
        return "SmsDeliveredModel{" +
                "traceUniqueId='" + traceUniqueId + '\'' +
                ", correlator='" + correlator + '\'' +
                ", receiver='" + receiver + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}
