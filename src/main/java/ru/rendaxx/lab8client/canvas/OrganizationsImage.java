package ru.rendaxx.lab8client.canvas;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.model.OrganizationService;
import ru.rendaxx.lab8client.model.object.OrganizationDto;
import ru.rendaxx.lab8client.util.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;
import static ru.rendaxx.lab8client.canvas.CanvasPanel.convertShapeToPolygon;

@Component
public class OrganizationsImage extends BufferedImage implements UpdateListener {

    private Set<OrganizationDto> printedOrganizations = new HashSet<>();
    private ApplicationContext applicationContext;
    Graphics2D g;

    @Autowired
    public OrganizationsImage(ApplicationContext applicationContext) {
        this(10000, 10000, TYPE_INT_RGB);
        this.applicationContext = applicationContext;
        g = createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public OrganizationsImage(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    public void paintShape(Shape s) {
        g.draw(s);
    }



    public boolean has(OrganizationDto organizationDto) {
        return printedOrganizations.contains(organizationDto);
    }

    public void animate(OrganizationDto org, JFrame parent) {
        var x = abs(org.getX().intValue())*50+50;
        var y = abs(org.getY().intValue())*50+50;
        Polygon polygonTriangle = new Polygon(new int[] {x, x + 25, x + 50}, new int[] {y, y - 25, y}, 3);
        Polygon polygonRectangle = new Polygon(new int[] {x+10, x + 40, x + 40, x + 10}, new int[] {y, y, y+20, y+20}, 4);
        g.setColor(Color.BLACK);
        g.fillPolygon(polygonTriangle);
        g.fillPolygon(polygonRectangle);

        float theta = 45f;

        Polygon t1 = polygonTriangle;
        Polygon t2 = polygonRectangle;
        while (theta <= 360) {
            float radiansTheta = (float) Math.toRadians(theta);

            AffineTransform transform = new AffineTransform();

            transform.rotate(radiansTheta, t1.xpoints[0], t1.ypoints[0]);
            Shape transformedT1 = transform.createTransformedShape(t1);

            Polygon transformedTriangle = convertShapeToPolygon(transformedT1);

            transform.rotate(radiansTheta, t2.xpoints[0], t2.ypoints[0]);
            Shape transformedT2 = transform.createTransformedShape(t2);

            Polygon transformedRectangle = convertShapeToPolygon(transformedT2);

            g.setColor(Color.WHITE);
            g.fillPolygon(t1);
            g.fillPolygon(t2);

            g.setColor(Color.BLACK);
            g.fillPolygon(transformedTriangle);
            g.fillPolygon(transformedRectangle);
            parent.revalidate();
            parent.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            t1 = transformedTriangle;
            t2 = transformedRectangle;
            theta += 45f;
        }

        g.setColor(Color.WHITE);
        g.fillRect(x, y, 50, 50);

        g.setColor(Color.BLACK);
        g.fillPolygon(polygonTriangle);
        g.fillPolygon(polygonRectangle);
        parent.revalidate();
        parent.repaint();
        printedOrganizations.add(org);
    }

    @Override
    public void update() {

    }
}
