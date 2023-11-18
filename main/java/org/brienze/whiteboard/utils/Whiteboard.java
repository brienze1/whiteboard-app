package org.brienze.whiteboard.utils;

import org.brienze.whiteboard.model.Shape;
import org.brienze.whiteboard.model.WhiteboardState;
import org.brienze.whiteboard.service.WhiteboardWebService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class Whiteboard extends JPanel {

    private final WhiteboardWebService whiteboardWebService;
    private Map<UUID, Shape> shapeMap = new HashMap<>();
    private WhiteboardState whiteboardState;

    public Whiteboard(WhiteboardWebService whiteboardWebService) {
        this.whiteboardWebService = whiteboardWebService;
    }

    private Map<UUID, Shape> getShapeMap() {
        if (this.shapeMap == null) {
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

    public void save(UUID key, int x, int y) {
        this.update(key, x, y);

        if (whiteboardState != null) {
            whiteboardWebService.save(whiteboardState.getName(), this.shapeMap.get(key));
        }
    }

    @Scheduled(fixedDelay = 200)
    public void getWhiteboardState() {
        if (whiteboardState != null) {
            WhiteboardState currentState = whiteboardWebService.getState(whiteboardState.getName());

            if (whiteboardState.mustClear(currentState)) {
                this.repaint();
                whiteboardState.clear();
            }
            if (currentState.mustUpdate(shapeMap)) {
                this.repaint();
            }

            whiteboardState = currentState;
        }
    }

    public void load(String name) {
        this.clear();
        this.whiteboardState = new WhiteboardState(name);
        System.out.println("load");
    }

    @Override
    public void repaint() {
        super.repaint();
        this.getShapeMap().values().forEach(Shape::repaint);
        System.out.println("repaint");
    }

    public void clear() {
        super.repaint();
        this.getShapeMap().values().forEach(this::remove);
        this.getShapeMap().clear();
        System.out.println("clear");
    }

}
