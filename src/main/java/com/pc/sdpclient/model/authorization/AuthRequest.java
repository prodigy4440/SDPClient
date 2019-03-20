package com.pc.sdpclient.model.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class AuthRequest implements Serializable {

    private Integer code;
    private String description;
    private String token;

    @JsonProperty("Header")
    public void unpackHeader(Map<String, Object> headerMap){
    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> bodyMap){
        Map<String, Object> authorizationResponse = (Map<String, Object>) bodyMap.get("authorizationResponse");
        Map<String, Object> propertyMap = (Map<String, Object>) authorizationResponse.get("result");

        this.code = Integer.parseInt((String)propertyMap.get("resultCode"));
        this.description = String.valueOf(propertyMap.get("resultDescription"));
        this.token = String.valueOf(propertyMap.get("token"));
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
        return Objects.equals(getCode(), that.getCode()) && Objects.equals(getDescription(),
                that.getDescription()) && Objects.equals(getToken(), that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getDescription(), getToken());
    }

    @Override
    public String toString() {
        return "AuthRequest{" + "code=" + code + ", description='" + description
                + '\'' + ", token='" + token + '\'' + '}';
    }
}
