package ru.checkdev.notification;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
@AutoConfigureMockMvc
class SpringbootstartApplicationTests {

    @Test
    void contextLoads() {
    }

}