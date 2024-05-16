package ru.rendaxx.lab8client.frame;

import ru.rendaxx.lab8client.forms.AuthForm;
import ru.rendaxx.lab8client.forms.RegisterForm;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {
        initUI();
    }

    private void initUI() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/registration");
        setTitle(resourceBundle.getString("frameLabel"));

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(new RegisterForm().getRegisterPanel());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }
}
