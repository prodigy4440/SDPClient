package com.fahdisa.sdpclient.util;

import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

public class IdGenerator {


    private static final DecimalFormat THREE_DIGIT_FORMATTER = new DecimalFormat("000");
    private static final DecimalFormat FOUR_DIGIT_FORMATTER = new DecimalFormat("0000");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyMMddHHmmss");

    public static class Auth{

        public static String next(Integer sequenceNumber, Integer messageSequence){

            String a = "0110000";
            String x = FOUR_DIGIT_FORMATTER.format(getIpOctetSum());
            String b = DATE_FORMATTER.format(new Date());

            return new StringBuilder().append(a)
                    .append(FOUR_DIGIT_FORMATTER.format(getIpOctetSum()))
                    .append(b)
                    .append(FOUR_DIGIT_FORMATTER.format(sequenceNumber))
                    .append(THREE_DIGIT_FORMATTER.format(messageSequence))
                    .toString();
        }
    }

    private static String getHostIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    private static Integer getIpOctetSum(){
        return Arrays.stream(getHostIp().split("\\."))
                .mapToInt(Integer::parseInt)
                .sum();
    }

}
