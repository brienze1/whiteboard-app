package org.brienze.whiteboard.model;

import org.brienze.whiteboard.enums.Type;

import java.awt.*;
import java.util.UUID;

public class Line extends Shape{

    public Line(UUID id,
                  String whiteboardName,
                  int x,
                  int y,
                  int tempX,
                  int tempY,
                  int x1,
                  int y1,
                  int x2,
                  int y2,
                  int width,
                  int height,
                  String text) {
        super(id, whiteboardName, Type.LINE, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
    }

    public Line(String whiteboardName, int x, int y) {
        super(whiteboardName, Type.LINE, x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawLine(getX1(), getY1(), getX2(), getY2());
    }
}
