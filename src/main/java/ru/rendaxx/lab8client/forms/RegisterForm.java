package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.RegistrationClient;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UserCredentials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Log
@Component
@Scope("prototype")
public class RegisterForm implements SetTextListener {
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

    private String lastUsedErrorBundle = "";

    private ApplicationContext applicationContext;

    @Autowired
    public RegisterForm(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] mainPassword = mainPasswordField.getPassword();
            char[] repeatPassword = repeatPasswordField.getPassword();

            ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/registration");

            if (username.isEmpty() || username.isBlank() || !Pattern.compile("[a-zA-Z0-9]+").matcher(username).find()) {
                lastUsedErrorBundle = "form.username.error.notValid";
                errorLabel.setText(resourceBundle.getString(lastUsedErrorBundle)); // TODO write requirements for password
                errorLabel.setForeground(Color.RED);
                return;
            }

            if (mainPassword.length < 8) {
                lastUsedErrorBundle = "form.password.error.tooShort";
                errorLabel.setText(resourceBundle.getString(lastUsedErrorBundle));
                errorLabel.setForeground(Color.RED);
                return;
            }

            if (!Arrays.equals(mainPassword, repeatPassword)) {
                lastUsedErrorBundle = "form.password.error.notMatching";
                errorLabel.setText(resourceBundle.getString(lastUsedErrorBundle));
                errorLabel.setForeground(Color.RED);
                return;
            }

            lastUsedErrorBundle = "";
            errorLabel.setText(lastUsedErrorBundle);

            lastUsedErrorBundle = applicationContext.getBean(RegistrationClient.class).register(new UserCredentials(username, mainPassword));
            errorLabel.setText(resourceBundle.getString(lastUsedErrorBundle));
            errorLabel.setForeground(Color.BLACK);
        });
    }

    @Override
    public void setText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/registration");
        registerButton.setText(resourceBundle.getString("form.register.button"));
        usernameLabel.setText(resourceBundle.getString("form.username.label"));
        mainPasswordLabel.setText(resourceBundle.getString("form.password.main.label"));
        repeatPasswordLabel.setText(resourceBundle.getString("form.password.repeat.label"));
        if (!lastUsedErrorBundle.isEmpty()) errorLabel.setText(resourceBundle.getString(lastUsedErrorBundle));
    }

    @Override
    public boolean isVisible() {
        return registerPanel.isVisible();
    }
}
