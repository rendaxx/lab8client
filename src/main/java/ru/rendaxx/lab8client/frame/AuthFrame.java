package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.action.ChangeLanguageAction;
import ru.rendaxx.lab8client.action.ChangeToCzechAction;
import ru.rendaxx.lab8client.action.ChangeToRussianAction;
import ru.rendaxx.lab8client.forms.AuthForm;
import ru.rendaxx.lab8client.util.LocaleChangeListener;
import ru.rendaxx.lab8client.util.LocalePublisher;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class AuthFrame extends JFrame implements LocaleChangeListener {

    private ApplicationContext applicationContext;

    @Autowired
    public AuthFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        ResourceBundle loginResourceBundle = ResourceBundle.getBundle("bundles/login");
        setTitle(loginResourceBundle.getString("frame.label"));

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(applicationContext.getBean(AuthForm.class).getAuthPanel());

        final JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        ResourceBundle generalResourceBundle = ResourceBundle.getBundle("bundles/general");

        final JMenu jMenuLang = new JMenu(generalResourceBundle.getString("form.menu.language"));
        jMenuLang.add(new JMenuItem(applicationContext.getBean(ChangeToRussianAction.class)));
        jMenuLang.add(new JMenuItem(applicationContext.getBean(ChangeToCzechAction.class)));
        jMenuLang.add(new JMenuItem());
        jMenuLang.add(new JMenuItem());
        menuBar.add(jMenuLang);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void localeChanged() {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
