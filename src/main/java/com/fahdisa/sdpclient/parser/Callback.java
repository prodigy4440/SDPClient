package com.fahdisa.sdpclient.parser;

import com.fahdisa.sdpclient.model.Status;
import com.fahdisa.sdpclient.model.ussd.Abort;
import com.fahdisa.sdpclient.model.ussd.Ussd;
import com.fahdisa.sdpclient.util.JsonUtil;

import java.io.IOException;
import java.util.Objects;

public class Callback {

    public static Status ussd(String xml){
        UssdModel ussdModel = UssdModel.process(xml);
        if(Objects.isNull(ussdModel)){
            return new Status(false, "", xml);
        }else{
            if(ussdModel.getType() == UssdModel.Type.UNKNOWN){
                return new Status(false, "", xml);
            }else{
                return new Status(true, "Success", ussdModel);
            }
        }
    }


    public static class UssdModel {

        private Type type;

        private Ussd ussd;

        private Abort abort;

        public UssdModel(){

        }

        public UssdModel(Type type){
            this.type =type;
        }

        public UssdModel(Type type, Ussd ussd){
            this.type = type;
            this.ussd = ussd;
        }

        public UssdModel(Type type, Abort abort){
            this.type = type;
            this.abort = abort;
        }

        public Type getType(){
            return this.type;
        }

        public Ussd getUssd(){
            return this.ussd;
        }

        public Abort getAbort(){
            return this.abort;
        }

        public static UssdModel process(String xml){
            if(Objects.isNull(xml) || xml.isEmpty()){
                return new UssdModel(Type.UNKNOWN);
            }else{
                if(xml.contains("notifyUssdAbort")){
                    return new UssdModel(Type.ABORT, new UssdModel().notifyAbort(xml));
                }else if(xml.contains("notifyUssdReception")){
                    return new UssdModel(Type.USSD, new UssdModel().notifyUssd(xml));
                }else{
                    return new UssdModel(Type.UNKNOWN);
                }
            }
        }

        private Ussd notifyUssd(String xml){
            try {
                return JsonUtil.xmlStraightToPojo(xml, com.fahdisa.sdpclient.model.ussd.Ussd.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Abort notifyAbort(String xml){
            try {
                return JsonUtil.xmlStraightToPojo(xml, com.fahdisa.sdpclient.model.ussd.Abort.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public enum Type{
            USSD, ABORT, UNKNOWN;
        }

        @Override
        public String toString() {
            return "UssdModel{" +
                    "type=" + type +
                    ", ussd=" + ussd +
                    ", abort=" + abort +
                    '}';
        }

        public static String USSD_RESPONSE =
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/ussd/notification/v1_0/local\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "   <loc:notifyUssdReceptionResponse> <loc:result>0</loc:result>\n" +
                        "</loc:notifyUssdReceptionResponse> </soapenv:Body>\n" +
                        "</soapenv:Envelope>\n";
        public static String ABORT_RESPONSE =
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/ussd/notification/v1_0/local\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "       <loc:notifyUssdAbortResponse/>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";
    }

}
