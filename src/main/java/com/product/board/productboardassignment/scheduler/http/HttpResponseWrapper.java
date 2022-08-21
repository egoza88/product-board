package com.product.board.productboardassignment.scheduler.http;

import lombok.Getter;

@Getter
public class HttpResponseWrapper {

    private final int statusCode;
    private final String body;

    public HttpResponseWrapper(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }
}
