package ru.rendaxx.lab8client.forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.action.ChangeToCatalanAction;
import ru.rendaxx.lab8client.action.ChangeToCzechAction;
import ru.rendaxx.lab8client.action.ChangeToRussianAction;
import ru.rendaxx.lab8client.action.ChangeToSpanishAction;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;

import javax.swing.*;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class DefaultMenuBar extends JMenuBar implements SetTextListener {

    private JMenu jMenuLang;
    private ApplicationContext applicationContext;


    @Autowired
    public DefaultMenuBar(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    public void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        jMenuLang = new JMenu();

        jMenuLang.add(applicationContext.getBean(ChangeToRussianAction.class));
        jMenuLang.add(applicationContext.getBean(ChangeToCzechAction.class));
        jMenuLang.add(applicationContext.getBean(ChangeToCatalanAction.class));
        jMenuLang.add(applicationContext.getBean(ChangeToSpanishAction.class));

        add(jMenuLang);

        setText();
    }


    @Override
    public void setText() {
        ResourceBundle generalResourceBundle = ResourceBundle.getBundle("bundles/general");
        jMenuLang.setText(generalResourceBundle.getString("form.menu.language"));
    }
}
