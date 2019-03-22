/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pc.sdpclient;

import com.pc.sdpclient.config.ServiceConfig;
import com.pc.sdpclient.config.UrlConfig;
import com.pc.sdpclient.model.Status;
import com.pc.sdpclient.network.SdpConnector;
import com.pc.sdpclient.parser.MtnXmlParser;
import com.pc.sdpclient.util.*;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author prodigy4440
 */
public class SDPClient {

    private static Logger logger = LoggerFactory.getLogger(SDPClient.class);

    public static void main(String[] args) {
//
//        String transactionId = TransactionUtil.generateTransactionId(MtnUrl.IP(),1,1);
//
//        logger.info("Transaction ID: {}", transactionId);
        UrlConfig urlConfig = new UrlConfig.Builder()
                .build();

        ServiceConfig jambUssdService = new ServiceConfig.Builder()
                .setSpId("2340110005999")
                .setSpPassword("F0E139532F43210A1DB9077C4B0FD06E")
                .setTimestamp("20190313095640")
                .setServiceId("234012000023788")
                .setProductId("23401220000027529")
                .setUssdServiceActivationNumber("*55019")
                .setCorrelator("234012000023788")
                .setEndpoint("http://154.113.0.202:8087/api/v1.0/ussd/notify")
                .build();

        ServiceConfig acadaStandardService = new ServiceConfig.Builder()
                .setSpId("2340110005999")
                .setSpPassword("ebe4fdda369ef289cf77210f6e0fff9b")
                .setTimestamp("20170110154245")
                .setOa("2348138075679")
                .setFa("2348138075679")
                .setServiceId("234012000023028")
                .setProductId("23401220000026651")
                .build();

        ServiceConfig acadaOnDemandService = new ServiceConfig.Builder()
                .setSpId("2340110005999")
                .setSpPassword("ebe4fdda369ef289cf77210f6e0fff9b")
                .setTimestamp("20170110154245")
                .setOa("2348138075679")
                .setFa("2348138075679")
                .setServiceId("234012000023027")
                .setProductId("23401220000026650")
                .build();

        ServiceConfig acadaSmsService = new ServiceConfig.Builder()
                .setSpId("2340110005999")
                .setSpPassword("ebe4fdda369ef289cf77210f6e0fff9b")
                .setTimestamp("20170110154245")
                .setOa("2348138075679")
                .setFa("2348138075679")
                .setServiceId("234012000023327")
                .setProductId("23401220000026940")
                .build();

        Integrator jambUssdIntegrator = new Integrator.Builder()
                .addUrl(urlConfig)
                .addService(jambUssdService)
                .build();

        Integrator acadaStandardIntegrator = new Integrator.Builder()
                .addUrl(urlConfig)
                .addService(acadaStandardService)
                .build();

        Integrator acadaOnDemandIntegrator = new Integrator.Builder()
                .addUrl(urlConfig)
                .addService(acadaOnDemandService)
                .build();

        Integrator acadaSmsIntegrator = new Integrator.Builder()
                .addUrl(urlConfig)
                .addService(acadaSmsService)
                .build();

        String phoneNumber = "2348131631151";

        Status status = acadaStandardIntegrator.unsubscribePhone(phoneNumber);
//        Status status = acadaOnDemandIntegrator.unsubscribePhone(phoneNumber);
//        Status status = acadaOnDemandIntegrator.chargePhone("2348131631151", 100);


        logger.info("{}", status);

//        Integrator integrator = new Integrator.Builder().addUrl(urlConfig).addService(serviceConfig).build();
//
//        sendAuthorizationRequest(urlConfig,serviceConfig,
//                "2348131631151",transactionId,
//                17,5000,"NGN","Jamb Digital Service", 1);

    }

