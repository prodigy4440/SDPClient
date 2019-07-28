package com.fahdisa.sdpclient.model.ussd;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;


public class Ussd implements Serializable {

    @JsonProperty
    private Header header;

    @JsonProperty
    private String codeScheme;

    @JsonProperty
    private Integer msgType;

    @JsonProperty
    private String serviceCode;

    @JsonProperty
    private Integer ussdOpType;

    @JsonProperty
    private String ussdString;

    @JsonProperty
    private String senderCB;

    @JsonProperty
    private String receiveCB;

    @JsonProperty
    private String msisdn;

    @JsonProperty
    private Map<String, String> extension;

    public Ussd() {
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
        Map<String, Object> ussdReception = (Map<String, Object>) notifyUssdReception.get("notifyUssdReception");
        this.codeScheme = String.valueOf(ussdReception.get("codeScheme"));
        this.msgType = Integer.parseInt(String.valueOf(ussdReception.get("msgType")));
        this.serviceCode = String.valueOf(ussdReception.get("serviceCode"));;
        this.ussdOpType = Integer.parseInt( String.valueOf(ussdReception.get("ussdOpType")));
        this.ussdString = String.valueOf(ussdReception.get("ussdString"));;
        this.senderCB = String.valueOf(ussdReception.get("senderCB"));;
        this.receiveCB = String.valueOf(ussdReception.get("receiveCB"));;
        this.msisdn = String.valueOf(ussdReception.get("msIsdn"));
        extension  = ((Map<String, String>) ussdReception.get("extensionInfo"));
    }



    public Ussd(Header header, String codeScheme, Integer msgType, String serviceCode, Integer ussdOpType,
                String ussdString, String senderCB, String receiveCB, String msisdn, Map<String, String> extension) {
        this.header = header;
        this.codeScheme = codeScheme;
        this.msgType = msgType;
        this.serviceCode = serviceCode;
        this.ussdOpType = ussdOpType;
        this.ussdString = ussdString;
        this.senderCB = senderCB;
        this.receiveCB = receiveCB;
        this.msisdn = msisdn;
        this.extension = extension;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getCodeScheme() {
        return codeScheme;
    }

    public void setCodeScheme(String codeScheme) {
        this.codeScheme = codeScheme;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Integer getUssdOpType() {
        return ussdOpType;
    }

    public void setUssdOpType(Integer ussdOpType) {
        this.ussdOpType = ussdOpType;
    }

    public String getUssdString() {
        return ussdString;
    }

    public void setUssdString(String ussdString) {
        this.ussdString = ussdString;
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

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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
        if (!(o instanceof Ussd)) return false;
        Ussd ussd = (Ussd) o;
        return Objects.equals(getCodeScheme(), ussd.getCodeScheme()) && Objects.equals(getMsgType(),
                ussd.getMsgType()) && Objects.equals(getServiceCode(), ussd.getServiceCode())
                && Objects.equals(getUssdOpType(), ussd.getUssdOpType()) && Objects.equals(getUssdString(),
                ussd.getUssdString()) && Objects.equals(getSenderCB(), ussd.getSenderCB())
                && Objects.equals(getReceiveCB(), ussd.getReceiveCB()) && Objects.equals(getMsisdn(),
                ussd.getMsisdn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodeScheme(), getMsgType(), getServiceCode(), getUssdOpType(),
                getUssdString(), getSenderCB(), getReceiveCB(), getMsisdn());
    }

    @Override
    public String toString() {
        return "Ussd{" + "header=" + header + ", codeScheme='" + codeScheme + '\'' + ", msgType="
                + msgType + ", serviceCode='" + serviceCode + '\'' + ", ussdOpType=" + ussdOpType
                + ", ussdString='" + ussdString + '\'' + ", senderCB='" + senderCB + '\'' + ", receiveCB='"
                + receiveCB + '\'' + ", msisdn='" + msisdn + '\'' + ", extension=" + extension + '}';
    }
}
