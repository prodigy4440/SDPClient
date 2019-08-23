package com.fahdisa.sdpclient.model.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AuthQueryList implements Serializable {

    private Integer code;
    private String description;

    private List<QueryInfo> queryInfos;

    @JsonProperty("Header")
    public void unpackHeader(Map<String, Object> headerMap){
    }

    @JsonProperty("Body")
    public void unpackBody(Map<String, Object> bodyMap){
        Map<String, Object> queryAuthorizationListResponse = (Map<String, Object>) bodyMap.get("queryAuthorizationListResponse");
        Map<String, Object> resultMap = (Map<String, Object>)queryAuthorizationListResponse.get("result");
        code = Integer.parseInt((String)resultMap.get("resultCode"));
        description = (String) resultMap.get("resultDescription");
        if(Objects.nonNull(queryAuthorizationListResponse.get("authorizationInfoList"))){
            Map<String, Object> authorizationInfoMap = (Map<String, Object>) queryAuthorizationListResponse.get("authorizationInfoList");
            List<Map<String, Object>> authInfos = (List)authorizationInfoMap.get("detailedAuthorizationInfo");
            if(Objects.isNull(queryInfos)){
                queryInfos = new LinkedList<>();

                for (Map<String, Object> authInfo :authInfos){
                    QueryInfo queryInfo = new QueryInfo();
                    queryInfo.setAccessToken((String)authInfo.get("accessToken"));
                    queryInfo.setAccessTokenStatus(Integer.parseInt((String)authInfo.get("accessTokenStatus")));
                    queryInfo.setAmount(Integer.parseInt((String)authInfo.get("amount")));
                    queryInfo.setCurrency((String)authInfo.get("currency"));
                    queryInfo.setProductId((String)authInfo.get("productId"));
                    queryInfo.setProductName((String)authInfo.get("productName"));
                    queryInfo.setServiceInterval(Integer.parseInt((String)authInfo.get("serviceInterval")));
                    queryInfo.setServiceIntervalUnit(Integer.parseInt((String)authInfo.get("serviceIntervalUnit")));
                    queryInfo.setTokenValidity((String)authInfo.get("tokenValidity"));
                    queryInfo.setTransactionId((String)authInfo.get("transactionId"));
                    queryInfos.add(queryInfo);
                }
            }
        }
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

    public List<QueryInfo> getQueryInfos() {
        return queryInfos;
    }

    public void setQueryInfos(List<QueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthQueryList that = (AuthQueryList) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(queryInfos, that.queryInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, queryInfos);
    }

    @Override
    public String toString() {
        return "AuthQueryList{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", queryInfos=" + queryInfos +
                '}';
    }
}
