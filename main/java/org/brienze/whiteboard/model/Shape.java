package org.brienze.whiteboard.model;

import org.brienze.whiteboard.dto.ShapeDto;
import org.brienze.whiteboard.enums.Type;

import javax.swing.*;
import java.util.UUID;

public class Shape extends JPanel {

    protected Shape(UUID id,
                 String whiteboardName,
                 Type type,
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
        this.id = id;
        this.whiteboardName = whiteboardName;
        this.type = type;
        this.x = x;
        this.y = y;
        this.tempX = tempX;
        this.tempY = tempY;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = width;
        this.height = height;
        this.text = text;
        this.setBounds(tempX, tempY, width, height);
        this.setOpaque(false);
    }

    public Shape(String whiteboardName, Type type, int x, int y) {
        this.id = UUID.randomUUID();
        this.whiteboardName = whiteboardName;
        this.type = type;
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
        this.setBounds(tempX, tempY, width, height);
        this.setOpaque(false);
    }

    public Shape(String whiteboardName, String text, int x, int y) {
        this.id = UUID.randomUUID();
        this.whiteboardName = whiteboardName;
        this.type = Type.TEXT;
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
        this.setBounds(tempX, tempY, width, height);
        this.setOpaque(false);
    }

    private final UUID id;
    private final String whiteboardName;
    private final Type type;
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

    public ShapeDto toDto() {
        return new ShapeDto(id, whiteboardName, type, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
    }
}
