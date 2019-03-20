/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pc.sdpclient;

import com.pc.sdpclient.model.Status;
import com.pc.sdpclient.util.*;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author prodigy4440
 */
public class SDPClient {

    public static void main(String[] args) {

        sendAuthorizationRequest("spid","sppassword","time",
                "serviceid", "2348131631151","transaction",
                79,5000,"NGN","Jamb Digital Service");

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
                                       Integer amount, String currency, String description){
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
                .replaceAll("service_description",description);

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


}
