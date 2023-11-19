package org.brienze.whiteboard.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class WhiteboardState {

    public WhiteboardState(String name, LocalDateTime clearedAt, Set<Shape> shapes) {
        this.name = name;
        this.clearedAt = clearedAt;
        this.shapes = shapes;
    }

    public WhiteboardState(String name) {
        this.name = name;
    }

    private final String name;
    private LocalDateTime clearedAt;
    private Set<Shape> shapes;

    public String getName() {
        return name;
    }

    public LocalDateTime getClearedAt() {
        return clearedAt;
    }

    public void clear() {
        this.clearedAt = LocalDateTime.now();
    }

    public boolean mustClear(WhiteboardState currentState) {
        return Optional.ofNullable(currentState.getClearedAt())
                       .orElse(LocalDateTime.MIN)
                       .isAfter(Optional.ofNullable(this.clearedAt).orElse(LocalDateTime.MIN));
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
