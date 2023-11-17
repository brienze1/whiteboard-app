package org.brienze.whiteboard.utils;

import java.awt.*;

public class Circle extends Shape {
    public Circle(int x, int y) {
        super(x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
