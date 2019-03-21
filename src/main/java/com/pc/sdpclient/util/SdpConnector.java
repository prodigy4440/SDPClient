package com.pc.sdpclient.util;

import com.pc.sdpclient.model.Status;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SdpConnector {

    private static Logger logger = LoggerFactory.getLogger(SdpConnector.class);

    public static Status<String> post(HttpUrl url, String xml){
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).build();
        try (Response response = OkHttpUtil.getHttpClient().newCall(request).execute()) {
            String resStr = response.toString();
            logger.info("Res As String: {}", resStr);
            return new Status<>(true, "Success", resStr);
        }catch (IOException ioe){
            logger.error("IOException", ioe);
            return new Status<>(false, ioe.getLocalizedMessage());
        }
    }
}
