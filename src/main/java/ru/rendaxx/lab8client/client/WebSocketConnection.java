package ru.rendaxx.lab8client.client;

import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import ru.rendaxx.lab8client.util.UserSession;

import java.net.URI;

@Component
@Log
@Lazy
public class WebSocketConnection {

    private final UserSession userSession;
    private final WebSocketClient client;
    private final String url;
    private final WebSocketHandler handler;

    @Autowired
    public WebSocketConnection(UserSession userSession, @Value("${wsUpdateUrl}") String url,
                               WebSocketClient client, WebSocketHandler handler) {
        this.userSession = userSession;
        this.url = url;
        this.client = client;
        this.handler = handler;
    }

    @PostConstruct
    private void connect() {
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.add("Cookie", userSession.getCookie());
        try {
            client.execute(handler, webSocketHttpHeaders, URI.create(url));
        } catch (Exception e) {
            log.warning("Error while establishing WebSocket connection: " + e);
        }
    }
}
