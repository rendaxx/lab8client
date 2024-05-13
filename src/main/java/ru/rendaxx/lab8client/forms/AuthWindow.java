package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Getter
public class AuthWindow {
    private JTextField usernameField;
    private JPanel loginPanel;
    private JPasswordField rawPasswordField;
    private JButton proceedLogin;

}
