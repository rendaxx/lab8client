package ru.rendaxx.lab8client.model.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rendaxx.lab8client.model.object.OrganizationDto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
public class LoadMessage {
    private Collection<OrganizationDto> payload;
}
