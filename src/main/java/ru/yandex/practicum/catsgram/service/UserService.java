package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.UserController;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<String, User> users = new HashMap<>();
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public User createUser(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            throw new InvalidEmailException("email не может быть пустым.");
        }
        if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует.");
        }
        log.debug("Сохранённый пользователь: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    public Collection<User> findAllUsers() {
        log.debug("В текущий момент: " + users.size() + " пользователей");
        return users.values();
    }

    public User updateUser(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            throw new InvalidEmailException("email не может быть пустым.");
        }
        log.debug("Сохранённый пользователь: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> findUserById(String id) {
        return userDao.findUserById(id);
    }
}
