package org.brienze.whiteboard.utils;

import java.awt.*;

public class Rectangle extends Shape {
    public Rectangle(int x, int y) {
        super(x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

}
