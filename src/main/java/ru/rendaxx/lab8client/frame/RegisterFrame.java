package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.forms.RegisterForm;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.LocalePublisher;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class RegisterFrame extends JFrame implements SetTextListener {
    private ApplicationContext applicationContext;

    @Autowired
    public RegisterFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(applicationContext.getBean(RegisterForm.class).getRegisterPanel());

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);

        setText();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    @Override
    public void setText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/registration");
        setTitle(resourceBundle.getString("frame.label"));
    }
}
