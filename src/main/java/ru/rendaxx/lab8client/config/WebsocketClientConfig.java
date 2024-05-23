package ru.rendaxx.lab8client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import ru.rendaxx.lab8client.client.UpdateHandler;

@Configuration
public class WebsocketClientConfig {


    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }
}
