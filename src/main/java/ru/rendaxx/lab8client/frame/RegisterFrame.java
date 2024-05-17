package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.forms.RegisterForm;
import ru.rendaxx.lab8client.util.LocaleChangeListener;
import ru.rendaxx.lab8client.util.LocalePublisher;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class RegisterFrame extends JFrame implements LocaleChangeListener {
    private ApplicationContext applicationContext;

    @Autowired
    public RegisterFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/registration");
        setTitle(resourceBundle.getString("frame.label"));

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(applicationContext.getBean(RegisterForm.class).getRegisterPanel());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    @Override
    public void localeChanged() {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
