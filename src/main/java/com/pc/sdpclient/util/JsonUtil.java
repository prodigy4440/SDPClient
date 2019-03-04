package com.pc.sdpclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class JsonUtil {

    private static XmlMapper XML_MAPPER = new XmlMapper();
    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static ObjectMapper getJsonMapper(){
        return JSON_MAPPER;
    }

    public static XmlMapper getXmlMapper(){
        return XML_MAPPER;
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
