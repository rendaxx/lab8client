package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.canvas.CanvasPanel;
import ru.rendaxx.lab8client.canvas.OrganizationsImage;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.model.OrganizationService;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Math.abs;
import static ru.rendaxx.lab8client.frame.AnimatedPolygon.createPolygon;

@Component
@Scope("prototype")
public class CanvasFrame extends JFrame implements SetTextListener, UpdateListener {

    private ApplicationContext applicationContext;
    private OrganizationService organizationService;
    CanvasPanel canvasPanel;
    JScrollPane jScrollPane;

    @Autowired
    public CanvasFrame(ApplicationContext applicationContext, OrganizationService organizationService) {
        this.applicationContext = applicationContext;
        this.organizationService = organizationService;
        canvasPanel = applicationContext.getBean(CanvasPanel.class);
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(UpdateHandler.class).addListener(this);
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));


        canvasPanel = applicationContext.getBean(CanvasPanel.class);
        canvasPanel.setPreferredSize(new Dimension(10000, 10000));
        jScrollPane = new JScrollPane(canvasPanel);

        add(jScrollPane);

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        update();
    }

    @Override
    public void setText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/main");
        setTitle(resourceBundle.getString("main.frame.title"));
    }

    @Override
    public void update() {
        for (OrganizationDto org : organizationService.getOrganizationList()) {
            if (!canvasPanel.has(org)) {
                AnimatedPolygon animatedPolygon = new AnimatedPolygon(canvasPanel, org);
                animatedPolygon.start();
                canvasPanel.addPrintedOrg(org);
            }
        }
        for (OrganizationDto org : canvasPanel.getPaintedObjects()) {
            if (!organizationService.getOrganizationList().contains(org)) {
                canvasPanel.getPaintedObjects().remove(org);
                canvasPanel.removePolygon(createPolygon(org));
            }
        }
    }



}
