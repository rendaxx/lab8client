package ru.rendaxx.lab8client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.rendaxx.lab8client.frame.AddCommandFrame;
import ru.rendaxx.lab8client.frame.AuthFrame;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class SwingApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException ignore) {}

        var context = new SpringApplicationBuilder(SwingApp.class).headless(false)
                .web(WebApplicationType.NONE).run(args);

        EventQueue.invokeLater(() -> {
            context.getBean(AuthFrame.class);
        });
    }
}