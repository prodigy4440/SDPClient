package com.pc.sdpclient.parser;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.pc.sdpclient.model.Fault;
import com.pc.sdpclient.model.Status;
import com.pc.sdpclient.model.authorization.AuthRequest;
import com.pc.sdpclient.model.subscription.SubResponse;
import com.pc.sdpclient.model.subscription.UnsubResponse;
import com.pc.sdpclient.model.ussd.Abort;
import com.pc.sdpclient.model.ussd.Ussd;
import com.pc.sdpclient.util.FileUtil;
import com.pc.sdpclient.util.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class MtnXmlParser {

    private static Logger logger = LoggerFactory.getLogger(MtnXmlParser.class);

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

    public static Status parseMtnSubscribeResponse(String xml){
        String json = JsonUtil.xmlToJson(xml);
        try {
            SubResponse subResponse = JsonUtil.getJsonMapper().readValue(json, SubResponse.class);
            return new Status(true, "Success", subResponse);
        } catch (IOException e) {
            logger.error("IOException",e);
            return new Status(false, e.getMessage());
        }
    }

    public static Status parseMtnUnsubscribeResponse(String xml){
        String json = JsonUtil.xmlToJson(xml);
        try {
            UnsubResponse unsubResponse = JsonUtil.getJsonMapper().readValue(json, UnsubResponse.class);
            return new Status(true, "Success", unsubResponse);
        } catch (IOException e) {
            logger.error("IOException",e);
            return new Status(false, e.getMessage());
        }
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

    public static Status<Abort> parseMtnUssdNotifyAbortion(String xml) {
        Abort abort;
        String json = JsonUtil.xmlToJson(xml);
        try {
            abort = JsonUtil.getJsonMapper().readValue(json, Abort.class);
            return new Status(true, "Success", abort);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
            return new Status(false, jme.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return new Status(false, ioe.getMessage());
        }
    }

    public static Status<AuthRequest> parserMtnAuthorizationRequest(String xml){
        String json = JsonUtil.xmlToJson(xml);
        try {
            AuthRequest authRequest = JsonUtil.getJsonMapper().readValue(json, AuthRequest.class);
            System.out.println(authRequest);
            return new Status(true, "Success", authRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return new Status<>(false, e.getMessage());
        }
    }

    public static String getMtnUssdNotifyAbortResponse() {
        String xml = FileUtil.loadXmlFile("xml/mtn-notify-ussd-abort-response.xml");
        return xml;
    }

    public static Status<Fault> parseFault(String xml){
        String json = JsonUtil.xmlToJson(xml);
        try {
            Fault fault = JsonUtil.getJsonMapper().readValue(json, Fault.class);
            return new Status<>(false, fault.getDescription(), fault);
        } catch (IOException e) {
            logger.error("IOException", e);
            return new Status<>(false, e.getLocalizedMessage());
        }
    }

    public static void main(String args []){
        String str = "If you are using manual document IDs, you must ensure that IDs from the server's automatically generated document ID sequence are never used. X Plugin is not aware of the data inserted into the collection, including any IDs you use. Thus in future inserts, if the document ID which you assigned manually when inserting a document uses an ID which the server was going to use, the insert operation fails with an error due to primary key duplication.\n" + "\n";
        System.out.println(str.length());
    }

}
