package org.brienze.whiteboard.api.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class WhiteboardState {

    public WhiteboardState(String name, LocalDateTime cleanedAt, Set<Shape> shapes) {
        this.name = name;
        this.cleanedAt = cleanedAt;
        this.shapes = shapes;
    }

    public WhiteboardState(String name) {
        this.name = name;
    }

    private final String name;
    private LocalDateTime cleanedAt;
    private Set<Shape> shapes;

    public String getName() {
        return name;
    }

    public LocalDateTime getCleanedAt() {
        return cleanedAt;
    }

    public void clear() {
        this.cleanedAt = LocalDateTime.now();
    }

    public boolean mustClear(WhiteboardState currentState) {
        return Optional.ofNullable(currentState.getCleanedAt())
                       .orElse(LocalDateTime.MIN)
                       .isAfter(Optional.ofNullable(this.cleanedAt).orElse(LocalDateTime.MIN));
    }

    public boolean mustUpdate(Map<UUID, Shape> shapeMap) {
        return !Optional.ofNullable(this.shapes)
                       .orElse(new HashSet<>())
                       .stream()
                       .filter(shape -> !shapeMap.containsKey(shape.getId()))
                       .peek(shape -> shapeMap.put(shape.getId(), shape))
                       .collect(Collectors.toSet())
                       .isEmpty();
    }

}
