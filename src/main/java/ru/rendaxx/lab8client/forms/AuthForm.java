package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.LoginClient;
import ru.rendaxx.lab8client.frame.MainFrame;
import ru.rendaxx.lab8client.frame.RegisterFrame;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UserCredentials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ResourceBundle;

@Log
@Component
@Scope("prototype")
@Getter
public class AuthForm implements SetTextListener {
    private JTextField usernameField;
    @Getter
    private JPanel authPanel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel errorLabel;
    @Getter
    private JButton registerButton;
    private JLabel registerButtonLabel;

    @Setter
    private RegisterFrame registrationFrame;

    private String lastUsedErrorBundle = "";

    private ApplicationContext applicationContext;

    @Autowired
    public AuthForm(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);


        loginButton.addActionListener(e -> {
            log.info("Clicked login button");
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            if (username.isEmpty() || password.length == 0) {
                return;
            }

            lastUsedErrorBundle = applicationContext.getBean(LoginClient.class).login(new UserCredentials(username, password));

            if (lastUsedErrorBundle.equals("form.login.success")) {
                lastUsedErrorBundle = "";
                if (registrationFrame != null) registrationFrame.dispose();
                SwingUtilities.windowForComponent(authPanel).dispose();
                Arrays.fill(password, (char) 0);
                EventQueue.invokeLater(() -> applicationContext.getBean(MainFrame.class));
            }

            ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/login");
            if (!lastUsedErrorBundle.isEmpty()) errorLabel.setText(resourceBundle.getString(lastUsedErrorBundle));
            else errorLabel.setText("");
        });
    }

    @Override
    public void setText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/login");
        loginButton.setText(resourceBundle.getString("form.login.button"));
        usernameLabel.setText(resourceBundle.getString("form.username.label"));
        passwordLabel.setText(resourceBundle.getString("form.password.label"));
        registerButton.setText(resourceBundle.getString("form.register.button"));
        registerButtonLabel.setText(resourceBundle.getString("form.register.label"));
        if (!lastUsedErrorBundle.isEmpty()) errorLabel.setText(resourceBundle.getString(lastUsedErrorBundle));
    }

    @Override
    public boolean isVisible() {
        return authPanel.isVisible();
    }
}
