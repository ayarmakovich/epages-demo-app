package com.epages.demo.app.product;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsController {

    @Autowired
    private ProductFetcher productFetcher;

    @RequestMapping(value = "/products")
    public String products(Model model, //
                           @RequestParam(value = "api_url", required = true) String apiUrl, //
                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page //
    ) throws IOException {
        model.addAttribute("api_url", apiUrl);
        model.addAttribute("pager", productFetcher.fetchProducts(apiUrl, page));
        return "products";
    }

    public static String getProductsLink(String apiUrl) {
        return "/products?api_url=" + apiUrl;
    }
}
