/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pc.sdpclient.util;

import okhttp3.HttpUrl;

/**
 *
 * @author prodigy4440
 */
public class MtnUrl {
    
    private static final String SCHEMA = "http";
    private static final String HOST = "41.206.4.162";
    private static final Integer PORT = 8310;
    
    public static HttpUrl CHARGE(){
        return new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(HOST)
                .port(PORT)
                .addPathSegment("AmountChargingService")
                .addPathSegment("services")
                .addPathSegment("AmountCharging")
                .build();
    }
    
    public static HttpUrl SUBSCRIBE(){
                return new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(HOST)
                .port(PORT)
                .addPathSegment("SubscribeManageService")
                .addPathSegment("services")
                .addPathSegment("SubscribeManage")
                .build();
    }
    
    public static HttpUrl SEND_SMS(){
                return new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(HOST)
                .port(PORT)
                .addPathSegment("SendSmsService")
                .addPathSegment("services")
                .addPathSegment("SendSms")
                .build();
    }

    public static HttpUrl SEND_USSD(){
                return new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(HOST)
                .port(PORT)
                .addPathSegment("SendUssdService")
                .addPathSegment("services")
                .addPathSegment("SendUssd")
                .build();
    }

    public static HttpUrl START_USSD(){
        return new HttpUrl.Builder()
                .scheme(SCHEMA)
                .host(HOST)
                .port(PORT)
                .addPathSegment("USSDNotificationManagerService")
                .addPathSegment("services")
                .addPathSegment("USSDNotificationManager")
                .build();
    }
    
}
