package org.brienze.whiteboard.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.brienze.whiteboard.api.model.WhiteboardState;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WhiteboardStateDto {

    public WhiteboardStateDto(String name) {
        this.name = name;
    }

    public WhiteboardStateDto() {
    }

    @JsonProperty("name")
    private String name;
    @JsonProperty("cleared_at")
    private LocalDateTime clearedAt;
    @JsonProperty("shapes")
    private Set<ShapeDto> shapes;

    public WhiteboardState toWhiteboardState() {
        return new WhiteboardState(name,
                                   clearedAt,
                                   Optional.ofNullable(shapes).orElse(new HashSet<>()).stream().map(ShapeDto::toShape).collect(Collectors.toSet()));
    }
}
