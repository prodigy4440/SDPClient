package com.pc.sdpclient.config;

import okhttp3.HttpUrl;

import java.util.Objects;

public class UrlConfig {

    private String schema;
    private String host;
    private int port;

    private UrlConfig() {
        this.schema = "http";
        this.host = "41.206.4.162";
        this.port = 8310;
    }

    private UrlConfig(String host, int port) {
        this();
        if (Objects.nonNull(host) && (!host.isEmpty())){
            this.host = host;
        }
        if (Objects.nonNull(port) && (port > 0)) {
            this.port = port;
        }
    }

    private UrlConfig(String schema, String host, int port) {
        this(host, port);
        if (Objects.nonNull(schema) && (!schema.isEmpty())){
            this.schema = schema;
        }
    }

    public String getSchema(){
        return this.schema;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public HttpUrl getChargeUrl(){
        return new HttpUrl.Builder()
                .scheme(getSchema())
                .host(getHost())
                .port(getPort())
                .addPathSegment("AmountChargingService")
                .addPathSegment("services")
                .addPathSegment("AmountCharging")
                .build();
    }

    public HttpUrl getSubscribe(){
        return new HttpUrl.Builder()
                .scheme(getSchema())
                .host(getHost())
                .port(getPort())
                .addPathSegment("SubscribeManageService")
                .addPathSegment("services")
                .addPathSegment("SubscribeManage")
                .build();
    }

    public HttpUrl getSendSms(){
        return new HttpUrl.Builder()
                .scheme(getSchema())
                .host(getHost())
                .port(getPort())
                .addPathSegment("SendSmsService")
                .addPathSegment("services")
                .addPathSegment("SendSms")
                .build();
    }

    public HttpUrl getSendUssd(){
        return new HttpUrl.Builder()
                .scheme(getSchema())
                .host(getHost())
                .port(getPort())
                .addPathSegment("SendUssdService")
                .addPathSegment("services")
                .addPathSegment("SendUssd")
                .build();
    }

    public HttpUrl getStartUssd(){
        return new HttpUrl.Builder()
                .scheme(getSchema())
                .host(getHost())
                .port(getPort())
                .addPathSegment("USSDNotificationManagerService")
                .addPathSegment("services")
                .addPathSegment("USSDNotificationManager")
                .build();
    }

    public HttpUrl getAuthorization(){
        return new HttpUrl.Builder()
                .scheme(getSchema())
                .host(getHost())
                .port(getPort())
                .addPathSegment("authorizationService")
                .addPathSegment("services")
                .addPathSegment("authorization")
                .build();
    }

    public static class Builder {
        private String schema;
        private String host;
        private int port;

        public Builder setSchema(String schema) {
            this.schema = schema;
            return this;
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(Integer port) {
            this.port = port;
            return this;
        }

        public UrlConfig build() {
            return new UrlConfig(this.schema, this.host, this.port);
        }
    }

    @Override
    public String toString() {
        return "UrlConfig{" + "schema='" + schema + '\'' + ", host='"
                + host + '\'' + ", port=" + port + '}';
    }
}
