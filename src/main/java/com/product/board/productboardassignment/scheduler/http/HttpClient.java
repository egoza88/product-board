package com.product.board.productboardassignment.scheduler.http;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class HttpClient {
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
            log.error("Request {} failed", httpUrl);
            return new HttpResponseWrapper(400, "");
        }
    }
}
