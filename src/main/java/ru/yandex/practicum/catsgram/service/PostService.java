package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();
    private static Integer globalPostId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, String sort, Integer from) {
        return posts.stream().sorted((date1, date2) -> {
            int comp = date1.getCreationDate().compareTo(date2.getCreationDate());
            if (sort.equals("desc")) {
                comp = -1 * comp;
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
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
        Optional<User> user = userService.findUserById(post.getAuthor());
        if (user == null) {
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        posts.add(post);
        post.setId(getNextId());
        return post;
    }

    public List<Post> findAllByUserMail(String sort, int size, String asText) {
        return posts.stream().filter(post -> post.getAuthor().equals(asText)).sorted((date1, date2) -> {
            int comp = date1.getCreationDate().compareTo(date2.getCreationDate());
            if (sort.equals("desc")) {
                comp = -1 * comp;
            }
            return comp;
        }).limit(size).collect(Collectors.toList());
    }
}