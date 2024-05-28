package ru.rendaxx.lab8client.canvas;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.model.object.OrganizationDto;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.PathIterator;
import java.util.*;

import static java.lang.Math.abs;

@Component
@Scope("prototype")
public class CanvasPanel extends JPanel {
    @Getter
    private Set<Polygon> polygons = new LinkedHashSet<>();
    private HashMap<String, Color> colorByUsername = new HashMap<>();
    @Getter
    private Set<OrganizationDto> paintedObjects = new HashSet<>();
    private ApplicationContext applicationContext;

    @Autowired
    public CanvasPanel(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Polygon s : polygons) {
            g2.fillPolygon(s);
        }
    }

    public void addPolygon(Polygon s) {
        polygons.add(s);
    }

    public void removePolygon(Polygon s) {
        polygons.remove(s);
    }

    public void addPrintedOrg(OrganizationDto org) {
        paintedObjects.add(org);
    }

    public boolean has(OrganizationDto organizationDto) {
        return paintedObjects.contains(organizationDto);
    }

    public void removeInvalid(Set<OrganizationDto> set) {

    }

    public static Polygon convertShapeToPolygon(Shape shape) {
        java.util.List<Point> points = new ArrayList<>();
        PathIterator pi = shape.getPathIterator(null);
        while (!pi.isDone()) {
            double[] coords = new double[6];
            int type = pi.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO, PathIterator.SEG_LINETO:
                    points.add(new Point((int) coords[0], (int) coords[1]));
                    break;
                default:
                    break; // Ignore other types of segments
            }
            pi.next();
        }

        // Assuming the shape forms a polygon, we close the loop by adding the last point to the first
        if (!points.isEmpty()) {
            points.add(points.getFirst()); // Close the polygon
        }

        // Extracting x and y points separately for creating a Polygon
        int[] xpoints = new int[points.size()];
        int[] ypoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xpoints[i] = points.get(i).x;
            ypoints[i] = points.get(i).y;
        }

        return new Polygon(xpoints, ypoints, points.size());
    }
}
