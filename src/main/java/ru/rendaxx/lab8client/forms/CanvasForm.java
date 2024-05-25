package ru.rendaxx.lab8client.forms;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.canvas.OrganizationCanvas;
import ru.rendaxx.lab8client.canvas.OrganizationsImage;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.util.UpdateListener;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class CanvasForm implements UpdateListener {
    @Getter
    private JPanel panel1;
    private JScrollPane scrollPane;
//    OrganizationsImage image;

    ApplicationContext applicationContext;

    @Autowired
    public CanvasForm(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private void createUIComponents() {
        applicationContext.getBean(UpdateHandler.class).addListener(this);
//        canvasPanel = applicationContext.getBean(OrganizationCanvas.class);
//        canvasPanel.setMinimumSize(new Dimension(2000, 2000));
        Image image = applicationContext.getBean(OrganizationsImage.class);
        scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
    }


    @Override
    public void update() {
        SwingUtilities.updateComponentTreeUI(panel1);
    }
}
