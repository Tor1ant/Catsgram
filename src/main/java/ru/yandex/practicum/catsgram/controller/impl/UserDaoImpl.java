package ru.yandex.practicum.catsgram.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserById(String id) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from cat_user where id = ?", id);
        if (sqlRowSet.next()) {
            log.info("User found: {}{}", sqlRowSet.getString("id"), sqlRowSet.getString("name"));
            User user = new User();
            user.setId(id);
            return Optional.of(user);
        } else {
            log.info("User not found: {}", id);
            return Optional.empty();
        }
    }
}
