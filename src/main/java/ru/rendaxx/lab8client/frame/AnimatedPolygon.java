package ru.rendaxx.lab8client.frame;

import ru.rendaxx.lab8client.canvas.CanvasPanel;
import ru.rendaxx.lab8client.model.object.OrganizationDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class AnimatedPolygon implements ActionListener {

    CanvasPanel canvasPanel;
    OrganizationDto organizationDto;
    Polygon polygon;
    Timer timer;
    private static final int ROTATE_DEGREE = 36;
    int degree = 0;

    public AnimatedPolygon(CanvasPanel canvasPanel, OrganizationDto organizationDto) {
        this.canvasPanel = canvasPanel;
        this.organizationDto = organizationDto;
        this.polygon = createPolygon(organizationDto);
        timer = new Timer(40, this);
    }

    public static Polygon createPolygon(OrganizationDto org) {
        var x = abs(org.getX().intValue())*50;
        var y = abs(org.getY().intValue())*50;
        return new Polygon(new int[] {x + 25, x + 50, x + 40, x + 40, x + 10, x + 10, x}, new int[] {y, y + 30, y + 30, y + 50, y + 50, y + 30, y + 30}, 7);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvasPanel.addPolygon(organizationDto, polygon);
        canvasPanel.repaint();
        if (degree >= 360) {
            timer.stop();
            return;
        }

        nextPolygon();

        degree += ROTATE_DEGREE;
    }

    private void nextPolygon() {
        canvasPanel.removePolygon(organizationDto);
        polygon = rotatePolygon(polygon, (float) -Math.toRadians(ROTATE_DEGREE));
        canvasPanel.addPolygon(organizationDto, polygon);
    }

    private Polygon rotatePolygon(Polygon polygon, float radiansTheta) {
        AffineTransform transform = new AffineTransform();

        transform.rotate(radiansTheta, polygon.xpoints[0], polygon.ypoints[0]);
        Shape transformedT1 = transform.createTransformedShape(polygon);
        return convertShapeToPolygon(transformedT1);
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

    public void start() {
        timer.start();
    }
}
