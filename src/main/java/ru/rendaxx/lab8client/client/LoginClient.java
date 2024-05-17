package ru.rendaxx.lab8client.client;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.util.UserCredentials;

@Log
@Component
@Scope("prototype")
public class LoginClient {

    private RestClient restClient;

    @Autowired
    public LoginClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String login(UserCredentials user) {
        MultiValueMap<String, String> bodyPair = new LinkedMultiValueMap<>();
        bodyPair.add("username", user.getUsername());
        bodyPair.add("rawPassword", new String(user.getRawPassword()));
        var response = restClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(bodyPair)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    log.warning("Client Error Status " + res.getStatusCode());
                })
                .toBodilessEntity();

        return switch (HttpStatus.valueOf(response.getStatusCode().value())) {
            case FOUND -> "ye";
            case UNAUTHORIZED -> "sad";
            default -> "Unexpected error";
        };
    }
}
