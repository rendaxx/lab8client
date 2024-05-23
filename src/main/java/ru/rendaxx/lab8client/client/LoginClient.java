package ru.rendaxx.lab8client.client;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import ru.rendaxx.lab8client.util.UserCredentials;
import ru.rendaxx.lab8client.util.UserSession;

@Log
@Component
@Scope("prototype")
public class LoginClient {

    @Autowired
    private ApplicationContext applicationContext;
    private RestClient restClient;
    private UserSession userSession;

    @Autowired
    public LoginClient(RestClient restClient, UserSession userSession) {
        this.restClient = restClient;
        this.userSession = userSession;
    }

    public String login(UserCredentials user) {
        MultiValueMap<String, String> bodyPair = new LinkedMultiValueMap<>();
        bodyPair.add("username", user.getUsername());
        bodyPair.add("rawPassword", new String(user.getRawPassword()));
        ResponseEntity<Void> response;
        try {
            response = restClient.post()
                    .uri("/login")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(bodyPair)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.warning("Client Error Status " + res.getStatusCode());
                    })
                    .toBodilessEntity();
        } catch (Exception e) {
            log.severe("Exception while signing in occurred: " + e);
            return "form.login.unexpectedError";
        }

        switch (HttpStatus.valueOf(response.getStatusCode().value())) {
            case FOUND -> {
                userSession.setUsername(user.getUsername());
                applicationContext.getBean(WebSocketConnection.class);
                return "form.login.success";
            }
            case UNAUTHORIZED -> {
                return "form.login.conflict";
            }
            default -> {
                return "form.login.unexpectedError";
            }
        }
    }
}
