package ru.checkdev.notification.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.checkdev.notification.model.SubscribeTopic;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscribeTopicTest {

    @Test
    public void whenDefaultConstructorNotNull() {
        SubscribeTopic subscribeTopic = new SubscribeTopic();
        assertNotNull(subscribeTopic);
    }

    @Test
    public void whenFieldsConstructorNotNull() {
        SubscribeTopic subscribeTopic = new SubscribeTopic(0, 1, 1);
        assertNotNull(subscribeTopic);
    }

    @Test
    public void whenIDSetAndGetEquals() {
        SubscribeTopic subscribeTopic = new SubscribeTopic(0, 1, 1);
        subscribeTopic.setId(1);
        assertThat(1, is(subscribeTopic.getId()));
    }
}