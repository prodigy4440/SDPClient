package com.fahdisa.sdpclient.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionUtil {

    public static String sha256(String input){
        return DigestUtils.sha256Hex(input);
    }

    public static String base64(String input){
        return new String(Base64.encodeBase64(input.getBytes()));
    }

    public static String md5(String input){
        return DigestUtils.md5Hex(input);
    }
}
