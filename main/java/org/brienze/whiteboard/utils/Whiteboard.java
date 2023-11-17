package org.brienze.whiteboard.utils;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class Whiteboard extends JPanel {

    private Map<UUID, Shape> shapeMap = new HashMap<>();

    private Map<UUID, Shape> getShapeMap() {
        if(this.shapeMap == null) {
            this.shapeMap = new HashMap<>();
        }
        return this.shapeMap;
    }

    public UUID draw(Shape shape) {
        UUID key = UUID.randomUUID();
        shapeMap.put(key, shape);
        this.add(shapeMap.get(key));
        shapeMap.get(key).repaint();
        return key;
    }

    public void update(UUID key, int realX, int realY) {
        shapeMap.get(key).update(realX, realY);
        shapeMap.get(key).repaint();
    }

    @Override
    public void repaint() {
        super.repaint();
        this.getShapeMap().values().forEach(this::remove);
        this.getShapeMap().clear();
    }
}
