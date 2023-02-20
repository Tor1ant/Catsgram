package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<String, User> users = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping()
    public List<User> getUsers() {
        log.debug("В текущий момент: " + users.size() + " пользователей");
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User postUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new InvalidEmailException("email не может быть пустым.");
        }
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует.");
        }
        log.debug("Сохранённый пользователь: {}", user);
        users.put(user.getEmail(), user);
        return user;
    }

    @PutMapping()
    public User putUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new InvalidEmailException("email не может быть пустым.");
        }
        log.debug("Сохранённый пользователь: {}", user);
        users.put(user.getEmail(), user);
        return user;
    }
}
