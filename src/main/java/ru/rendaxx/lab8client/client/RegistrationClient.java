package ru.rendaxx.lab8client.client;

import jakarta.annotation.Resource;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.util.UserCredentials;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Log
public class RegistrationClient {
    private RestClient restClient;

    public String register(UserCredentials user) {
        var response = restClient.post()
                .uri("/registration")
                .contentType(APPLICATION_JSON)
                .body(user)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    log.warning("Client Error Status " + res.getStatusCode());
                })
                .toBodilessEntity();

        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/registration");

        return switch (HttpStatus.valueOf(response.getStatusCode().value())) {
            case OK -> resourceBundle.getString("okStatusMessage");
            case CONFLICT -> resourceBundle.getString("conflictStatusMessage");
            default -> "Unexpected error";
        };
    }
}
