package ru.rendaxx.lab8client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.rendaxx.lab8client.forms.AuthWindow;

import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

@SpringBootApplication
public class SwingApp extends JFrame {

    public SwingApp() {
        initUI();
    }

    private void initUI() {
        AuthWindow authWindow = new AuthWindow();

        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        layeredPane.add(authWindow.getLoginPanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createLayout(JComponent... arg) {

        var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );
    }

    public static void main(String[] args) {
        var context = new SpringApplicationBuilder(SwingApp.class).headless(false)
                .web(WebApplicationType.NONE).run(args);

        EventQueue.invokeLater(() -> {
            SwingApp ex = context.getBean(SwingApp.class);
            ex.setVisible(true);
        });
    }
}