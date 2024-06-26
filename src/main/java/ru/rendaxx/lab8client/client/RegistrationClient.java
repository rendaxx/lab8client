package ru.rendaxx.lab8client.client;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.util.UserCredentials;

import java.util.ResourceBundle;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Log
@Component
@Scope("prototype")
public class RegistrationClient {
    private RestClient restClient;

    @Autowired
    public RegistrationClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String register(UserCredentials user) {
        ResponseEntity<Void> response;
        try {
            response = restClient.post()
                    .uri("/registration")
                    .contentType(APPLICATION_JSON)
                    .body(user)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.warning("Client Error Status " + res.getStatusCode());
                    })
                    .toBodilessEntity();
        } catch (Exception e) {
            log.severe("Exception while registration occurred: " + e);
            return "form.register.unexpectedError";
        }

        return switch (HttpStatus.valueOf(response.getStatusCode().value())) {
            case OK -> "form.message.okStatus";
            case CONFLICT -> "form.message.conflict";
            default -> "form.register.unexpectedError";
        };
    }
}
