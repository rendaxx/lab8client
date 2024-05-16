package ru.rendaxx.lab8client.frame;

import jakarta.annotation.PostConstruct;
import ru.rendaxx.lab8client.forms.AuthForm;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class AuthFrame extends JFrame{

    public AuthFrame() {
        initUI();
    }

    private void initUI() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/login");
        setTitle(resourceBundle.getString("frameLabel"));

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(new AuthForm().getAuthPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
