package ru.rendaxx.lab8client.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.canvas.CanvasPanel;
import ru.rendaxx.lab8client.client.DeleteCommandClient;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.forms.AnimatedPolygon;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.model.OrganizationService;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class CanvasFrame extends JFrame implements SetTextListener, UpdateListener {

    private ApplicationContext applicationContext;
    private OrganizationService organizationService;
    final CanvasPanel canvasPanel;
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

        canvasPanel.setPreferredSize(new Dimension(10000, 10000));

        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                List<OrganizationDto> list = new ArrayList<>();
                for (var entry : canvasPanel.getPolygons().entrySet()) {
                    if (entry.getValue().contains(e.getPoint())) {
                        list.add(entry.getKey());
                    }
                }
                JPopupMenu popupMenu = new JPopupMenu();
                for (OrganizationDto org : list) {
                    JMenu jMenu = new JMenu(org.getName());
                    popupMenu.add(jMenu);
                    JMenuItem deleteItem = new JMenuItem("Delete");
                    deleteItem.addActionListener(event->{
                        applicationContext.getBean(DeleteCommandClient.class).delete(org);
                    });

                    JMenuItem updateItem = new JMenuItem("Update");
                    updateItem.addActionListener(event -> {
                        UpdateCommandFrame updateFrame = applicationContext.getBean(UpdateCommandFrame.class);
                        updateFrame.setFields(org);
                    });

                    jMenu.add(deleteItem);
                    jMenu.add(updateItem);
                }
                JMenuItem addCommand = new JMenuItem("Add new organization");
                addCommand.addActionListener(event -> {
                    applicationContext.getBean(AddCommandFrame.class);
                });
                popupMenu.add(addCommand);
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        jScrollPane = new JScrollPane(canvasPanel);

        add(jScrollPane);

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setText();
        setVisible(true);
        update();
    }

    @Override
    public void setText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/main");
        setTitle(resourceBundle.getString("form.canvas.button"));
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

        Iterator<OrganizationDto> it = canvasPanel.getPaintedObjects().iterator();
        while (it.hasNext()) {
            OrganizationDto org = it.next();
            if (!organizationService.getOrganizationList().contains(org)) {
                it.remove();
                canvasPanel.removePolygon(org);
            }
        }
        canvasPanel.repaint();
    }
}
