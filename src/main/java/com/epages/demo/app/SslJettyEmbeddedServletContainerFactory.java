package com.epages.demo.app;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;

public class SslJettyEmbeddedServletContainerFactory extends JettyEmbeddedServletContainerFactory {

    private final int httpPort;

    private final int httpsPort;

    public SslJettyEmbeddedServletContainerFactory(int httpPort, int httpsPort) {
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
    }

    @Override
    protected JettyEmbeddedServletContainer getJettyEmbeddedServletContainer(Server server) {
        server.setConnectors(new Connector[] { httpConnector(server), httpsConnector(server) });
        return super.getJettyEmbeddedServletContainer(server);
    }

    private ServerConnector httpsConnector(Server server) {
        ServerConnector httpsConnector = new ServerConnector( //
                server, //
                new SslConnectionFactory(sslContextFactory(), "http/1.1"), //
                new HttpConnectionFactory(httpsConfiguration()) //
        );
        httpsConnector.setPort(httpsPort);
        return httpsConnector;
    }

    private ServerConnector httpConnector(Server server) {
        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(httpPort);
        return httpConnector;
    }

    private HttpConfiguration httpsConfiguration() {
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        return https;
    }

    private SslContextFactory sslContextFactory() {
        SslContextFactory sslContextFactory = new SslContextFactory();
        // rm ./keystore.jks && keytool -genkey -v -keystore ./keystore.jks -alias demoapp -keyalg RSA -keysize 2048 -keypass epages -storepass epages -validity 3650 -dname "CN=localhost, OU=Demo App, O=ePages GmbH, L=Hamburg, ST=Germany, C=DE"
        sslContextFactory.setKeyStorePath(SslJettyEmbeddedServletContainerFactory.class.getResource("/keystore.jks").toExternalForm());
        sslContextFactory.setCertAlias("demoapp");
        sslContextFactory.setKeyStorePassword("epages");
        sslContextFactory.setKeyManagerPassword("epages");
        return sslContextFactory;
    }
}
