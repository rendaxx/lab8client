package ru.rendaxx.lab8client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.client.CookieInterceptor;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UserSession;

import java.util.*;

@Configuration
public class AppConfig {

    @Value("${baseUrl}")
    private String baseUrl;

    @Bean
    public ClientHttpRequestInterceptor myCookieInterceptor() {
        return new CookieInterceptor(userSession());
    }

    @Bean
    public UserSession userSession() {
        return new UserSession();
    }

    @Bean
    public RestClient myRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor(myCookieInterceptor())
                .build();
    }

    @Bean("myListenersList")
    public List<SetTextListener> myListenersList() {
        return new LinkedList<>();
    }

    @Bean("myOrganizationCollection")
    public LinkedHashSet<OrganizationDto> myCollectionList() {
        return new LinkedHashSet<>();
    }
}
