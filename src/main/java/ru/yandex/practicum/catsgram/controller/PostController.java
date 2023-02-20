package ru.yandex.practicum.catsgram.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    private static final Logger log = (Logger) LoggerFactory.getLogger(PostController.class);
    private final List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public List<Post> findAll() {
        log.setLevel(Level.DEBUG);
        log.debug("Текущее количество постов: " + posts.size());
        return posts;
    }

    @PostMapping(value = "/post")
    public void create(@RequestBody Post post) {
        log.setLevel(Level.DEBUG);
        log.debug("Сохраненный пост {}",post);
        posts.add(post);
    }
}