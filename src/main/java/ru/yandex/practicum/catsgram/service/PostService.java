package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();
    private static Integer globalPostId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll() {
        return posts;
    }

    private static Integer getNextId() {
        return globalPostId++;
    }

    public Post findPost(Integer postId) {

        return posts.stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));

    }

    public Post create(Post post) {
        User user = userService.findUserByEmail(post.getAuthor());
        if (user == null) {
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        posts.add(post);
        post.setId(getNextId());
        return post;
    }
}