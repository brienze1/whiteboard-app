package org.brienze.whiteboard.api.model;

import org.brienze.whiteboard.api.enums.Type;

import java.awt.*;
import java.util.UUID;

public class Text extends Shape {

    public Text(UUID id,
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
        super(id, whiteboardName, Type.TEXT, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
    }

    public Text(String whiteboardName,String test, int x, int y) {
        super(whiteboardName, test, x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawString(getText(), 0, 13);
    }
}
