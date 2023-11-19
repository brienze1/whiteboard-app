package org.brienze.whiteboard.model;

import org.brienze.whiteboard.enums.Type;

import java.awt.*;
import java.util.UUID;

public class Rectangle extends Shape {

    public Rectangle(UUID id,
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
        super(id, whiteboardName, Type.RECTANGLE, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
    }

    public Rectangle(String whiteboardName, int x, int y) {
        super(whiteboardName, Type.RECTANGLE, x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

}
