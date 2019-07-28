package com.fahdisa.sdpclient.model.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class UnsubResponse implements Serializable {

    private Integer code;
    private String description;

    public UnsubResponse(){
    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> bodyMap){
        Map<String, Object> productRespMap = (Map<String, Object>)((Map<String, Object>)
                bodyMap.get("unSubscribeProductResponse")).get("unSubscribeProductRsp");
        this.code = Integer.parseInt(String.valueOf(productRespMap.get("result")));
        this.description = String.valueOf(productRespMap.get("resultDescription"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnsubResponse)) return false;
        UnsubResponse that = (UnsubResponse) o;
        return Objects.equals(getCode(), that.getCode()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getDescription());
    }

    @Override
    public String toString() {
        return "UnsubResponse{" + "code=" + code +
                ", description='" + description + '\'' + '}';
    }
}
