package org.brienze.whiteboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.*;

public class WhiteboardState {

    public WhiteboardState(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    private String name;
    @JsonProperty("cleared_at")
    private LocalDateTime clearedAt;
    @JsonProperty("shapes")
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
        return Optional.ofNullable(this.shapes)
                       .orElse(new HashSet<>())
                       .stream()
                       .anyMatch(shape -> !shapeMap.containsKey(shape.getId()));
    }
}
