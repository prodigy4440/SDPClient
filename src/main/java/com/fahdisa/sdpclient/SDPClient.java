/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fahdisa.sdpclient;

import com.fahdisa.sdpclient.config.ServiceConfig;
import com.fahdisa.sdpclient.config.UrlConfig;
import com.fahdisa.sdpclient.model.Status;
import com.fahdisa.sdpclient.model.config.SdpConfig;
import com.fahdisa.sdpclient.parser.MtnXmlParser;
import com.fahdisa.sdpclient.util.FileUtil;
import com.fahdisa.sdpclient.util.JsonUtil;
import com.fahdisa.sdpclient.util.MtnUrl;
import com.fahdisa.sdpclient.util.OkHttpUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author prodigy4440
 */
public class SDPClient {

    private static Logger logger = LoggerFactory.getLogger(SDPClient.class);

    public static void main(String[] args) throws IOException {

        Path paths = Paths.get("dp-sdp-config.json");
        String jsonConfig = Files.readAllLines(paths).stream().collect(Collectors.joining());
        List<SdpConfig> sdpConfigs = Arrays.asList(JsonUtil.getJsonMapper().readValue(jsonConfig, SdpConfig[].class));

        SdpConfig sdpConfig = null;

        for (SdpConfig sdpCon :
                sdpConfigs) {
            if(sdpCon.getName().equalsIgnoreCase("nimc-ussd-menu")){
                sdpConfig = sdpCon;
            }
        }

        System.out.println(sdpConfig);

        UrlConfig urlConfig = new UrlConfig.Builder().build();
        ServiceConfig nimcService = new ServiceConfig.Builder()
                .setSpId(sdpConfig.getSpId())
                .setSpPassword(sdpConfig.getSpPassword())
                .setTimestamp(sdpConfig.getTimestamp())
                .setServiceId(sdpConfig.getServiceId())
                .setProductId(sdpConfig.getProductId())
                .setUssdServiceActivationNumber(sdpConfig.getUssdServiceActivationNumber())
                .setCorrelator(sdpConfig.getCorrelator())
                .setEndpoint(sdpConfig.getEndpoint())
                .build();

        Integrator nimcIntegrator = new Integrator.Builder()
                .addUrl(urlConfig)
                .addService(nimcService)
                .build();


        Status status = nimcIntegrator.chargePhone("08131631151","",2000);
        logger.info("{}", status);

//        String transactionId = TransactionUtil.generateTransactionId(MtnUrl.IP(),1,4);
//
//        logger.info("Transaction ID: {}", transactionId);
//        UrlConfig urlConfig = new UrlConfig.Builder().build();
//        String spPassword = EncryptionUtil.md5("2340110011483" + "bmeB500" + "20190728084320");
//
//        ServiceConfig megaVaultServiceConfig = new ServiceConfig
//                .Builder()
//                .setSpId("2340110011483")
//                .setSpPassword(spPassword)
//                .setTimestamp("20190728084320")
//                .setOa("2348166721550")
//                .setFa("2348166721550")
//                .setServiceId("234012000024080")
//                .setProductId("23401220000027965")
//                .setEndpoint("http://69.28.95.40:8087/megavault/api/v1.0/sdp/callback")
//                .setCorrelator("8738938838")
//                .setSmsServiceActivationNumber("20902")
//                .build();
//
//        Integrator megaVaultIntegrator = new Integrator.Builder()
//                .addUrl(urlConfig)
//                .addService(megaVaultServiceConfig)
//                .build();
//
//
//        String phoneNumber = "2348131631151";
//
//        Status status = megaVaultIntegrator.sendSms("20902", phoneNumber, "Sample Message From from SDP Client");
//        logger.info("STATUS: {}", status);
//AuthModel{spId='2340110011483', serviceId='234012000024080', traceUniqueId='203035007912019082706090241004', subscriberID='2348131631151', partnerID='2340110011483', capabilityType='17', consentResult='0', accessToken='27060917079100699621', tokenType='1', tokenExpiryTime='20190828050917'}



//        Status authorizationStatus = megaVaultIntegrator.sendAuthorizationRequest("09030007010", transactionId, 17, 5000,
//                "NGN", "Payment", 0, 1,
//                "MegaVault", "2", 1, 1, 1);
//        logger.info("AUTHORIZATION STATUS: {}", authorizationStatus);
//        Status queryStatus = megaVaultIntegrator
//                .sendQueryAuthorizationRequest(phoneNumber, "011000004131908241358070001003",
//                        "08241358080777872280");
//        logger.info("QUERY STATUS: {}",queryStatus);
//        Status chargeStatus = megaVaultIntegrator.chargePhone("09030007010", "27161009075802604522", 5000);
//        logger.info("CHARGE STATUS: {}", chargeStatus);


//        ServiceConfig jambUssdService = new ServiceConfig.Builder()
//                .setSpId("2340110005999")
//                .setSpPassword("F0E139532F43210A1DB9077C4B0FD06E")
//                .setTimestamp("20190313095640")
//                .setServiceId("234012000023788")
//                .setProductId("23401220000027529")
//                .setUssdServiceActivationNumber("*55019")
//                .setCorrelator("234012000023788")
//                .setEndpoint("http://154.113.0.202:8087/api/v1.0/ussd/notify")
//                .build();
//
//        ServiceConfig acadaStandardService = new ServiceConfig.Builder()
//                .setSpId("2340110005999")
//                .setSpPassword("ebe4fdda369ef289cf77210f6e0fff9b")
//                .setTimestamp("20170110154245")
//                .setOa("2348138075679")
//                .setFa("2348138075679")
//                .setServiceId("234012000023028")
//                .setProductId("23401220000026651")
//                .build();
//
//        ServiceConfig acadaOnDemandService = new ServiceConfig.Builder()
//                .setSpId("2340110005999")
//                .setSpPassword("ebe4fdda369ef289cf77210f6e0fff9b")
//                .setTimestamp("20170110154245")
//                .setOa("2348138075679")
//                .setFa("2348138075679")
//                .setServiceId("234012000023027")
//                .setProductId("23401220000026650")
//                .build();
//
//        ServiceConfig acadaSmsService = new ServiceConfig.Builder()
//                .setSpId("2340110005999")
//                .setSpPassword("ebe4fdda369ef289cf77210f6e0fff9b")
//                .setTimestamp("20170110154245")
//                .setOa("2348138075679")
//                .setFa("2348138075679")
//                .setServiceId("234012000023327")
//                .setProductId("23401220000026940")
//                .setEndpoint("http://154.113.0.202:8999/acada/api/v1.0/integration/mtn/notify/sms")
//                .setSmsServiceActivationNumber("162")
//                .setCorrelator("744550456547")
//                .build();
//
//
//        ServiceConfig dailyDevotionalServiceConfig = new ServiceConfig.Builder()
//                .setSpId("2340110005999")
//                .setSpPassword("ebe4fdda369ef289cf77210f6e0fff9b")
//                .setTimestamp("20170110154245")
//                .setOa("2348138075679")
//                .setFa("2348138075679")
//                .setServiceId("234012000023873")
//                .setProductId("23401220000027676")
//                .setEndpoint("http://154.113.0.202:8999/acada/api/v1.0/integration/mtn/notify/sms")
//                .setCorrelator("744550456547")
//                .build();
//

//        Integrator jambUssdIntegrator = new Integrator.Builder()
//                .addUrl(urlConfig)
//                .addService(jambUssdService)
//                .build();

//        Integrator acadaStandardIntegrator = new Integrator.Builder()
//                .addUrl(urlConfig)
//                .addService(acadaStandardService)
//                .build();
//
//        Integrator acadaOnDemandIntegrator = new Integrator.Builder()
//                .addUrl(urlConfig)
//                .addService(acadaOnDemandService)
//                .build();

//        Integrator acadaSmsIntegrator = new Integrator.Builder()
//                .addUrl(urlConfig)
//                .addService(acadaSmsService)
//                .build();


//        Integrator devotionalIntegrator = new Integrator.Builder()
//                .addUrl(urlConfig)
//                .addService(dailyDevotionalServiceConfig)
//                .build();

//        devotionalIntegrator.sendAuthorizationRequest("2348131631151",
//                "12345", 17, 5000,
//                "NGN","Dsc",0,
//                1,"MegaVault","2",
//                1,5,1);

//        Status status = acadaSmsIntegrator.sendSms("162", phoneNumber, "If you are using manual document IDs, you must ensure that IDs from the server's automatically generated document ID sequence are never used. X Plugin is not aware of the data inserted into the collection, including any IDs you use. Thus in future inserts, if the document ID which you assigned manually when inserting a document uses an ID which the server was going to use, the insert operation fails with an error due to primary key duplication.\n" + "\n");
//        Status status = acadaStandardIntegrator.unsubscribePhone(phoneNumber);
//        Status status = acadaOnDemandIntegrator.unsubscribePhone(phoneNumber);
//        Status status = acadaOnDemandIntegrator.chargePhone("2348131631151", 100);
//        Status status = jambUssdIntegrator.chargePhone("2348062318399", 5000);

//        Status status = devotionalIntegrator.sendSms("2646","08131631151","Sample Devotional message");
//        Status status = devotionalIntegrator.sendSms("55020","2348131631151","Sample Devotional message");

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
}
