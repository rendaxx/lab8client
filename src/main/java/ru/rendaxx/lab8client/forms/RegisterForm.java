package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.extern.java.Log;
import ru.rendaxx.lab8client.client.RegistrationClient;
import ru.rendaxx.lab8client.util.UserCredentials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Log
public class RegisterForm {
    private JTextField usernameField;
    @Getter
    private JPanel registerPanel;
    private JPasswordField mainPasswordField;
    private JPasswordField repeatPasswordField;
    private JButton registerButton;
    private JLabel usernameLabel;
    private JLabel mainPasswordLabel;
    private JLabel repeatPasswordLabel;
    private JLabel errorLabel;
    private JLabel usernameErrorLabel;


    public RegisterForm() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] mainPassword = mainPasswordField.getPassword();
                char[] repeatPassword = repeatPasswordField.getPassword();

                ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/registration");

                if (username.isEmpty() || username.isBlank() || !Pattern.compile("[a-zA-Z0-9]+").matcher(username).find()) {
                    errorLabel.setText(resourceBundle.getString("errorUsernameIsNotValid")); // TODO write requirements for password
                    errorLabel.setForeground(Color.RED);
                    return;
                }

                if (mainPassword.length < 8) {
                    errorLabel.setText(resourceBundle.getString("errorShortPassword"));
                    errorLabel.setForeground(Color.RED);
                    return;
                }

                if (!Arrays.equals(mainPassword, repeatPassword)) {
                    errorLabel.setText(resourceBundle.getString("errorPasswordAreNotMatching"));
                    errorLabel.setForeground(Color.RED);
                    return;
                }

                errorLabel.setText("");

                String response = new RegistrationClient().register(new UserCredentials(username, mainPassword));

                errorLabel.setText(response);
                errorLabel.setForeground(Color.BLACK);
            }
        });
    }
}
