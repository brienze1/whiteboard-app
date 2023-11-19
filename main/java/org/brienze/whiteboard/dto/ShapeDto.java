package org.brienze.whiteboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.brienze.whiteboard.enums.Type;
import org.brienze.whiteboard.model.*;

import java.util.UUID;

public class ShapeDto {

    public ShapeDto(UUID id,
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
    }

    public ShapeDto() {
    }

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("whiteboard_name")
    private String whiteboardName;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("temp_x")
    private int tempX;
    @JsonProperty("temp_y")
    private int tempY;
    @JsonProperty("x1")
    private int x1;
    @JsonProperty("y1")
    private int y1;
    @JsonProperty("x2")
    private int x2;
    @JsonProperty("y2")
    private int y2;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;
    @JsonProperty("text")
    private String text;

    public Shape toShape() {
        switch (type) {
            case RECTANGLE -> {
                return new Rectangle(id, whiteboardName, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
            }
            case CIRCLE -> {
                return new Circle(id, whiteboardName, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
            }
            case TEXT ->  {
                return new Text(id, whiteboardName, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
            }
            case LINE -> {
                return new Line(id, whiteboardName, x, y, tempX, tempY, x1, y1, x2, y2, width, height, text);
            }
        }
        throw new RuntimeException("shape not found");
    }

}
