package ru.rendaxx.lab8client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.rendaxx.lab8client.forms.AuthForm;
import ru.rendaxx.lab8client.frame.AuthFrame;

import javax.swing.*;
import java.awt.EventQueue;
import java.util.ResourceBundle;

@SpringBootApplication
public class SwingApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException ignore) {}

        var context = new SpringApplicationBuilder(SwingApp.class).headless(false)
                .web(WebApplicationType.NONE).run(args);

        context.getBean(AuthFrame.class);
//        EventQueue.invokeLater(() -> {
//            SwingApp ex = context.getBean(SwingApp.class);
//            ex.setVisible(true);
//        });
    }
}