    public static Status chargePhone(String spId, String spPassword, String timeStamp, String serviceId, String productId,
                                     String phoneNumber, Integer amount) {
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-charge.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password", spPassword)
                .replaceAll("time_stamp", timeStamp)
                .replaceAll("product_id", productId)
                .replaceAll("service_id", serviceId)
                .replaceAll("charge_amount", String.valueOf(amount))
                .replaceAll("end_user_phone_number", phoneNumber);

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.CHARGE())
                .post(requestBody).build();
        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            if (response.code() == 200) {
                return MtnXmlParser.parseMtnChargeXml(response.body().string());
            } else if (response.code() == 500) {
                return MtnXmlParser.parseMtnFaultXml(response.body().string());
            } else {
                String xmlResponse = response.body().string();
                return new Status<>(false, "Error", xmlResponse);
            }
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status subscribePhone(String spId, String spPassword, String timeStamp, String productId,
                                        String phoneNumber) {
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-subscribe.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password", spPassword)
                .replaceAll("time_stamp", timeStamp)
                .replaceAll("product_id", productId)
                .replaceAll("end_user_phone_number", phoneNumber);
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.SUBSCRIBE())
                .post(requestBody).build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            return MtnXmlParser.parseMtnResponse(xmlResponse, "ns1:subscribeProductResponse", "ns1:subscribeProductRsp");
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status unsubscribePhone(String spId, String spPassword, String timeStamp, String productId,
                                          String phoneNumber) {
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-unsubscribe.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password", spPassword)
                .replaceAll("time_stamp", timeStamp)
                .replaceAll("product_id", productId)
                .replaceAll("end_user_phone_number", phoneNumber);
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.SUBSCRIBE())
                .post(requestBody).build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            return MtnXmlParser.parseMtnResponse(xmlResponse, "ns1:unSubscribeProductResponse", "ns1:unSubscribeProductRsp");
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status sendSms(String spId, String spPassword, String bundleId, String serviceId, String timeStamp,
                               String oa, String fa, String linkId, String presentId,String sender, String phoneNumber,
                                 String sms,
                               String notifyUrl, String correlator){

        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-send-sms.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password",spPassword)
                .replaceAll("bundle_id",bundleId)
                .replaceAll("service_id",serviceId)
                .replaceAll("time_stamp",timeStamp)
                .replaceAll("o_a",oa)
                .replaceAll("fake_a",fa)
                .replaceAll("link_id",linkId)
                .replaceAll("present_id", presentId)
                .replaceAll("end_user_phonenumber", phoneNumber)
                .replaceAll("sender_name", sender)
                .replaceAll("sms_message", sms)
                .replaceAll("notify_url",notifyUrl)
                .replaceAll("correlator_ref", correlator);

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.SEND_SMS())
                .post(requestBody).build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            Status status = MtnXmlParser.parseMtnSendSms(xmlResponse);
            return status;
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status startUssdNotification(String spId, String spPassword, String serviceId, String timeStamp,
                                 String notifyUrl, String activationNumber, String correlator){

        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-start-ussd-notification.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password",spPassword)
                .replaceAll("service_id",serviceId)
                .replaceAll("time_stamp",timeStamp)
                .replaceAll("notify_url_end_point",notifyUrl)
                .replaceAll("ussd_service_activation_number",activationNumber)
                .replaceAll("correlator_ref", correlator);

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.START_USSD())
                .post(requestBody)
                .addHeader("SOAPAction","")
                .build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            Status status = MtnXmlParser.parseMtnStartUssdStartNotification(xmlResponse);
            return status;
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status stopUssdNotification(String spId, String spPassword, String serviceId, String timeStamp,
                                               String correlator){

        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-stop-ussd-notification.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password",spPassword)
                .replaceAll("service_id",serviceId)
                .replaceAll("time_stamp",timeStamp)
                .replaceAll("correlator_ref", correlator);

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.START_USSD())
                .post(requestBody)
                .addHeader("SOAPAction","")
                .build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            Status status = MtnXmlParser.parseMtnStartUssdStartNotification(xmlResponse);
            return status;
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status sendUssd(String spId, String spPassword, String serviceId, String bundleId, String timestamp,
                                  String oa, String fa, Integer msgType, String senderCb,
                                  String receiverCb, Integer msgOpType, String msisdn, String serviceCode,
                                  String codeScheme, String ussdString){

        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-send-ussd-request.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password",spPassword)
                .replaceAll("bundle_id",bundleId)
                .replaceAll("time_stamp",timestamp)
                .replaceAll("service_id", serviceId)
                .replaceAll("fake_oa",oa)
                .replaceAll("fake_fa",fa)
                .replaceAll("msg_type", String.valueOf(msgType))
                .replaceAll("sender_cb", senderCb)
                .replaceAll("receive_cb", receiverCb)
                .replaceAll("ussd_op_type", String.valueOf(msgOpType))
                .replaceAll("user_phone_number", msisdn)
                .replaceAll("service_code", serviceCode)
                .replaceAll("code_scheme", codeScheme)
                .replaceAll("ussd_string",ussdString);

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.SEND_USSD())
                .post(requestBody).build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            Status status = MtnXmlParser.parseMtnSendUssd(xmlResponse);
            return status;
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status sendUssdAbort(String spId, String spPassword, String serviceId,
                                       String timestamp, String oa, String fa,
                                       String senderCb, String receiverCb, String abortReason){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-send-ussd-abort-request.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password",spPassword)
                .replaceAll("service_id",serviceId)
                .replaceAll("time_stamp",timestamp)
                .replaceAll("fake_oa",oa)
                .replaceAll("fake_fa",fa)
                .replaceAll("sender_cb", senderCb)
                .replaceAll("receive_cb", receiverCb)
                .replaceAll("abort_reason", abortReason);
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.SEND_USSD())
                .post(requestBody).build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            Status status = MtnXmlParser.parseMtnAbortUssd(xmlResponse);
            return status;
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }


    public static Status sendAuthorizationRequest(String spId, String spPassword, String timestamp,
                                                  String serviceId, String phoneNumber,
                                                  String transactionId, Integer scope,
                                       Integer amount, String currency, String description,
                                                  Integer frequency){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-authorization-request.xml")
                .replaceAll("sp_id", spId)
                .replaceAll("sp_password",spPassword)
                .replaceAll("time_stamp",timestamp)
                .replaceAll("end_user_identifier",phoneNumber)
                .replaceAll("transaction_id",transactionId)
                .replaceAll("auth_scope",String.valueOf(scope))
                .replaceAll("service_id",serviceId)
                .replaceAll("service_amount",String.valueOf(amount))
                .replaceAll("country_currency",currency)
                .replaceAll("service_description",description)
                .replaceAll("auth_frequency", String.valueOf(frequency));

        System.out.println(xmlRequest);
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xmlRequest);

        Request request = new Request.Builder()
                .url(MtnUrl.AUTHORIZATION())
                .post(requestBody).build();

        try {
            Response response = OkHttpUtil.getHttpClient().newCall(request).execute();
            String xmlResponse = response.body().string();
            System.out.println(xmlResponse);
            Status status = MtnXmlParser.parserMtnAuthorizationRequest(xmlResponse);
            return status;
        } catch (IOException e) {
            return new Status(false, e.getMessage());
        }
    }

    public static Status sendAuthorizationRequest(UrlConfig urlConfig, ServiceConfig serviceConfig, String phoneNumber,
                                                  String transactionId, Integer scope,
                                                  Integer amount, String currency, String description,
                                                  Integer frequency){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-authorization-request.xml")
                .replaceAll("sp_id", serviceConfig.getSpId())
                .replaceAll("sp_password",serviceConfig.getSpPassword())
                .replaceAll("time_stamp",serviceConfig.getTimestamp())
                .replaceAll("end_user_identifier",phoneNumber)
                .replaceAll("transaction_id",transactionId)
                .replaceAll("auth_scope",String.valueOf(scope))
                .replaceAll("service_id",serviceConfig.getServiceId())
                .replaceAll("service_amount",String.valueOf(amount))
                .replaceAll("country_currency",currency)
                .replaceAll("service_description",description)
                .replaceAll("auth_frequency", String.valueOf(frequency));
        Status<String> postStatus = SdpConnector.post(urlConfig.getAuthorization(), xmlRequest);
        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            return MtnXmlParser.parserMtnAuthorizationRequest(xmlResponse);
        }else{
            return postStatus;
        }
    }


}
