package com.beehyv.tbalert.tbalertbackend.configuration;

import com.plivo.api.Plivo;
import com.plivo.api.PlivoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlivoConfig {

    @Value("${plivo.auth.id}")
    private String authId;

    @Value("${plivo.auth.token}")
    private String authToken;

    @Bean
    public PlivoClient plivoClient() {
        Plivo.init(authId, authToken);
        return Plivo.getClient();
    }
}
