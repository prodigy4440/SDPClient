package com.fahdisa.sdpclient;

import com.fahdisa.sdpclient.config.ServiceConfig;
import com.fahdisa.sdpclient.config.UrlConfig;
import com.fahdisa.sdpclient.model.Status;
import com.fahdisa.sdpclient.model.subscription.SubResponse;
import com.fahdisa.sdpclient.model.subscription.UnsubResponse;
import com.fahdisa.sdpclient.network.SdpConnector;
import com.fahdisa.sdpclient.parser.MtnXmlParser;
import com.fahdisa.sdpclient.util.FileUtil;
import com.fahdisa.sdpclient.util.JsonUtil;
import com.fahdisa.sdpclient.util.MtnUrl;
import com.fahdisa.sdpclient.util.OkHttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
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

    public Status chargePhone(String phoneNumber, String authorizationToken, Integer amount) {
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-charge.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password", getServiceConfig().getSpPassword())
                .replaceAll("time_stamp", getServiceConfig().getTimestamp())
//                .replaceAll("product_id", getServiceConfig().getProductId())
                .replaceAll("authorization_token", authorizationToken)
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
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                Status subStatus = MtnXmlParser.parseMtnSubscribeResponse(xmlResponse);
                if(subStatus.getStatus()){
                    SubResponse subResponse = (SubResponse) subStatus.getData();
                    if(subResponse.getCode() == 22007233){
                        return subStatus;
                    }else{
                        return new Status(false, subResponse.getDescription());
                    }
                }
                return MtnXmlParser.parseMtnSubscribeResponse(xmlResponse);
            }
        }else{
            return postStatus;
        }
    }

    public Status unsubscribePhone(String phoneNumber) {
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-unsubscribe.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password", getServiceConfig().getSpPassword())
                .replaceAll("time_stamp", getServiceConfig().getTimestamp())
                .replaceAll("product_id", getServiceConfig().getProductId())
                .replaceAll("end_user_phone_number", phoneNumber);

        Status<String> postStatus = SdpConnector.post(getUrlConfig().getSubscribe(), xmlRequest);

        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                Status status = MtnXmlParser.parseMtnUnsubscribeResponse(xmlResponse);
                if(status.getStatus()){
                    UnsubResponse unsubResponse = (UnsubResponse)status.getData();
                    if(unsubResponse.getCode() == 0){
                        return status;
                    }else{
                        return new Status(false, unsubResponse.getDescription());
                    }
                }else{
                    return status;
                }
            }
        }else{
            return postStatus;
        }
    }

    public Status sendSms(String sender, String phoneNumber, String message){

        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-send-sms.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password",getServiceConfig().getSpPassword())
                .replaceAll("bundle_id",getServiceConfig().getBundleId())
                .replaceAll("service_id",getServiceConfig().getServiceId())
                .replaceAll("time_stamp",getServiceConfig().getTimestamp())
                .replaceAll("o_a",getServiceConfig().getOa())
                .replaceAll("fake_a",getServiceConfig().getFa())
                .replaceAll("link_id",getServiceConfig().getLinkid())
                .replaceAll("present_id", getServiceConfig().getPresentId())
                .replaceAll("end_user_phonenumber", phoneNumber)
                .replaceAll("sender_name", sender)
                .replaceAll("sms_message", message)
                .replaceAll("notify_url",getServiceConfig().getEndpoint())
                .replaceAll("correlator_ref", getServiceConfig().getCorrelator());

        Status<String> postStatus = SdpConnector.post(getUrlConfig().getSendSms(), xmlRequest);

        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                return MtnXmlParser.parseMtnSendSms(xmlResponse);
            }
        }else{
            return postStatus;
        }
    }

    public Status startSmsNotification(){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-start-sms-notification.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password",getServiceConfig().getSpPassword())
                .replaceAll("service_id",getServiceConfig().getServiceId())
                .replaceAll("time_stamp",getServiceConfig().getTimestamp())
                .replaceAll("service_activation_number",getServiceConfig().getSmsServiceActivationNumber())
                .replaceAll("notify_url",getServiceConfig().getEndpoint())
                .replaceAll("correlator_ref", getServiceConfig().getCorrelator());

        Status<String> postStatus = SdpConnector.post(getUrlConfig().getStartSms(), xmlRequest);
        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                return MtnXmlParser.parseStartSmsResponse(xmlResponse);
            }
        }else{
            return postStatus;
        }
    }

    public Status stopSmsNotification(){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-stop-sms-notification.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password",getServiceConfig().getSpPassword())
                .replaceAll("service_id",getServiceConfig().getServiceId())
                .replaceAll("time_stamp",getServiceConfig().getTimestamp())
                .replaceAll("correlator_ref", getServiceConfig().getCorrelator());

        Status<String> postStatus = SdpConnector.post(getUrlConfig().getStartSms(), xmlRequest);
        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                return MtnXmlParser.parseStartSmsResponse(xmlResponse);
            }
        }else{
            return postStatus;
        }
    }

    public Status sendUssd(Integer msgType, String senderCb,
                                  String receiverCb, Integer msgOpType, String msisdn, String serviceCode,
                                  String codeScheme, String ussdString){

        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-send-ussd-request.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password",getServiceConfig().getSpPassword())
                .replaceAll("bundle_id",getServiceConfig().getBundleId())
                .replaceAll("time_stamp",getServiceConfig().getTimestamp())
                .replaceAll("service_id", getServiceConfig().getTimestamp())
                .replaceAll("fake_oa",getServiceConfig().getOa())
                .replaceAll("fake_fa",getServiceConfig().getFa())
                .replaceAll("msg_type", String.valueOf(msgType))
                .replaceAll("sender_cb", senderCb)
                .replaceAll("receive_cb", receiverCb)
                .replaceAll("ussd_op_type", String.valueOf(msgOpType))
                .replaceAll("user_phone_number", msisdn)
                .replaceAll("service_code", serviceCode)
                .replaceAll("code_scheme", codeScheme)
                .replaceAll("ussd_string",ussdString);

        Status<String> postStatus = SdpConnector.post(getUrlConfig().getSendUssd(), xmlRequest);
        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                return MtnXmlParser.parseMtnSendUssd(xmlResponse);
            }
        }else{
            return postStatus;
        }
    }

    public Status sendUssdAbort(String senderCb, String receiverCb, String abortReason){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-send-ussd-abort-request.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password",getServiceConfig().getSpPassword())
                .replaceAll("service_id",getServiceConfig().getServiceId())
                .replaceAll("time_stamp",getServiceConfig().getTimestamp())
                .replaceAll("fake_oa",getServiceConfig().getOa())
                .replaceAll("fake_fa",getServiceConfig().getFa())
                .replaceAll("sender_cb", senderCb)
                .replaceAll("receive_cb", receiverCb)
                .replaceAll("abort_reason", abortReason);

        Status<String> postStatus = SdpConnector.post(getUrlConfig().getSendUssd(), xmlRequest);
        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            if(xmlResponse.contains("faultstring")){
                return MtnXmlParser.parseFault(xmlResponse);
            }else{
                return MtnXmlParser.parseMtnAbortUssd(xmlResponse);
            }
        }else{
            return postStatus;
        }
    }

    /**
     * Request for User consent before charging them for a service
     *
     * Scoep: 17: Payment
     *        99: Subscription
     *        4: LBS
     *        79: On-Demand
     * */
    public Status sendAuthorizationRequest(String phoneNumber, String transactionId, Integer scope,
                                                  Integer amount, String currency, String description,
                                                  Integer frequency){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-authorization-request.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password",getServiceConfig().getSpPassword())
                .replaceAll("time_stamp",getServiceConfig().getTimestamp())
                .replaceAll("end_user_identifier",phoneNumber)
                .replaceAll("transaction_id",transactionId)
                .replaceAll("auth_scope",String.valueOf(scope))
                .replaceAll("service_id",getServiceConfig().getServiceId())
                .replaceAll("service_amount",String.valueOf(amount))
                .replaceAll("country_currency",currency)
                .replaceAll("service_description",description)
                .replaceAll("notification_url",getServiceConfig().getEndpoint())
                .replaceAll("auth_frequency", String.valueOf(frequency));
        Status<String> postStatus = SdpConnector.post(getUrlConfig().getAuthorization(), xmlRequest);
        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            return MtnXmlParser.parserMtnAuthorizationRequest(xmlResponse);
        }else{
            return postStatus;
        }
    }


    /**
     *  Query user authorization status
     *
     * @param phoneNumber The user's phone number
     * @param transactionId Unique id to identify transaction
     * @param accessToken The token code of the authorization request
     * */
    public Status sendQueryAuthorizationRequest(String phoneNumber,
                                                String transactionId,
                                                String accessToken){
        String xmlRequest = FileUtil.loadXmlFile("xml/mtn-query-authorization-request.xml")
                .replaceAll("sp_id", getServiceConfig().getSpId())
                .replaceAll("sp_password",getServiceConfig().getSpPassword())
                .replaceAll("time_stamp",getServiceConfig().getTimestamp())
                .replaceAll("end_user_identifier",phoneNumber)
                .replaceAll("transaction_id",transactionId)
                .replaceAll("access_token",accessToken)
                .replaceAll("service_id",getServiceConfig().getServiceId());
        Status<String> postStatus = SdpConnector.post(getUrlConfig().getAuthorization(), xmlRequest);
        if(postStatus.getStatus()){
            String xmlResponse = postStatus.getData();
            return MtnXmlParser.parseMtnAuthQueryResponse(xmlResponse);
        }else{
            return postStatus;
        }
    }

    public UrlConfig getUrlConfig(){
        return this.urlConfig;
    }

    public ServiceConfig getServiceConfig(){
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
