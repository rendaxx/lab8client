package ru.rendaxx.lab8client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.util.LocaleChangeListener;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

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

    @Bean("myList")
    public List<LocaleChangeListener> myListenersList() {
        return new LinkedList<>();
    }
}
