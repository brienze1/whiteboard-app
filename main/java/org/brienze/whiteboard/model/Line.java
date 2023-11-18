package org.brienze.whiteboard.model;

import java.awt.*;

public class Line extends Shape{

    public Line(int x, int y) {
        super(x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawLine(getX1(), getY1(), getX2(), getY2());
    }
}
