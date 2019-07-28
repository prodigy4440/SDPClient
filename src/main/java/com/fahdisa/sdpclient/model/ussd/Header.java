package com.fahdisa.sdpclient.model.ussd;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Header {


    @JsonProperty
    private String spId;

    @JsonProperty
    private String serviceId;

    @JsonProperty
    private String timeStamp;

    @JsonProperty
    private String spRevpassword;

    @JsonProperty
    private String linkid;

    @JsonProperty
    private String traceUniqueID;

    @JsonProperty
    private String spRevId;

    public Header() {
    }

    public Header(String spId, String serviceId, String timeStamp, String spRevpassword, String linkid, String traceUniqueID, String spRevId) {
        this.spId = spId;
        this.serviceId = serviceId;
        this.timeStamp = timeStamp;
        this.spRevpassword = spRevpassword;
        this.linkid = linkid;
        this.traceUniqueID = traceUniqueID;
        this.spRevId = spRevId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSpRevpassword() {
        return spRevpassword;
    }

    public void setSpRevpassword(String spRevpassword) {
        this.spRevpassword = spRevpassword;
    }

    public String getLinkid() {
        return linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public String getTraceUniqueID() {
        return traceUniqueID;
    }

    public void setTraceUniqueID(String traceUniqueID) {
        this.traceUniqueID = traceUniqueID;
    }

    public String getSpRevId() {
        return spRevId;
    }

    public void setSpRevId(String spRevId) {
        this.spRevId = spRevId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Header)) return false;
        Header header = (Header) o;
        return Objects.equals(getSpId(), header.getSpId()) && Objects.equals(getServiceId(), header.getServiceId()) && Objects.equals(getTimeStamp(), header.getTimeStamp()) && Objects.equals(getSpRevpassword(), header.getSpRevpassword()) && Objects.equals(getLinkid(), header.getLinkid()) && Objects.equals(getTraceUniqueID(), header.getTraceUniqueID()) && Objects.equals(getSpRevId(), header.getSpRevId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSpId(), getServiceId(), getTimeStamp(), getSpRevpassword(), getLinkid(), getTraceUniqueID(), getSpRevId());
    }

    @Override
    public String toString() {
        return "Header{" + "spId='" + spId + '\'' + ", serviceId='" + serviceId + '\'' + ", timeStamp='" + timeStamp + '\'' + ", spRevpassword='" + spRevpassword + '\'' + ", linkid='" + linkid + '\'' + ", traceUniqueID='" + traceUniqueID + '\'' + ", spRevId='" + spRevId + '\'' + '}';
    }
}
