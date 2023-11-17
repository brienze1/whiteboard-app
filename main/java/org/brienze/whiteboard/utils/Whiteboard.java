package org.brienze.whiteboard.utils;

import javax.swing.*;
import java.awt.*;

public class Whiteboard extends JPanel {

    public Whiteboard() {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
    }

    private DrawShape lastAddedComponent;

    public void add(Shape shape) {
        lastAddedComponent = new DrawShape(shape);

        super.add(lastAddedComponent);
    }

    public void updateComponent(int x, int y, int width, int height) {
        lastAddedComponent.updateComponent(x, y, width, height);
    }
}
