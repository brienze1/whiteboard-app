package org.brienze.whiteboard.api.service;

import org.brienze.whiteboard.api.dto.ShapeDto;
import org.brienze.whiteboard.api.dto.WhiteboardStateDto;
import org.brienze.whiteboard.api.model.Shape;
import org.brienze.whiteboard.api.model.WhiteboardState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WhiteboardWebService {

    @Value("${whiteboard.api.url:http://localhost:8080/whiteboards}")
    private String url;

    private final RestTemplate restTemplate;

    public WhiteboardWebService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    @Retryable(retryFor = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public void save(String name, Shape shape) {
        restTemplate.exchange(url + "/" + name + "/shapes", HttpMethod.POST, new HttpEntity<>(shape.toDto(), null), ShapeDto.class);
        System.out.println("Save Shape for whiteboard: " + name);
    }

    public WhiteboardState getState(String name) {
        WhiteboardStateDto state;
        try {
            state = restTemplate.exchange(url + "/" + name, HttpMethod.GET, new HttpEntity<>(null), WhiteboardStateDto.class).getBody();
        } catch (Exception ex) {
            state = new WhiteboardStateDto(name);
            state = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(state, null), WhiteboardStateDto.class).getBody();
        }

        System.out.println("Get whiteboard state: " + name);

        return state.toWhiteboardState();
    }

    public WhiteboardState clearState(String name) {
        return restTemplate.exchange(url + "/" + name + "/clear", HttpMethod.POST, new HttpEntity<>(null), WhiteboardStateDto.class)
                           .getBody()
                           .toWhiteboardState();
    }
}
