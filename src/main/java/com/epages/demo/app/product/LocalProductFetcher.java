package com.epages.demo.app.product;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Profile("development")
public class LocalProductFetcher implements ProductFetcher {

    @Autowired
    private JsonParser jsonParser;

    private String readJson(String path) throws IOException {
        final InputStream inputStream = new ClassPathResource(path).getInputStream();
        // http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.toString())) {
            return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        }
    }

    @Override
    public Map<String, Object> fetchProducts(String apiUrl, Integer page) throws IOException {
        return jsonParser.parseMap(readJson("/products.json"));
    }
}
