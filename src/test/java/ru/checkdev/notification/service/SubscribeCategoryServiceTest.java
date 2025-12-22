package ru.checkdev.notification.service;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.checkdev.notification.model.SubscribeCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@NoArgsConstructor
public class SubscribeCategoryServiceTest {

    @Autowired
    private SubscribeCategoryService service;

    @Test
    public void whenGetAllSubCatReturnContainsValue() {
        SubscribeCategory subscribeCategory = this.service.save(new SubscribeCategory(0, 1, 1));
        List<SubscribeCategory> result = this.service.findAll();
        assertTrue(result.contains(subscribeCategory));
    }

    @Test
    public void requestByUserIdReturnCorrectValue() {
        SubscribeCategory subscribeCategory = this.service.save(new SubscribeCategory(1, 2, 2));
        List<Integer> result = this.service.findCategoriesByUserId(subscribeCategory.getUserId());
        assertEquals(result, List.of(2));
    }

    @Test
    public void whenDeleteSubCatItIsNotExist() {
        SubscribeCategory subscribeCategory = this.service.save(new SubscribeCategory(2, 3, 3));
        subscribeCategory = this.service.delete(subscribeCategory);
        List<SubscribeCategory> result = this.service.findAll();
        assertFalse(result.contains(subscribeCategory));
    }
}