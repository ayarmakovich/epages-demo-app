package com.epages.demo.app.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.fluent.Response;

public class ResponseUtils {
    private ResponseUtils() {
        // don't instance me
    }

    public static String content(Response response) throws IOException {
        return new String(response.returnContent().asBytes(), StandardCharsets.UTF_8);
    }
}
