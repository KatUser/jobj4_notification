package ru.checkdev.notification.service;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.checkdev.notification.model.SubscribeCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@NoArgsConstructor
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscribeCategoryServiceTest {

    @Autowired
    private SubscribeCategoryService service;

    @BeforeEach
    public void setUp() {
        service.deleteAllSubscribeCategories();
    }

    @Test
    public void whenGetAllSubCatReturnContainsValue() {
        SubscribeCategory subscribeCategory = this.service.save(new SubscribeCategory(1, 1));
        List<SubscribeCategory> result = this.service.findAll();
        assertTrue(result.contains(subscribeCategory));
    }

    @Test
    public void requestByUserIdReturnCorrectValueOfCategoryId() {
        SubscribeCategory subscribeCategory = this.service.save(new SubscribeCategory(25, 27));
        List<Integer> result = this.service.findCategoriesByUserId(subscribeCategory.getUserId());
        assertEquals(result.get(0), subscribeCategory.getCategoryId());
    }

    @Test
    public void whenDeleteSubCatItIsNotExist() {
        SubscribeCategory subscribeCategory = this.service.save(new SubscribeCategory(3, 3));
        subscribeCategory = this.service.delete(subscribeCategory);
        List<SubscribeCategory> result = this.service.findAll();
        assertFalse(result.contains(subscribeCategory));
    }
}