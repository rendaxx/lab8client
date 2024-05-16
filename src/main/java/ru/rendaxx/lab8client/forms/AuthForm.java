package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.extern.java.Log;
import ru.rendaxx.lab8client.client.LoginClient;
import ru.rendaxx.lab8client.frame.RegisterFrame;
import ru.rendaxx.lab8client.util.UserCredentials;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Log
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
    private JLabel registerLabel;

    private RegisterFrame registrationFrame;

    public AuthForm() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Clicked register button");
                if (registrationFrame == null || !registrationFrame.isDisplayable()) {
                    registrationFrame = new RegisterFrame();
                }
            }
        });

        loginButton.addActionListener(e -> {
            log.info("Clicked login button");
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            String response = new LoginClient().login(new UserCredentials(username, password));

            if (response.equals("ye")) {

            }

            errorLabel.setText(response);
        });
    }
}
