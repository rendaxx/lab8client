package ru.rendaxx.lab8client.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.rendaxx.lab8client.model.CollectionService;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.util.LocalDateTypeAdapter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

@Log
@Component
public class UpdateHandler extends TextWebSocketHandler {

    CollectionService<OrganizationDto> collectionService;

    @Autowired
    public UpdateHandler(CollectionService<OrganizationDto> collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Message form server arrived  " + message);

        Type setType = new TypeToken<LinkedHashSet<OrganizationDto>>(){}.getType();

        Set<OrganizationDto> items = new GsonBuilder().
                registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create().fromJson(message.getPayload(), setType);

        collectionService.load(items);
    }
}
