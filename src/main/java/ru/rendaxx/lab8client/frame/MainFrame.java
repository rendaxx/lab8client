package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.forms.MainForm;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class MainFrame extends JFrame implements SetTextListener, UpdateListener {

    private final ApplicationContext applicationContext;

    @Autowired
    public MainFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);
        applicationContext.getBean(UpdateHandler.class).addListener(this);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        getContentPane().add(applicationContext.getBean(MainForm.class).getRootPanel());

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setText();
        pack();
        setVisible(true);
    }

    @Override
    public void setText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/main");
        setTitle(resourceBundle.getString("main.frame.title"));
    }

    @Override
    public void update() {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
