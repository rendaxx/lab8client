package ru.rendaxx.lab8client.util;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class UserCredentials {
    private String username;
    private char[] rawPassword;
}
