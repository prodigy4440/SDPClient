package com.pc.sdpclient.model.ussd;

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


    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "Ussd{" + "header=" + header + ", codeScheme='" + codeScheme + '\'' + ", msgType=" + msgType + ", serviceCode='" + serviceCode + '\'' + ", ussdOpType=" + ussdOpType + ", ussdString='" + ussdString + '\'' + ", senderCB='" + senderCB + '\'' + ", receiveCB='" + receiveCB + '\'' + ", msisdn='" + msisdn + '\'' + ", extension=" + extension + '}';
    }
}
