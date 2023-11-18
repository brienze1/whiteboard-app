package org.brienze.whiteboard.model;

import java.awt.*;

public class Text extends Shape {
    public Text(String test, int x, int y) {
        super(test, x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawString(getText(), 0, 13);
    }
}
