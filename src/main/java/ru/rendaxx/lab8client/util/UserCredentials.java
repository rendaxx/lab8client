package ru.rendaxx.lab8client.util;

import lombok.Value;

@Value
public class UserCredentials {
    private String username;
    private char[] rawPassword;
}
