package ru.rendaxx.lab8client.canvas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.model.OrganizationService;
import ru.rendaxx.lab8client.model.object.OrganizationDto;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

@Component
@Scope("prototype")
public class OrganizationCanvas extends JPanel {

    OrganizationService organizationService;
    ApplicationContext applicationContext;

    @Autowired
    public OrganizationCanvas(OrganizationService organizationService, ApplicationContext applicationContext) {
        this.organizationService = organizationService;
        this.applicationContext = applicationContext;
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        for (OrganizationDto org : organizationService.getOrganizationList()) {
//            int x = abs(org.getX().intValue()) + 12;
//            int y = abs(org.getY().intValue()) + 12;
//            g.drawString(org.getName(), x, y);
//        }
////        g.drawString("abacaba", -100, 100);
//        g.drawString("abacaba", 1000, 100);
//    }

//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(applicationContext.getBean(OrganizationsImage.class), 0, 0, this);
//    }
}
