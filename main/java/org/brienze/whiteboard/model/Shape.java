package org.brienze.whiteboard.model;

import javax.swing.*;
import java.util.UUID;

public abstract class Shape extends JPanel {

    public Shape(int x, int y) {
        this.id = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.tempX = x;
        this.tempY = y;
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.width = 0;
        this.height = 0;
        this.setBounds(x, y, width, height);
        this.setOpaque(false);
    }

    public Shape(String text, int x, int y) {
        this.id = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.tempX = x;
        this.tempY = y;
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.width = 400;
        this.height = 20;
        this.text = text;
        this.setBounds(x, y, width, height);
        this.setOpaque(false);
    }

    private final UUID id;
    private final int x;
    private final int y;
    private int tempX;
    private int tempY;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private String text;

    public UUID getId() {
        return id;
    }

    public int getX() {
        return tempX;
    }

    public int getY() {
        return tempY;
    }

    protected int getX1() {
        return x1;
    }

    protected int getY1() {
        return y1;
    }

    protected int getX2() {
        return x2;
    }

    protected int getY2() {
        return y2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public void update(int realX, int realY) {
        if (text != null) {
            tempX = realX;
            tempY = realY;

            this.setBounds(tempX, tempY, this.width, this.height);

            return;
        }

        int width = realX - x;
        int height = realY - y;

        tempX = x;
        tempY = y;

        if (width < 0) {
            width *= -1;
            tempX -= width;
            x1 = width;
            x2 = 0;
        } else {
            x2 = width;
            x1 = 0;
        }

        if (height < 0) {
            height *= -1;
            tempY -= height;
            y1 = height;
            y2 = 0;
        } else {
            y2 = height;
            y1 = 0;
        }

        this.width = width;
        this.height = height;

        this.setBounds(tempX, tempY, width, height);
    }
}
