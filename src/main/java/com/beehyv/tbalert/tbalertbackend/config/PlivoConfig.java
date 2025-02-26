package com.beehyv.tbalert.tbalertbackend.config;

import com.plivo.api.Plivo;
import com.plivo.api.PlivoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.config.plivo-connection", havingValue = "true", matchIfMissing = false)
@Slf4j
public class PlivoConfig {

    @Value("${plivo.auth.id}")
    private String authId;

    @Value("${plivo.auth.token}")
    private String authToken;

    @Bean
    public PlivoClient plivoClient() {
        log.info("Initializing Plivo Client");
        Plivo.init(authId, authToken);
        return Plivo.getClient();
    }
}
