package com.pc.sdpclient.model.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class SubResponse implements Serializable {

    private String code;
    private String description;

    public SubResponse(){
    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> bodyMap){
        Map<String, Object> productRespMap = (Map<String, Object>)((Map<String, Object>)
                bodyMap.get("subscribeProductResponse")).get("subscribeProductRsp");
        this. code = String.valueOf(productRespMap.get("result"));
        this.description = String.valueOf(productRespMap.get("resultDescription"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubResponse)) return false;
        SubResponse that = (SubResponse) o;
        return Objects.equals(getCode(), that.getCode()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getDescription());
    }

    @Override
    public String toString() {
        return "SubResponse{" + "code='" + code + '\'' + ", description='" + description + '\'' + '}';
    }
}
