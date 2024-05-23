package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.forms.AddCommandForm;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.forms.MainForm;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class AddCommandFrame extends JFrame implements SetTextListener {

    private final ApplicationContext applicationContext;

    @Autowired
    public AddCommandFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(applicationContext.getBean(AddCommandForm.class).getRootPanel());

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void setText() {

    }
}
