package com.product.board.productboardassignment.scheduler.http;

import lombok.NonNull;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static HttpResponseWrapper sendGet(@NonNull String httpUrl, Headers headers) {
        Request request = new Request.Builder()
                .url(httpUrl)
                .headers(headers)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body() == null ? "" : response.body().string();
            return new HttpResponseWrapper(response.code(), responseBody);
        } catch (IOException e) {
            LOGGER.error("Request {} failed", httpUrl);
            return new HttpResponseWrapper(400, "");
        }
    }
}
