package ru.rendaxx.lab8client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;

@SpringBootApplication
public class Lab8clientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Lab8clientApplication.class)
                .profiles("client")
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);

    }

}
