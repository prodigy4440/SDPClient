package com.pc.sdpclient.util;

import com.pc.sdpclient.model.Status;
import okhttp3.*;

import java.io.IOException;

public class SdpConnector {

    public static Status<String> post(HttpUrl url, String xml){
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody requestBody = RequestBody.create(mediaType, xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody).build();
        try (Response response = OkHttpUtil.getHttpClient().newCall(request).execute()) {
            return new Status<>(true, "Success", response.toString());
        }catch (IOException ioe){
            ioe.printStackTrace();
            return new Status<>(false, ioe.getLocalizedMessage());
        }
    }
}
