package com.fahdisa.sdpclient.model.sms;

import com.fahdisa.sdpclient.util.JsonUtil;

import java.io.IOException;
import java.util.Objects;

public class SmsModel {

    private SmsDeliveredModel deliveredModel;

    private SmsReceivedModel receivedModel;

    private Type type;

    private String message;

    public SmsModel() {
    }

    public SmsModel(SmsReceivedModel receivedModel) {
        this.receivedModel = receivedModel;
        this.type = Type.SMS;
        this.message = "Success";
    }

    public SmsModel(SmsDeliveredModel deliveredModel) {
        this.deliveredModel = deliveredModel;
        this.type = Type.REPORT;
        this.message = "Success";
    }

    public SmsModel(String message) {
        this.message = message;
        this.type = Type.ERROR;
    }

    public SmsDeliveredModel getDeliveredModel() {
        return deliveredModel;
    }

    public void setDeliveredModel(SmsDeliveredModel deliveredModel) {
        this.deliveredModel = deliveredModel;
    }

    public SmsReceivedModel getReceivedModel() {
        return receivedModel;
    }

    public void setReceivedModel(SmsReceivedModel receivedModel) {
        this.receivedModel = receivedModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsModel smsModel = (SmsModel) o;
        return Objects.equals(deliveredModel, smsModel.deliveredModel) &&
                Objects.equals(receivedModel, smsModel.receivedModel) &&
                type == smsModel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveredModel, receivedModel, type);
    }

    @Override
    public String toString() {
        return "SmsModel{" +
                "deliveredModel=" + deliveredModel +
                ", receivedModel=" + receivedModel +
                ", type=" + type +
                ", message='" + message + '\'' +
                '}';
    }

    public static SmsModel parse(String xml) {
        try {
            if (xml.contains("notifySmsReception")) {
                SmsReceivedModel smsReceivedModel = JsonUtil.getJsonMapper().readValue(JsonUtil.xmlToJson(xml), SmsReceivedModel.class);
                return new SmsModel(smsReceivedModel);
            } else if (xml.contains("notifySmsDeliveryReceipt")) {
                SmsDeliveredModel smsDeliveredModel = JsonUtil.getJsonMapper().readValue(JsonUtil.xmlToJson(xml), SmsDeliveredModel.class);
                return new SmsModel(smsDeliveredModel);
            } else {
                return new SmsModel("Unknown payload");
            }
        } catch (IOException ioe) {
            return new SmsModel(ioe.getMessage());
        }
    }

    public enum Type {
        SMS, REPORT, ERROR;
    }
}
