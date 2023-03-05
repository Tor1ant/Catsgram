package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feed/friends")
public class PostFeedController {
    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public List<List<Post>> getFriendsFeed(@RequestBody String friendsAndParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (friendsAndParams == null) {
            throw new RuntimeException("параметры заполнены не верно");
        }
        try {
            JsonNode firstDeserialization = objectMapper.readTree(friendsAndParams);
            String s = firstDeserialization.textValue();
            JsonNode secondDeserialization = objectMapper.readTree(s);
            String sort = secondDeserialization.get("sort").asText();
            int size = secondDeserialization.get("size").asInt();
            ArrayNode arrayNode = (ArrayNode) secondDeserialization.get("friends");
            List<List<Post>> friendsPosts = new ArrayList<>();
            for (JsonNode friendMail : arrayNode) {
                friendsPosts.add(postService.findAllByUserMail(sort, size, friendMail.asText()));
            }
            return friendsPosts;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("не правильный json");
        }
    }
}
