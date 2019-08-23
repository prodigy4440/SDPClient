package com.fahdisa.sdpclient.model.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class AuthResponse implements Serializable {

    private String code;

    private String description;

    private String tokenStatus;
            private String accessToken;
    private String tokenValidity;
            private Integer totalAmount;

    public AuthResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponse that = (AuthResponse) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(tokenStatus, that.tokenStatus) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(tokenValidity, that.tokenValidity) &&
                Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, tokenStatus, accessToken, tokenValidity, totalAmount);
    }


    @JsonProperty("Header")
    public void unpackHeader(Map<String, Object> header) {

    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> body) {
        Map<String, Object> queryAuthorizationResponse = (Map)body.get("queryAuthorizationResponse");
        Map<String, Object> result = (Map<String, Object>) queryAuthorizationResponse.get("result");
        this.code = (String)result.get("resultCode");
        this.description = (String)result.get("resultDescription");
        Map<String, Object> authorizationInfo = (Map<String, Object>) queryAuthorizationResponse.get("authorizationInfo");
        this.tokenStatus = (String)authorizationInfo.getOrDefault("accessTokenStatus", "");
        this.accessToken = (String)authorizationInfo.get("accessToken");
        this.tokenValidity = (String)authorizationInfo.get("tokenValidity");
        this.totalAmount = Integer.parseInt((String)authorizationInfo.get("totalAmount"));
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", tokenStatus='" + tokenStatus + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", tokenValidity='" + tokenValidity + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
