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
//        Status status = chargePhone(Constant.SPID, Constant.SPPASSWORD,
//                Constant.TIMESTAMP, Constant.ACADA_STANDARD_PLAN_SERVICE_ID,
//                Constant.ACADA_STANDARD_PLAN_PRODUCT_ID, "2348131631151");
//        System.out.println(status);
//        String ibro = "2347031900599";
//        Status status1 = subscribePhone(Constant.SPID, Constant.SPPASSWORD, Constant.TIMESTAMP, Constant.ACADA_WEEKLY_PLAN_PRODUCT_ID, "2348131631151");
//        System.out.println(status1);
//        Status status2 = unsubscribePhone(Constant.SPID, Constant.SPPASSWORD, Constant.TIMESTAMP, Constant.ACADA_WEEKLY_PLAN_PRODUCT_ID, "2348131631151");
//        System.out.println(status2);
//        Status smsStatus = sendSms(Constant.SPID, Constant.SPPASSWORD, "", Constant.ACADA_SMS_SERVICE_ID, Constant.TIMESTAMP, "2348138075679",
//                "2348138075679", "", "", "162", "2348166721550", "Sample message",
//                "http://154.113.0.202:8999/digitalpulse/api/v1.0/mtn/notify/sms",
//                "744550456547");

//        sendUssd("2340110005999","ps_password","bundleid","timestamp",
//                "oa","fa",0,"sendercb","receivercb",
//                1,"2348131631151","55019","68",
//                "Hello World");
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


}
