package ru.rendaxx.lab8client.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
public class UserSession {
    private String username;
    private String cookie;
}
