package ru.rendaxx.lab8client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestClient;

import javax.swing.*;

@Configuration
public class AppConfig {

    @Value("${baseUrl}")
    private String baseUrl;

    @Bean
    public RestClient myRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
