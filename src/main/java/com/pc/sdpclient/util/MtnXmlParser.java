package com.pc.sdpclient.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.pc.sdpclient.model.Status;
import com.pc.sdpclient.model.ussd.Abort;
import com.pc.sdpclient.model.ussd.Ussd;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class MtnXmlParser {

    public static Status parseMtnResponse(String xml, String mainTag, String subTag) {
        if (Objects.isNull(xml) || xml.isEmpty()) {
            return new Status(false, "Invalid xml");
        } else {
            JSONObject bodyJson = extractXmlBodyAsJson(xml);
            JSONObject subscribeProductResponseJson = bodyJson.getJSONObject(mainTag);
            JSONObject subscribeProductJson = subscribeProductResponseJson.getJSONObject(subTag);
            int result = subscribeProductJson.getInt("result");
            String description = subscribeProductJson.getString("resultDescription");

            if (result == 0) {
                return new Status(true, description, result);
            } else {
                return new Status(false, description, result);
            }
        }
    }

    private static JSONObject extractXmlBodyAsJson(String xml) {
        JSONObject jsonObject = FileUtil.xmlToJson(xml);
        JSONObject envelopeJson = jsonObject.getJSONObject("soapenv:Envelope");
        JSONObject bodyJson = envelopeJson.getJSONObject("soapenv:Body");
        return bodyJson;
    }


    private static JSONObject extractXmlHeaderAsJson(String xml) {
        JSONObject jsonObject = FileUtil.xmlToJson(xml);
        System.out.println(jsonObject);
        JSONObject envelopeJson = jsonObject.getJSONObject("soapenv:Envelope");
        JSONObject bodyJson = envelopeJson.getJSONObject("soapenv:Header");
        return bodyJson;
    }

    public static Status parseSubscribe(String xml) {
        return parseMtnResponse(xml, "ns1:subscribeProductResponse", "ns1:subscribeProductRsp");
    }

    public static Status parseUnsubscribe(String xml) {
        return parseMtnResponse(xml, "ns1:unSubscribeProductResponse", "ns1:unSubscribeProductRsp");
    }

    public static Status parseMtnFaultXml(String xml) {
        if (Objects.isNull(xml) || xml.isEmpty()) {
            return new Status(false, "Invalid xml");
        } else {
            JSONObject bodyJson = extractXmlBodyAsJson(xml);
            JSONObject faultJson = bodyJson.getJSONObject("soapenv:Fault");
            String code = faultJson.getString("faultcode");
            String description = faultJson.getString("faultstring");
            return new Status(false, description, code);
        }
    }

    public static Status parseMtnChargeXml(String xml) {
        JSONObject bodyJson = extractXmlBodyAsJson(xml);
        JSONObject jsonObject = bodyJson.getJSONObject("ns1:chargeAmountResponse");
        if (Objects.nonNull(jsonObject)) {
            return new Status(true, "Success");
        } else {
            return new Status(false, "Not a charging response");
        }
    }

    public static Status parseMtnSendSms(String xml) {
        JSONObject bodyJson = extractXmlBodyAsJson(xml);
        JSONObject jsonObject = bodyJson.getJSONObject("ns1:sendSmsResponse");
        if (Objects.nonNull(jsonObject)) {
            String transactionReference = jsonObject.getString("ns1:result");
            return new Status(true, "Success", transactionReference);
        } else {
            return new Status(false, "Not a charging response");
        }
    }

    public static Status parseMtnSendUssd(String xml) {
        JsonNode bodyNode = null;
        try {
            JsonNode jsonNode = JsonUtil.getJsonMapper().readValue(JsonUtil.xmlToJson(xml), JsonNode.class);
            bodyNode = jsonNode.get("Body");
            Integer result = bodyNode.get("sendUssdResponse").get("result").asInt();
            return new Status(true, "Success", result);
        } catch (NullPointerException npe) {
            String text = bodyNode.get("Fault").get("faultstring").asText();
            return new Status(false, text);
        } catch (IOException ioe) {
            return new Status(false, ioe.getMessage());
        }
    }

    public static Status parseMtnAbortUssd(String xml){
        JsonNode bodyNode = null;
        try {
            JsonNode jsonNode = JsonUtil.getJsonMapper().readValue(JsonUtil.xmlToJson(xml), JsonNode.class);
            bodyNode = jsonNode.get("Body");
            JsonNode sendAbort = bodyNode.get("sendUssdAbortResponse");
            return new Status(true, "Success", null);
        } catch (NullPointerException npe) {
            String text = bodyNode.get("Fault").get("faultstring").asText();
            return new Status(false, text);
        } catch (IOException ioe) {
            return new Status(false, ioe.getMessage());
        }
    }

    public static Status parseMtnStartUssdStartNotification(String xml) {
        JSONObject bodyJson = extractXmlBodyAsJson(xml);
        try {
            JSONObject jsonObject = bodyJson.getJSONObject("ns1:startUSSDNotificationResponse");
            if (Objects.nonNull(jsonObject)) {
                return new Status(true, "Success", xml);
            } else {
                return new Status(false, bodyJson.getJSONObject("soapenv:Fault").getString("faultstring"));
            }
        } catch (JSONException je) {
            return new Status(false, bodyJson.getJSONObject("soapenv:Fault").getString("faultstring"));
        }

    }

    public static Status parseMtnUssdNotificationReception(String xml) {
        Ussd ussd = null;

        try {
            ussd = JsonUtil.getJsonMapper().readValue(JsonUtil.xmlToJson(xml), Ussd.class);
            return new Status(true, "Success", ussd);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
            return new Status(false, jme.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return new Status(false, ioe.getMessage());
        }
    }

    public static String getMtnUssdNotificationReceptionResponse() {
        String xml = FileUtil.loadXmlFile("xml/mtn-notify-ussd-reception-response.xml");
        return xml;
    }

    public static Status parseMtnUssdNotifyAbortion(String xml) {
        Abort abort;

        try {
            System.out.println(xml);
            abort = JsonUtil.getJsonMapper().readValue(JsonUtil.xmlToJson(xml), Abort.class);
            return new Status(true, "Success", abort);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
            return new Status(false, jme.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return new Status(false, ioe.getMessage());
        }
    }

    public static String getMtnUssdNotifyAbortResponse() {
        String xml = FileUtil.loadXmlFile("xml/mtn-notify-ussd-abort-response.xml");
        return xml;
    }

    public static void main(String args []){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Header><ns1:NotifySOAPHeader xmlns:ns1=\"http://www.huawei.com.cn/schema/common/v2_1\"><ns1:spId>2340110005999</ns1:spId><ns1:serviceId>234012000023788</ns1:serviceId><ns1:timeStamp>20190313131202</ns1:timeStamp><ns1:traceUniqueID>100201200101190313131202430801</ns1:traceUniqueID></ns1:NotifySOAPHeader></soapenv:Header><soapenv:Body><ns2:notifyUssdAbort xmlns:ns2=\"http://www.csapi.org/schema/parlayx/ussd/notification/v1_0/local\"><ns2:senderCB>17960189</ns2:senderCB><ns2:receiveCB>17960189</ns2:receiveCB><ns2:abortReason>Session Timeout.</ns2:abortReason></ns2:notifyUssdAbort></soapenv:Body></soapenv:Envelope>";

        Status status = parseMtnAbortUssd(xml);
        System.out.println(status);
        Abort abort = (Abort) status.getData();
        System.out.println(abort);
    }

}
