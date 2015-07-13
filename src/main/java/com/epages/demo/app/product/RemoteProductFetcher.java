package com.epages.demo.app.product;

import static com.epages.demo.app.util.RequestUtils.accept;
import static com.epages.demo.app.util.RequestUtils.authorization;
import static com.epages.demo.app.util.ResponseUtils.content;
import static org.apache.http.client.fluent.Request.Get;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.epages.demo.app.installation.AppInstallation;
import com.epages.demo.app.installation.AppInstallationRepository;

@Component
@Profile("production")
public class RemoteProductFetcher implements ProductFetcher {

    @Autowired
    private JsonParser jsonParser;

    @Autowired
    private AppInstallationRepository appInstallationRepository;

    @Override
    public Map<String, Object> fetchProducts(String apiUrl, Integer page) throws IOException {
        final AppInstallation appInstallation = appInstallationRepository.findByApiUrl(apiUrl);
        if (appInstallation == null) {
            throw new IllegalStateException(String.format("No AppInstallation found for api_url '%s'", apiUrl));
        }

        final Response response = productsRequest(appInstallation, page).execute();

        return jsonParser.parseMap(content(response));
    }

    private Request productsRequest(AppInstallation appInstallation, Integer page) {
        final String endpoint = fromHttpUrl(appInstallation.getApiUrl()) //
                .path("/products") //
                .queryParam("page", page) //
                .build() //
                .toUriString();
        return Get(endpoint).addHeader(accept()).addHeader(authorization(appInstallation.getToken()));
    }
}
