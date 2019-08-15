package com.fahdisa.sdpclient.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.Objects;

public class JsonUtil<T> {

    private static XmlMapper XML_MAPPER = new XmlMapper();
    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static ObjectMapper getJsonMapper(){
        return JSON_MAPPER;
    }

    public static XmlMapper getXmlMapper(){
        return XML_MAPPER;
    }

    public static <T> T xmlToPojoViaJson(String xml, Class<T> type) throws IOException {
        String json = xmlToJson(xml);
        if(Objects.isNull(json) || json.isEmpty()){
            throw new IOException("Invalid json");
        }
        return JsonUtil.getJsonMapper().readValue(json, type);
    }

    public static String xmlToJson(String xml){
        try {
            return getJsonMapper().writeValueAsString(getXmlMapper().readTree(xml));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
