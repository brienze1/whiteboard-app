package org.brienze.whiteboard.api.viewmodel;

import org.brienze.whiteboard.api.enums.Type;
import org.brienze.whiteboard.api.model.*;
import org.brienze.whiteboard.api.service.WhiteboardWebService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class WhiteboardViewModel extends JPanel {

    private final WhiteboardWebService whiteboardWebService;
    private Map<UUID, Shape> shapeMap = new HashMap<>();
    private WhiteboardState whiteboardState;

    public WhiteboardViewModel(WhiteboardWebService whiteboardWebService) {
        this.whiteboardWebService = whiteboardWebService;
    }

    public UUID draw(Type type, String text, int x, int y) {
        Shape shape = null;
        switch (type) {
            case RECTANGLE -> shape = new Rectangle(this.whiteboardState.getName(), x, y);
            case CIRCLE -> shape = new Circle(this.whiteboardState.getName(), x, y);
            case TEXT -> shape = new Text(this.whiteboardState.getName(), text, x, y);
            case LINE -> shape = new Line(this.whiteboardState.getName(), x, y);
        }

        getShapeMap().put(shape.getId(), shape);
        this.add(this.getShapeMap().get(shape.getId()));
        getShapeMap().get(shape.getId()).repaint();

        return shape.getId();
    }

    public void update(UUID key, int realX, int realY) {
        if (getShapeMap().get(key) != null) {
            getShapeMap().get(key).update(realX, realY);
            getShapeMap().get(key).repaint();
        }
    }

    public void save(UUID key, int x, int y) {
        this.update(key, x, y);

        if (whiteboardState != null) {
            whiteboardWebService.save(whiteboardState.getName(), this.shapeMap.get(key));
        }
    }

    @Scheduled(fixedDelay = 500)
    public void getWhiteboardState() {
        if (whiteboardState != null) {
            WhiteboardState currentState = whiteboardWebService.getState(whiteboardState.getName());

            if (whiteboardState.mustClear(currentState)) {
                this.clearInternal();
                whiteboardState.clear();
            }
            if (currentState.mustUpdate(shapeMap)) {
                this.repaint();
            }

            whiteboardState = currentState;
        }
    }

    @Override
    public String getName() {
        return Optional.ofNullable(this.whiteboardState).map(WhiteboardState::getName).orElse(null);
    }

    public void load(String name) {
        this.clearInternal();
        this.whiteboardState = new WhiteboardState(name);
        this.getWhiteboardState();
        System.out.println("load");
    }

    @Override
    public void repaint() {
        super.repaint();
        this.getShapeMap().values().forEach(shape -> {
            this.add(shape);
            shape.repaint();
        });
        System.out.println("repaint");
    }

    public void clear() {
        if (whiteboardState != null) {
            this.whiteboardState = whiteboardWebService.clearState(this.whiteboardState.getName());
            clearInternal();
            System.out.println("clear");
        }
    }

    private void clearInternal() {
        super.repaint();
        this.getShapeMap().values().forEach(this::remove);
        this.getShapeMap().clear();
        System.out.println("clearInternal");
    }

    private Map<UUID, Shape> getShapeMap() {
        if (this.shapeMap == null) {
            this.shapeMap = new HashMap<>();
        }
        return this.shapeMap;
    }

}
