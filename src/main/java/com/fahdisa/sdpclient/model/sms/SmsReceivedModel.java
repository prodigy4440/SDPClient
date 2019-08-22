package com.fahdisa.sdpclient.model.sms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsReceivedModel implements Serializable {

    private String sender;

    private String receiver;

    private String message;

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> body) {
        Map<String, Object> smsReception = (Map)body.get("notifySmsReception");
        Map<String, Object> messageModel = (Map)smsReception.get("message");
        message = (String)messageModel.getOrDefault("message","");
        sender = (String)messageModel.getOrDefault("senderAddress","");
        receiver = (String)messageModel.getOrDefault("smsServiceActivationNumber","");
        sender = sender.replace("tel:","");
        receiver = receiver.replace("tel:", "");
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SmsModel{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
