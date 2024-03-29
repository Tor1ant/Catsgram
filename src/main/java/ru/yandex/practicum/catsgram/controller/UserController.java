package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Collection<User> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping()
    public User postUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping()
    public User putUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("{login}")
    public Optional<User> findUserByEmail(@PathVariable String login) {
        return userService.findUserById(login);
    }
}
