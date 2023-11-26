package org.brienze.whiteboard.api.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class IconMaker implements Icon {
    private final String type;
    private int width;
    private int height;
    private double diameter;
    private int length;
    private final int strokeWidth;


    public IconMaker(String type, int width, int height, int strokeWidth) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.strokeWidth = strokeWidth;
    }

    public IconMaker(String type, double diameter, int strokeWidth) {
        this.type = type;
        this.diameter = diameter;
        this.strokeWidth = strokeWidth;
    }

    public IconMaker(String type, int length, int strokeWidth) {
        this.type = type;
        this.length = length;
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void paintIcon(java.awt.Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(strokeWidth));

        Shape shape;

        switch (this.type) {
            case "Rectangle" -> {
                shape = new Rectangle2D.Double(x, y, width - 1, height - 1);
                g2d.setColor(Color.BLACK);
                g2d.draw(shape);
            }
            case "Circle" -> {
                shape = new Ellipse2D.Double(x - strokeWidth - 7, y - strokeWidth - 7, diameter - strokeWidth, diameter - strokeWidth);
                g2d.setColor(Color.BLACK);
                g2d.draw(shape);
            }
            case "Line" -> {
                int halfStroke = strokeWidth / 2;
                shape = new Line2D.Double(x - halfStroke - 8, y - halfStroke - 8, x + length - halfStroke, y + length - halfStroke);
                g2d.setColor(Color.BLACK);
                g2d.draw(shape);
            }
        }

        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
