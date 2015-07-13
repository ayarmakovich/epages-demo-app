package com.epages.demo.app.installation;

import com.epages.demo.app.product.ProductsController;

public class AppInstallation {

    private String apiUrl;

    private String token;

    public AppInstallation() {
    }

    public AppInstallation(String token, String apiUrl) {
        this.apiUrl = apiUrl;
        this.token = token;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProductsLink() {
        return ProductsController.getProductsLink(apiUrl);
    }

    public String getShopName() {
        String[] res = apiUrl.split("/");
        if (res.length <= 0) {
            return null;
        }
        return res[res.length-1];
    }
}
