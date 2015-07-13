package com.epages.demo.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EPagesDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EPagesDemoApplication.class, args);
    }

    @Value("${server.port}")
    private int httpPort;

    @Bean
    public JsonParser jsonParser() {
        return JsonParserFactory.getJsonParser();
    }

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        final int httpsPort = httpPort + 1;
        return new SslJettyEmbeddedServletContainerFactory(httpPort, httpsPort);
    }
}
