package ru.rendaxx.lab8client.client;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.util.UserCredentials;

@Log
public class LoginClient {

    @Resource
    private RestClient restClient;

    public String login(UserCredentials user) {
        var response = restClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(user)
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
