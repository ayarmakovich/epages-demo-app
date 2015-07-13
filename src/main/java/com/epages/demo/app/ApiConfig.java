package com.epages.demo.app;

import com.epages.demo.app.installation.AppInstallationResource;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api")
public class ApiConfig extends ResourceConfig {

    public ApiConfig() {
        register(AppInstallationResource.class);
    }
}
