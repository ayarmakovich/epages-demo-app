package com.epages.demo.app.product;

import java.io.IOException;
import java.util.Map;

public interface ProductFetcher {
    Map<String, Object> fetchProducts(String apiUrl, Integer page) throws IOException;
}
