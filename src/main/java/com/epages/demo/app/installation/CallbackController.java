package com.epages.demo.app.installation;

import static com.epages.demo.app.util.ResponseUtils.content;
import static org.apache.http.client.fluent.Form.form;
import static org.apache.http.client.fluent.Request.Post;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CallbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackController.class);

    @Value("#{environment.CLIENT_ID ?: '54f46f318732110bd85f41c7'}")
    private String clientId;

    @Value("#{environment.CLIENT_SECRET ?: 'ClientSecret1'}")
    private String clientSecret;

    @Autowired
    private AppInstallationRepository appInstallationRepository;

    @Autowired
    private JsonParser jsonParser;

    // https://localhost:5001/callback?code=svwvuIIgENO4q9AamlD7gW314udU1yX8&return_url=http:%2F%2Fotrosien%2Fepages%2FDemoShop.admin%2F%3FObjectID%3D9743%26ViewAction%3DMBO-ViewAppDetails%26appID%3D54f46f318732110bd85f41c7&api_url=http:%2F%2Fotrosien%2Frs%2Fshops%2FDemoShop
    // curl -sik -XGET "https://localhost:5001/callback?code=svwvuIIgENO4q9AamlD7gW314udU1yX8&access_token_url=http%3A%2F%2Flocalhost%3A8088%2Frs%2Fshops%2FDemoShop%2Ftoken&api_url=http:%2F%2Flocalhost:8088%2Frs%2Fshops%2FDemoShop&return_url=http:%2F%2Fgoogle.de"
    // curl -sik -XGET "https://localhost:5001/callback?code=AccessCode&access_token_url=http:%2F%2Fwww.mocky.io%2Fv2%2FFOOBAR&api_url=http:%2F%2Fwww.mocky.io%2Fv2%2FFOOBAR&return_url=http:%2F%2Flocalhost:5000"
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = true) String code, //
                           @RequestParam(value = "access_token_url", required = false) String accessTokenUrl, //
                           @RequestParam(value = "api_url", required = true) String apiUrl, //
                           @RequestParam(value = "return_url", required = true) String returnUrl //
    ) {
        try {
            final String token = retrieveTokenForCode(accessTokenUrl != null ? accessTokenUrl : apiUrl + "/token" , apiUrl, code);
            appInstallationRepository.save(new AppInstallation(token, apiUrl));
            LOGGER.info("Successfully processed callback for api_url '{}'", apiUrl);
            return redirect(returnUrl, CallbackResult.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("Failure processing callback for api_url '{}'", apiUrl, e);
            return redirect(returnUrl, CallbackResult.ERROR);
        }
    }

    private String redirect(String returnUrl, CallbackResult callbackResult) {
        final String redirectUrl = fromHttpUrl(returnUrl) //
                .queryParam("callbackResult", callbackResult) //
                .build() //
                .toUriString();
        return "redirect:" + redirectUrl;
    }

    private String retrieveTokenForCode(String accessTokenUrl, String apiUrl, String code) throws IOException {
        final Response response = tokenRequest(accessTokenUrl, apiUrl, code).execute();
        final Map<String, Object> objects = jsonParser.parseMap(content(response));
        return objects.get("access_token").toString();
    }

    private Request tokenRequest(String accessTokenUrl, String apiUrl, String code) {
        return Post(accessTokenUrl).bodyForm(form() //
                .add("code", code) //
                .add("client_id", clientId) //
                .add("client_secret", clientSecret) //
                .build() //
        );
    }

    private enum CallbackResult {
        SUCCESS, ERROR;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
