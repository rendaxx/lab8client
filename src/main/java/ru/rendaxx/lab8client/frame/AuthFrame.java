package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.action.ChangeToCzechAction;
import ru.rendaxx.lab8client.action.ChangeToRussianAction;
import ru.rendaxx.lab8client.forms.AuthForm;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.LocalePublisher;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class AuthFrame extends JFrame implements SetTextListener {

    private final ApplicationContext applicationContext;

    @Autowired
    public AuthFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(applicationContext.getBean(AuthForm.class).getAuthPanel());

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setText();
        setVisible(true);
    }

    @Override
    public void setText() {
        ResourceBundle loginResourceBundle = ResourceBundle.getBundle("bundles/login");
        setTitle(loginResourceBundle.getString("frame.label"));
    }
}
