package com.webflux.sample.config.feign;

import jakarta.annotation.PostConstruct;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "default"})
public class FeignProxyConfig {

    @Generated
    private static final Logger log = LoggerFactory.getLogger(FeignProxyConfig.class);

    @Value("${huntercodexs.openfeign.client.config.proxy.enable:true}")
    private boolean enableProxy;

    @Value("${huntercodexs.openfeign.client.config.proxy.host:host.docker.internal}")
    private String proxyHost;

    @Value("${huntercodexs.openfeign.client.config.proxy.port:35900}")
    private String proxyPort;

    @PostConstruct
    public void properties() {
        if (this.enableProxy) {
            System.setProperty("https.proxyHost", this.proxyHost);
            System.setProperty("https.proxyPort", this.proxyPort);
            System.setProperty("https.proxySet", "true");
            System.setProperty("http.proxyHost", this.proxyHost);;
            System.setProperty("http.proxyPort", this.proxyPort);
            System.setProperty("http.proxySet", "true");
            log.info("Feign Proxy enabled on {}:{}", this.proxyHost, this.proxyPort);
        } else {
            log.info("Feign Proxy is disabled");
        }
    }

}
