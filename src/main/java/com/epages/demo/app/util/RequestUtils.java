package com.epages.demo.app.util;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class RequestUtils {

    private RequestUtils() {
        // don't instance me
    }

    public static Header accept() {
        return new BasicHeader("Accept", "application/vnd.epages.v1+json");
    }

    public static Header authorization(String token) {
        return new BasicHeader("Authorization", "Bearer " + token);
    }
}
