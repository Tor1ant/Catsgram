package ru.yandex.practicum.catsgram;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.catsgram.controller.PostFeedController;
import ru.yandex.practicum.catsgram.service.PostService;
import ru.yandex.practicum.catsgram.service.UserService;

@SpringBootTest
class CatsgramApplicationTests {

    @Test
    void contextLoads() {
        PostFeedController postFeedController = new PostFeedController(new PostService(new UserService()));
        System.out.println(postFeedController.getFriendsFeed("{\"sort\":\"desc\"," +
                "\"size\":3," +
                "\"friends\":[\"puss@boots.com\",\"cat@dogs.net\",\"purrr@luv.me\"]}"));
    }
}
