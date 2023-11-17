package org.brienze.whiteboard.utils;

import javax.swing.*;
import java.awt.*;

public class DrawShape extends JPanel {

    public DrawShape(Shape shape) {
        this.shape = shape;
    }

    private final Shape shape;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        switch (shape.getShape()) {
            case "square" -> g.drawRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
            case "circle" -> g.drawOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
        }
    }

    public void updateComponent(int x, int y, int width, int height) {
        this.shape.setX(x);
        this.shape.setY(y);
        this.shape.setWidth(width);
        this.shape.setHeight(height);

        repaint();
    }
}
