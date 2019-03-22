package com.pc.sdpclient;

import com.pc.sdpclient.config.ServiceConfig;
import com.pc.sdpclient.config.UrlConfig;
import com.pc.sdpclient.model.Status;
import com.pc.sdpclient.network.SdpConnector;
import com.pc.sdpclient.parser.MtnXmlParser;
import com.pc.sdpclient.util.FileUtil;
import com.pc.sdpclient.util.MtnUrl;
import com.pc.sdpclient.util.OkHttpUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class Integrator {

    private UrlConfig urlConfig;

    private ServiceConfig serviceConfig;

    private Integrator(UrlConfig urlConfig, ServiceConfig serviceConfig) {
        this.urlConfig = urlConfig;
        this.serviceConfig = serviceConfig;
    }


    public Status chargePhone(String phoneNumber, Integer amount) {
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-charge.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password", getServiceConfig().getSpPassword())
                .replaceAll("time_stamp", getServiceConfig().getTimestamp())
                .replaceAll("product_id", getServiceConfig().getProductId())
                .replaceAll("service_id", getServiceConfig().getServiceId())
                .replaceAll("charge_amount", String.valueOf(amount))
                .replaceAll("end_user_phone_number", phoneNumber);

        Status<String> postStatus = SdpConnector.post(getUrlConfig().getChargeUrl(), xmlRequest);

        if(postStatus.getStatus()){
            String resp = postStatus.getData();
            if(resp.contains("faultstring")){
                return MtnXmlParser.parseFault(resp);
            }else{
                return MtnXmlParser.parseMtnChargeXml(postStatus.getData());
            }
        }else{
            return postStatus;
        }
    }

    public Status subscribePhone(String phoneNumber) {
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-subscribe.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password", getServiceConfig().getSpPassword())
                .replaceAll("time_stamp", getServiceConfig().getTimestamp())
                .replaceAll("product_id", getServiceConfig().getProductId())
                .replaceAll("end_user_phone_number", phoneNumber);


        Status<String> postStatus = SdpConnector.post(getUrlConfig().getSubscribe(), xmlRequest);


        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            System.out.println(xmlResponse);
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                return MtnXmlParser.parseMtnSubscribeResponse(xmlResponse);
            }
        }else{
            return postStatus;
        }
    }

    private UrlConfig getUrlConfig(){
        return this.urlConfig;
    }

    private ServiceConfig getServiceConfig(){
        return this.serviceConfig;
    }

    public static class Builder{

        private UrlConfig urlConfig;
        private ServiceConfig serviceConfig;

        public Builder addUrl(UrlConfig urlConfig){
            this.urlConfig = urlConfig;
            return this;
        }

        public Builder addService(ServiceConfig serviceConfig){
            this.serviceConfig = serviceConfig;
            return this;
        }

        public Integrator build(){
            return new Integrator(this.urlConfig, this.serviceConfig);
        }
    }
}
