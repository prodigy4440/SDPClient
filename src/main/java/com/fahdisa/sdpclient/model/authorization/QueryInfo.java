package com.fahdisa.sdpclient.model.authorization;

import java.io.Serializable;
import java.util.Objects;

public class QueryInfo implements Serializable {

    private String productName;

    private String productId;

    private String transactionId;

    private Integer amount;

    private String currency;

    private Integer serviceInterval;

    private Integer serviceIntervalUnit;

    private Integer accessTokenStatus;

    private String accessToken;

    private String tokenValidity;

    public QueryInfo() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getServiceInterval() {
        return serviceInterval;
    }

    public void setServiceInterval(Integer serviceInterval) {
        this.serviceInterval = serviceInterval;
    }

    public Integer getServiceIntervalUnit() {
        return serviceIntervalUnit;
    }

    public void setServiceIntervalUnit(Integer serviceIntervalUnit) {
        this.serviceIntervalUnit = serviceIntervalUnit;
    }

    public Integer getAccessTokenStatus() {
        return accessTokenStatus;
    }

    public void setAccessTokenStatus(Integer accessTokenStatus) {
        this.accessTokenStatus = accessTokenStatus;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(String tokenValidity) {
        this.tokenValidity = tokenValidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryInfo queryInfo = (QueryInfo) o;
        return Objects.equals(productName, queryInfo.productName) &&
                Objects.equals(productId, queryInfo.productId) &&
                Objects.equals(transactionId, queryInfo.transactionId) &&
                Objects.equals(amount, queryInfo.amount) &&
                Objects.equals(currency, queryInfo.currency) &&
                Objects.equals(serviceInterval, queryInfo.serviceInterval) &&
                Objects.equals(serviceIntervalUnit, queryInfo.serviceIntervalUnit) &&
                Objects.equals(accessTokenStatus, queryInfo.accessTokenStatus) &&
                Objects.equals(accessToken, queryInfo.accessToken) &&
                Objects.equals(tokenValidity, queryInfo.tokenValidity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productId, transactionId, amount, currency, serviceInterval,
                serviceIntervalUnit, accessTokenStatus, accessToken, tokenValidity);
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", serviceInterval=" + serviceInterval +
                ", serviceIntervalUnit=" + serviceIntervalUnit +
                ", accessTokenStatus=" + accessTokenStatus +
                ", accessToken='" + accessToken + '\'' +
                ", tokenValidity='" + tokenValidity + '\'' +
                '}';
    }
}
