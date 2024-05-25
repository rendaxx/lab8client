package ru.rendaxx.lab8client.canvas;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.model.OrganizationService;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.util.UpdateListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static java.lang.Math.abs;

@Component
@Scope("prototype")
public class OrganizationsImage extends BufferedImage implements UpdateListener {

    private OrganizationService organizationService;
    private HashMap<String, Color> colorByUsername = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    public OrganizationsImage(OrganizationService organizationService, ApplicationContext applicationContext) {
        this(10000, 10000, TYPE_INT_RGB);
        this.organizationService = organizationService;
        this.applicationContext = applicationContext;
        applicationContext.getBean(UpdateHandler.class).addListener(this);
    }

    public OrganizationsImage(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    @PostConstruct
    private void paintOrganizations() {

        Graphics2D g = createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//        graphics2D.clearRect(0, 0, 10000, 10000);
        g.setColor(Color.BLACK);
        for (OrganizationDto org : organizationService.getOrganizationList()) {
            var x = abs(org.getX().intValue())*50+50;
            var y = abs(org.getY().intValue())*50+50;
            g.drawPolygon(new int[] {x, x + 25, x + 50}, new int[] {y, y - 25, y}, 3);
            g.drawRect(x+10, y, 30, 20);
        }
    }


    @Override
    public void update() {
        paintOrganizations();
    }
}
