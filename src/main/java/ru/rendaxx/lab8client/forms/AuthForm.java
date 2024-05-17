package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.LoginClient;
import ru.rendaxx.lab8client.frame.RegisterFrame;
import ru.rendaxx.lab8client.util.UserCredentials;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Log
@Component
@Scope("prototype")
public class AuthForm {
    private JTextField usernameField;
    @Getter
    private JPanel authPanel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel errorLabel;
    private JButton registerButton;

    private RegisterFrame registrationFrame;

    private ApplicationContext applicationContext;

    @Autowired
    public AuthForm(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        registerButton.addActionListener(e -> {
            log.info("Clicked register button");
            if (registrationFrame == null || !registrationFrame.isDisplayable()) {
                registrationFrame = applicationContext.getBean(RegisterFrame.class);
            }
        });

        loginButton.addActionListener(e -> {
            log.info("Clicked login button");
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            String response = applicationContext.getBean(LoginClient.class).login(new UserCredentials(username, password));

            if (response.equals("ye")) {

            }

            errorLabel.setText(response);
        });
    }
}
