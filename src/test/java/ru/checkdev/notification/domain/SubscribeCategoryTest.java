package ru.checkdev.notification.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.checkdev.notification.model.SubscribeCategory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscribeCategoryTest {

    @Test
    public void whenDefaultConstructorNotNull() {
        SubscribeCategory subscribeCategory = new SubscribeCategory();
        assertNotNull(subscribeCategory);
    }

    @Test
    public void whenFieldsConstructorNotNull() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(0, 1, 1);
        assertNotNull(subscribeCategory);
    }

    @Test
    public void whenIDSetAndGetEquals() {
        SubscribeCategory subscribeCategory = new SubscribeCategory(0, 1, 1);
        subscribeCategory.setId(1);
        assertThat(1, is(subscribeCategory.getId()));
    }
}