package com.fahdisa.sdpclient.model.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class AuthRequest implements Serializable {

    private Integer resultCode;
    private String description;
    
    private String token;
    private String tokenExpiryTime;

    @JsonProperty("Header")
    public void unpackHeader(Map<String, Object> headerMap){
    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> bodyMap){
        Map<String, Object> authorizationResponse = (Map<String, Object>) bodyMap.get("authorizationResponse");
       if(Objects.nonNull(authorizationResponse.get("result"))){
            Map<String, Object> propertyMap = (Map<String, Object>) authorizationResponse.get("result");
           if(Objects.nonNull(propertyMap.get("resultCode"))){
               this.resultCode = Integer.parseInt((String)propertyMap.get("resultCode"));
           }
           if(Objects.nonNull(propertyMap.get("resultDescription"))){
               this.description = String.valueOf(propertyMap.get("resultDescription"));
           }
        }

        if(Objects.nonNull(authorizationResponse.get("token"))){
            this.token = String.valueOf(authorizationResponse.get("token"));
        }
        if(Objects.nonNull(authorizationResponse.get("extensionInfo"))){
            Map<String, Object> extensionInfo = (Map<String, Object>)authorizationResponse.get("extensionInfo");
            if(Objects.nonNull(extensionInfo.get("item"))){
                if(Objects.nonNull(extensionInfo.get("key"))){
                    String key  = (String) extensionInfo.get("key");
                    String value = (String) extensionInfo.get("value");
                    if(key.equalsIgnoreCase("tokenExpiryTime")){
                        tokenExpiryTime = value;
                    }
                }
            }
        }
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthRequest)) return false;
        AuthRequest that = (AuthRequest) o;
        return Objects.equals(getResultCode(), that.getResultCode()) && Objects.equals(getDescription(),
                that.getDescription()) && Objects.equals(getToken(), that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResultCode(), getDescription(), getToken());
    }

    @Override
    public String toString() {
        return "AuthRequest{" + "code=" + resultCode + ", description='" + description
                + '\'' + ", token='" + token + '\'' + '}';
    }
}
