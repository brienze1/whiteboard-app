package org.brienze.whiteboard.service;

import org.brienze.whiteboard.model.Shape;
import org.brienze.whiteboard.model.WhiteboardState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WhiteboardWebService {

    @Value("${whiteboard.api.url}")
    private String url;

    private final RestTemplate restTemplate;

    public WhiteboardWebService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    @Retryable(retryFor = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public void save(String name, Shape shape) {
//        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(null), String.class);
        System.out.println("Save Shape for whiteboard: " + name);
    }

    public WhiteboardState getState(String whiteboardName) {
        try {
//        restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), String.class);
        } catch (Exception ex) {
//        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(null), String.class);
        }
        System.out.println("Get whiteboard state: " + whiteboardName);

        return new WhiteboardState(whiteboardName);
    }

}
