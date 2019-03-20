package com.pc.sdpclient.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TransactionUtil {

    private static final DecimalFormat DECIMAL_FORMAT_FOUR = new DecimalFormat("0000");
    private static final DecimalFormat DECIMAL_FORMAT_THREE = new DecimalFormat("000");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmss");


    public static String generateTransactionId(String ip, Integer sequenceNumber, Integer messageNumber){
        return new StringBuilder().append("0110000")
                .append(ipToOctetSting(ip))
                .append(SIMPLE_DATE_FORMAT.format(new Date()))
                .append(DECIMAL_FORMAT_FOUR.format(sequenceNumber))
                .append(DECIMAL_FORMAT_THREE.format(messageNumber))
                .toString();
    }

    private static String ipToOctetSting(String ip){
        String[] ipSplit = ip.split("\\.");
        Integer ipSum = 0;
        for (String ips : ipSplit){
            ipSum += Integer.parseInt(ips);
        }
        return DECIMAL_FORMAT_FOUR.format(ipSum);
    }

}
