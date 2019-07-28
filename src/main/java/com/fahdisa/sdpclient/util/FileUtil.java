/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fahdisa.sdpclient.util;

import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 *
 * @author prodigy4440
 */
public class FileUtil {

    public static String loadXmlFile(String fileName){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String collect = br.lines().collect(Collectors.joining("\n"));
        return collect;
    }
    
    public static String convertToString(InputStream is){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        return bufferedReader.lines().collect(Collectors.joining("\n"));
    }

    public static JSONObject xmlToJson(String xml){
        return XML.toJSONObject(xml);
    }
    
}
