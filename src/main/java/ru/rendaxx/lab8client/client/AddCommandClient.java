package ru.rendaxx.lab8client.client;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.model.object.OrganizationDto;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Log
@Component
public class AddCommandClient {
    private RestClient restClient;

    @Autowired
    public AddCommandClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String add(OrganizationDto organization) {
        ResponseEntity<Void> response;
        try {
            response = restClient.post()
                    .uri("/add")
                    .contentType(APPLICATION_JSON)
                    .body(organization)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.warning("Client Error Status " + res.getStatusCode());
                    })
                    .toBodilessEntity();
        } catch (Exception e) {
            log.severe("Exception while registration occurred: " + e);
            return "unexpected error";
        }

        return switch (HttpStatus.valueOf(response.getStatusCode().value())) {
            case OK -> "ok";
            default -> "not ok";
        };
    }
}
