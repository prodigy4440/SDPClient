package com.pc.sdpclient.model.ussd;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Abort implements Serializable {

    @JsonProperty
    private Header header;

    @JsonProperty
    private String senderCB;

    @JsonProperty
    private String receiveCB;

    @JsonProperty
    private String reason;

    @JsonProperty
    private Map<String, String> extension;

    public Abort() {
        header = new Header();
    }

    @JsonProperty("Header")
    public void unpackHeader(Map<String, Object> header) {
        Map<String, Object> innerHeader = (Map<String, Object>) header.get("NotifySOAPHeader");
        this.header.setSpId(String.valueOf(innerHeader.get("spId")));
        this.header.setServiceId(String.valueOf(innerHeader.get("serviceId")));
        this.header.setSpRevId(String.valueOf(innerHeader.get("spRevId")));
        this.header.setTimeStamp(String.valueOf(innerHeader.get("timeStamp")));
        this.header.setLinkid(String.valueOf(innerHeader.get("linkid")));
        this.header.setSpRevpassword(String.valueOf(innerHeader.get("spRevpassword")));
        this.header.setTraceUniqueID(String.valueOf(innerHeader.get("traceUniqueID")));
    }


    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> body) {
        Map<String, Object> notifyUssdReception = (Map<String, Object>) body;
        Map<String, Object> ussdReception = (Map<String, Object>) notifyUssdReception.get("notifyUssdAbort");
        this.senderCB = String.valueOf(ussdReception.get("senderCB"));;
        this.receiveCB = String.valueOf(ussdReception.get("receiveCB"));;
        this.reason = String.valueOf(ussdReception.get("abortReason"));
        extension  = ((Map<String, String>) ussdReception.get("extensionInfo"));
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getSenderCB() {
        return senderCB;
    }

    public void setSenderCB(String senderCB) {
        this.senderCB = senderCB;
    }

    public String getReceiveCB() {
        return receiveCB;
    }

    public void setReceiveCB(String receiveCB) {
        this.receiveCB = receiveCB;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<String, String> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, String> extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Abort)) return false;
        Abort abort = (Abort) o;
        return Objects.equals(getHeader(), abort.getHeader()) && Objects.equals(getSenderCB(), abort.getSenderCB()) && Objects.equals(getReceiveCB(), abort.getReceiveCB()) && Objects.equals(getReason(), abort.getReason()) && Objects.equals(getExtension(), abort.getExtension());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeader(), getSenderCB(), getReceiveCB(), getReason(), getExtension());
    }

    @Override
    public String toString() {
        return "Abort{" + "header=" + header + ", senderCB='" + senderCB + '\'' + ", receiveCB='"
                + receiveCB + '\'' + ", reason='" + reason + '\'' + ", extension=" + extension + '}';
    }
}
